import json
import torch
from torch import optim
from Tools import process_gml_file
from Tools import get_data_loader
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding1
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding2
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding5
from Tools import generate_random_graph

with open(r"./params.json", 'r') as file:

    params = json.load(file)
    data_set_name = params["data_set"]
    module_name = params["module_name"]
    batchSize = params["batchSize"]
    epochs = params["epochs"]
    radio = params["radio"]
    print("module_name: {0}".format(module_name))
    if module_name == "MihGNNEmbedding1":
        module_params = params["MihGNNEmbedding1"]
    if module_name == "MihGNNEmbedding2":
        module_params = params["MihGNNEmbedding2"]
    if module_name == "MihGNNEmbedding5":
        module_params = params["MihGNNEmbedding5"]
    embedding_size = module_params["embedding_size"]
    layers = module_params["layers"]
    steps = module_params["steps"]
    delays = module_params["delays"]
    print("dataSet: {0}".format(data_set_name))
    print("radio: {0}".format(radio))
    print("batchSize: {0}".format(batchSize))
    print("epochs: {0}".format(epochs))
    print("embedding_size: {0}".format(embedding_size))
    print("layers: {0}".format(layers))
    print("steps: {0}".format(steps))
    print("delays: {0}".format(delays))

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

G, A, edges, nodes, neighbors = process_gml_file(file_address)
# A = generate_random_graph(10, 0.1)
train_loader, test_loader, A_test = get_data_loader(A, radio, batchSize, "under_sample", GPU)

A_shape = A.shape
N = A_shape[0]
print("邻接矩阵尺寸：[{0}, {1}]".format(A_shape[0], A_shape[1]))

if module_name == "MihGNNEmbedding1":
    module = MihGNNEmbedding1(A = A_test, N = N, d = embedding_size, layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding2":
    module = MihGNNEmbedding2(A = A, N = N, d = embedding_size, layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding5":
    module = MihGNNEmbedding5(A = A, N = N, d = embedding_size, layers = layers, steps = steps, delay = delays, GPU = GPU)
def Train(module, epochs):
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data

            labels = labels.to(torch.long)
            optimizer.zero_grad()
            loss = module(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 100 == 0:
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

def Test1(module):

    predictionsList = []
    labelsList = []
    for i, data in enumerate(test_loader, 0):
        edges, labels = data
        predictions = module.test(edges)
        labelsList.append(labels)
        predictionsList.append(predictions)
    predictionResult = torch.cat(predictionsList, dim = 0)
    predictionResult = torch.Tensor.cpu(predictionResult).detach().numpy()
    labelResult = torch.cat(labelsList, dim = 0)
    labelResult = torch.Tensor.cpu(labelResult).numpy()
    if len(predictionResult) != len(labelResult):
        print("error! The size of prediction is not equal the size of real!")
    test_size = len(labelResult)
    devide_values = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9]
    for devide_value in devide_values:
        TP = 0
        FP = 0
        TN = 0
        FN = 0
        for i in range(test_size):
            p = predictionResult[i]
            l = labelResult[i]
            if (p >= devide_value and l == 1):
                TP = TP + 1
            if (p < devide_value and l == 1):
                FN = FN + 1
            if (p >= devide_value and l == 0):
                FP = FP + 1
            if (p < devide_value and l == 0):
                TN = TN + 1
        print("-------------devide value : {0} ---------------".format(devide_value))
        print("TP: {0}".format(TP))
        print("FP: {0}".format(FP))
        print("TN: {0}".format(TN))
        print("FN: {0}".format(FN))

def Test2(module):
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
    Train(module, epochs)
    Test2(module)


