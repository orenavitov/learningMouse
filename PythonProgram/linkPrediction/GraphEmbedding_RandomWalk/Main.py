import torch
import json
from torch import nn
from torch import optim
from GraphEmbedding_RandomWalk.NN import LineNetwork
from GraphEmbedding_RandomWalk.DeepWalk import Deep_Walk
from GraphEmbedding_RandomWalk.Node2Vec import Node2vec
from GraphEmbedding_RandomWalk.DeepWalk import getDataLoader as DeepWalk_getDataLoader
from GraphEmbedding_RandomWalk.Node2Vec import getDataLoader as Node2Vec_getDataLoader
from Tools import process_gml_file
with open(r"./params.json", 'r') as file:
    params = json.load(file)
    data_set_name = params["data_set"]
    batchSize = params["batchSize"]
    epochs = params["epochs"]
    radio = params["radio"]

    print("dataSet: {0}".format(data_set_name))
    print("radio: {0}".format(radio))

    print("batchSize: {0}".format(batchSize))
    print("epochs: {0}".format(epochs))


if data_set_name == 'bio-CE-GT':
    file_address = r"./Data/bio-CE-GT.gml"
if data_set_name == 'hamster':
    file_address = r"./Data/hamster.gml"

G, A, edges, nodes, neighbors = process_gml_file(file_address)

if torch.cuda.is_available():
    GPU = True
else:
    GPU = False

def train(train_loader, module, epochs, loss_function, optimizer):
    for epoch in range(epochs):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            if (GPU):
                inputs = inputs.cuda()
                labels = labels.cuda()
            optimizer.zero_grad()
            outputs = module(inputs)
            loss = loss_function(outputs, labels)
            if (GPU):
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

    if (GPU):
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
    node2vec = Node2vec(G=G, A=A, walk_length=walk_length, num_walks=num_walker,
                        walker=walker, p=p, q=q, embed_size=embed_size,
                        iter=iter, window_size=window_size, workers=worker)
    node2vec.train()
    train_data, train_label, test_data, test_label = node2vec.get_data()
    train_loader, test_loader = Node2Vec_getDataLoader(train_data, train_label, test_data, test_label, batchSize)
    ln = LineNetwork(30, 61, 2)
    # print("Network params: {0}".format(len(ln.parameters())))

    if (GPU):
        ln = ln.cuda()
    # 定义损失函数
    loss_function = nn.CrossEntropyLoss()
    optimizer = optim.Adam(ln.parameters(), lr=0.001)
    # 训练模型
    train(train_loader, ln, epochs, loss_function, optimizer)
    # 评估模型
    eval(ln, test_loader)