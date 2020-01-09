'''
@Time: 2019/12/31 15:37
@Author: mih
@Des: 
'''
from Tools import process_gml_file
import argparse
import torch
from DeepWalk import Deep_Walk
import torch.utils.data as Data
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from NN import LineNetwork
import numpy
from GGNN import GGNN
from NN import LineNetwork
import json
from Node2Vec import Node2vec

parser = argparse.ArgumentParser()
parser.add_argument('--batchSize', type=int, default=64, help='input batch size')
parser.add_argument('--lr', type=float, default=0.01, help='learning rate')
parser.add_argument('--manualSeed', type=int, help='manual seed')
parser.add_argument('--epoch', type=int, default=1000, help='epoch')
opt = parser.parse_args()
print(opt)


G, A, edges, nodes, neighbors = process_gml_file(
        r"D:\ComplexNetworkData\Complex Network Datasets\For Link Prediction\metabolic\metabolic.gml")
print("graph info: {0}".format(G.size()))
node_number = len(G.nodes)
use_gpu = torch.cuda.is_available()

def getDataLoader(train_data, train_label, test_data, test_label):
    train_data = torch.tensor(train_data, dtype=torch.float)
    train_label = torch.tensor(train_label, dtype=torch.long)
    train_dataSet = Data.TensorDataset(train_data, train_label)
    train_loader = Data.DataLoader(
        dataset=train_dataSet,
        batch_size=48,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    test_data = torch.tensor(test_data, dtype=torch.float)
    test_label = torch.tensor(test_label, dtype=torch.long)
    test_dataSet = Data.TensorDataset(test_data, test_label)
    test_loader = Data.DataLoader(
        dataset=test_dataSet,
        batch_size=16,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    return train_loader, test_loader

def train(train_loader, module, epochs, loss_function, optimizer):
    # 多批次循环
    for epoch in range(epochs):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            # 获取输入
            inputs, labels = data
            labels = labels.to(torch.long)
            if (use_gpu):
                inputs = inputs.cuda()
                labels = labels.cuda()

            # 梯度置0
            optimizer.zero_grad()
            # 正向传播，反向传播，优化
            outputs = module(inputs)
            loss = loss_function(outputs, labels)
            if (use_gpu):
                loss.cuda()
            loss.backward(retain_graph=True)
            optimizer.step()
            # 打印状态信息
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss / 2000))
                running_loss = 0.0

def eval(module, test_loader):
    module.eval()
    TP = 0
    TN = 0
    FP = 0
    FN = 0
    total = 0
    for test_data, test_label in test_loader:
        outputs = module(test_data)
        # torch.max 返回ouputs中第dim个维度的最大值即索引
        _, predictions = torch.max(outputs.data, 1)
        total += test_label.size(0)
        for index in range(len(predictions)):
            prediction = predictions[index]
            test = test_label[index]
            if (prediction == 1):
                if (test == 1):
                    TP = TP + 1
                if (test == 0):
                    TN = TN + 1
            if (prediction == 0):
                if (test == 1):
                    FP = FP + 1
                if (test == 0):
                    FN = FN + 1
    correct = TP + FN
    print("TP: {0}\n".format(TP))
    print("TN: {0}\n".format(TN))
    print("FP: {0}\n".format(FP))
    print("FN: {0}\n".format(FN))
    print('准确率: %.4f %%' % (100 * correct / total))
    # print("prediction :\n{0}".format(prediction_result))
    # print("real : \n{0}".format(reals))

def deep_walk_train():
    # DeepWalk获得嵌入表示
    deep_walk = Deep_Walk(walk_length=40, G=G, A=A, embed_size=30)
    train_data, train_label, test_data, test_label = deep_walk.get_data()
    train_loader, test_loader = getDataLoader(train_data, train_label, test_data, test_label)
    ln = LineNetwork(30, 61, 2)
    # print("Network params: {0}".format(len(ln.parameters())))

    if (use_gpu):
        ln = ln.cuda()
    # 定义损失函数
    loss_function = nn.CrossEntropyLoss()
    optimizer = optim.Adam(ln.parameters(), lr=0.001)
    # 训练模型
    train(train_loader, ln, 30, loss_function, optimizer)
    # 评估模型
    eval(ln, test_loader)

def node2Vec_train():
    with open(r"./params.json", 'r') as f:
        params = json.load(f)
        node2vec_params = params["Node2Vec"]
        walk_length = node2vec_params["walk_length"]
        num_walker = node2vec_params["num_walks"]
        walker = node2vec_params["walker"]
        p = node2vec_params["p"]
        q = node2vec_params["q"]
        embed_size = node2vec_params["embed_size"]
        iter = node2vec_params["iter"]
        window_size = node2vec_params["window_size"]
        worker = node2vec_params["workers"]
        node2vec = Node2vec(G = G, A = A, walk_length = walk_length, num_walks = num_walker,
                            walker = walker, p = p, q = q, embed_size = embed_size,
                            iter = iter, window_size = window_size, workers = worker)
        node2vec.train()
        train_data, train_label, test_data, test_label = node2vec.get_data()
        train_loader, test_loader = getDataLoader(train_data, train_label, test_data, test_label)
        ln = LineNetwork(30, 61, 2)
        # print("Network params: {0}".format(len(ln.parameters())))

        if (use_gpu):
            ln = ln.cuda()
        # 定义损失函数
        loss_function = nn.CrossEntropyLoss()
        optimizer = optim.Adam(ln.parameters(), lr=0.001)
        # 训练模型
        train(train_loader, ln, 30, loss_function, optimizer)
        # 评估模型
        eval(ln, test_loader)

def ggnn_train():
    state_dim = 5
    epochs = 6
    states = torch.rand((node_number, state_dim), dtype=torch.float32, requires_grad = True)
    ggnn = GGNN(state_dim=state_dim, n_edge_type=1, n_node=node_number, n_steps=1)
    if (use_gpu):
        ggnn = ggnn.cuda()
    train_loader, test_loader = ggnn(states, A)
    ln = LineNetwork(state_dim * 2, state_dim, 2)
    if (use_gpu):
        ln = ln.cuda()
    # 定义损失函数
    loss_function = nn.CrossEntropyLoss()
    optimizer = optim.Adam(ln.parameters(), lr=0.001)

    train(train_loader, ln, epochs, loss_function, optimizer)

    # 评估模型
    eval(ln, test_loader)


if __name__ == '__main__':
    node2Vec_train()