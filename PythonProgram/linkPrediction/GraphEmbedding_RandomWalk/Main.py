import torch
import json
from torch import nn
from torch import optim
import numpy
import torch.utils.data as Data
from GraphEmbedding_RandomWalk.DeepWalk import Deep_Walk
from GraphEmbedding_RandomWalk.Node2Vec import Node2vec
from Tools import process_gml_file


def getDataLoader(A, radio, batch_size, sample_method):
    node_number = A.shape[0]
    positives = []
    negavives = []
    for i in range(node_number):
        for j in range(node_number):
            if i != j:
                label = A[i][j]
                if label == 1:
                    positives.append([i, j, 1])
                if label == 0:
                    negavives.append([i, j, 0])
    edges_size = len(positives)
    if sample_method == 'under_sample':
        numpy.random.shuffle(positives)
        numpy.random.shuffle(negavives)
        train_positives = positives[ : int(edges_size * radio)]
        test_positives = positives[int(edges_size * radio) : ]
        train_negatives = negavives[ : int(edges_size * radio)]
        test_negatives = negavives[int(edges_size * radio) : edges_size]
        train_positives.extend(train_negatives)
        train_data = train_positives
        test_positives.extend(test_negatives)
        test_data = test_positives
        numpy.random.shuffle(train_data)
        numpy.random.shuffle(test_data)
    if sample_method == 'over_sample':
        pass
    A_test = numpy.zeros(shape=[node_number, node_number])
    for i, index in enumerate(positives):
        row = index[0]
        col = index[1]
        A_test[row][col] = 1
    train_pairs = [pair[: 2] for pair in train_data]
    train_labels = [pair[-1] for pair in train_data]
    test_pairs = [pair[: 2] for pair in test_data]
    test_labels = [pair[-1] for pair in test_data]
    train_pairs = torch.tensor(train_pairs, dtype=torch.long)
    train_label = torch.tensor(train_labels, dtype=torch.long)
    train_dataSet = Data.TensorDataset(train_pairs, train_label)
    train_loader = Data.DataLoader(
        dataset=train_dataSet,
        batch_size=batch_size,
        shuffle=True,
    )
    test_pairs = torch.tensor(test_pairs, dtype=torch.long)
    test_label = torch.tensor(test_labels, dtype=torch.long)
    test_dataSet = Data.TensorDataset(test_pairs, test_label)
    test_loader = Data.DataLoader(
        dataset=test_dataSet,
        batch_size=batch_size,
        shuffle=True,
    )
    return train_loader, test_loader, A_test


with open(r"./params.json", 'r') as file:
    params = json.load(file)
    data_set_name = params["data_set"]
    module_name = params["module_name"]
    batchSize = params["batchSize"]
    epochs = params["epochs"]
    radio = params["radio"]
    print("module_name: {0}".format(module_name))
    if module_name == "DeepWalk":
        module_params = params["DeepWalk"]
    if module_name == "Node2Vec":
        module_params = params["Node2Vec"]
        p = module_params["p"]
        q = module_params["q"]
        print("p: {0}".format(p))
        print("q: {0}".format(q))
    embed_size = module_params["embed_size"]
    walk_length = module_params["walk_length"]
    num_walks = module_params["num_walks"]
    workers = module_params["workers"]
    window_size = module_params["window_size"]
    iter = module_params["iter"]
    sample_method = module_params['sample_method']
    print("dataSet: {0}".format(data_set_name))
    print("radio: {0}".format(radio))
    print("batchSize: {0}".format(batchSize))
    print("epochs: {0}".format(epochs))
    print("embedding_size: {0}".format(embed_size))
    print("walk_length: {0}".format(walk_length))
    print("num_walks: {0}".format(num_walks))
    print("workers: {0}".format(workers))
    print("window_size: {0}".format(window_size))
    print("iter: {0}".format(iter))
    print("sample_method: {0}".format(sample_method))

if data_set_name == 'bio-CE-GT':
    file_address = r"../Data/bio-CE-GT.gml"
if data_set_name == 'hamster':
    file_address = r"../Data/hamster.gml"
if data_set_name == 'USAir':
    file_address = r"../Data/USAir.gml"

if torch.cuda.is_available():
    GPU = True
else:
    GPU = False

G, A, nodes, all_neighbors, As = process_gml_file(file_address)
train_loader, test_loader, A_test = getDataLoader(A, radio=radio, batch_size=batchSize, sample_method=sample_method)
if module_name == 'DeepWalk':
    module = Deep_Walk(G=G, A=A_test, walk_length=walk_length, embed_size=embed_size, window_size=window_size,
                       workers=workers)
if module_name == 'Node2Vec':
    module = Node2vec(G = G, A = A_test, walk_length = walk_length, p = p, q = q, embed_size = embed_size, iter = iter, window_size = window_size, workers = workers)


def Train(module):
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data

            optimizer.zero_grad()
            loss = module(inputs, labels)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 10 == 0:
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0


def Test(module):
    TP = 0
    TN = 0
    FP = 0
    FN = 0
    total = 0
    for test_data, test_label in test_loader:
        output = module.test(test_data)
        _, predictions = torch.max(output.data, dim=1)
        total += test_label.size(0)
        for index in range(len(predictions)):
            prediction = predictions[index]
            test = test_label[index]
            if (prediction == 1 and test == 1):
                TP = TP + 1
            if (prediction == 1 and test == 0):
                FP = FP + 1
            if (prediction == 0 and test == 1):
                FN = FN + 1
            if (prediction == 0 and test == 0):
                TN = TN + 1
    print("TP: {0}".format(TP))
    print("TN: {0}".format(TN))
    print("FP: {0}".format(FP))
    print("FN: {0}".format(FN))

if __name__ == '__main__':
    Train(module)
    Test(module)
