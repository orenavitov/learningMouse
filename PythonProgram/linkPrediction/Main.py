'''
@Time: 2019/12/31 15:37
@Author: mih
@Des: 
'''
from Tools import process_gml_file
import torch
from DeepWalk import Deep_Walk
from torch import nn
import torch.optim as optim
from GGNN import GGNN
from NN import LineNetwork
import json
from Node2Vec import Node2vec
from VanillaGNN import GNN
from DeepWalk import getDataLoader as DeepWalk_getDataLoader
from Node2Vec import getDataLoader as Node2Vec_getDataLoader
from VanillaGNN import getDataLoader as VanillaGNN_getDataLoader
from MihGNNEmbeddingTest import getDataLoader as MihGNNEmbeddingTest_getDataLoader
from MihGNNEmbeddingTest import MihGNNEmbeddingTest1

G, A, edges, nodes, neighbors = process_gml_file(
        r"C:\Users\mihao\Desktop\米昊的东西\input.gml")
shape = A.shape
N = shape[0]
node_number = len(G.nodes)
use_gpu = torch.cuda.is_available()
with open(r"./params.json", 'r') as f:
    params = json.load(f)
    batchSize = params["batchSize"]
    epochs = params["epochs"]




def train(train_loader, module, epochs, loss_function, optimizer):
    for epoch in range(epochs):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            if (use_gpu):
                inputs = inputs.cuda()
                labels = labels.cuda()
            optimizer.zero_grad()
            outputs = module(inputs)
            loss = loss_function(outputs, labels)
            if (use_gpu):
                loss.cuda()
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
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
        _, predictions = torch.max(outputs.data, 1)
        total += test_label.size(0)
        for index in range(len(predictions)):
            prediction = predictions[index]
            test = test_label[index]
            if (prediction == 1):
                if (test == 1):
                    TP = TP + 1
                if (test == 0):
                    FP = FP + 1
            if (prediction == 0):
                if (test == 1):
                    FN = FN + 1
                if (test == 0):
                    TN = TN + 1
    print("TP: {0}\n".format(TP))
    print("TN: {0}\n".format(TN))
    print("FP: {0}\n".format(FP))
    print("FN: {0}\n".format(FN))
    print('准确率: %.4f %%' % (100 * (TP) / (TP + FP)))


def deep_walk_train():

        deep_walk_params = params["DeepWalk"]
        embed_size = deep_walk_params["embed_size"]
        walk_length = deep_walk_params["walk_length"]
        num_walks = deep_walk_params["num_walks"]
        walker = deep_walk_params["walker"]
        # DeepWalk获得嵌入表示
        deep_walk = Deep_Walk(walk_length=walk_length, G=G, A=A, embed_size=embed_size)
        train_data, train_label, test_data, test_label = deep_walk.get_data()
        train_loader, test_loader = DeepWalk_getDataLoader(train_data, train_label, test_data, test_label, batchSize)
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
        train_loader, test_loader = Node2Vec_getDataLoader(train_data, train_label, test_data, test_label, batchSize)
        ln = LineNetwork(30, 61, 2)
        # print("Network params: {0}".format(len(ln.parameters())))

        if (use_gpu):
            ln = ln.cuda()
        # 定义损失函数
        loss_function = nn.CrossEntropyLoss()
        optimizer = optim.Adam(ln.parameters(), lr=0.001)
        # 训练模型
        train(train_loader, ln, epochs, loss_function, optimizer)
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

def VanillaGNN_train():
    VanillaGNN_params = params["VanillaGNN"]
    embed_size = VanillaGNN_params["embed_size"]
    gnn = GNN(G=G, A=A, neighbors=neighbors, embedding_size=embed_size)
    loss_function = torch.nn.CrossEntropyLoss()
    optimizer = optim.Adam(gnn.parameters(), lr=0.001)
    train_loader, test_loader = VanillaGNN_getDataLoader(A, radio=0.6)
    train(train_loader=train_loader, module=gnn, epochs=epochs, loss_function=loss_function, optimizer=optimizer)
    eval(gnn, test_loader)

def MihGNNEmbedding_train():
    train_loader, test_loader = MihGNNEmbeddingTest_getDataLoader(A, radio=0.7)
    module = MihGNNEmbeddingTest1(A=A, N=N, d=6, layers=3, steps=2, delay=[1, 0.5, 0.1])
    loss_function = torch.nn.CrossEntropyLoss()
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    train(train_loader, module, epochs, loss_function, optimizer)
    eval(module, test_loader)
if __name__ == '__main__':
    MihGNNEmbedding_train()