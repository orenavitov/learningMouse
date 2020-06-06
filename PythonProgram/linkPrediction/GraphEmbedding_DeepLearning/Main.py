import json
import torch
from torch import optim
from Tools import process_gml_file
from Tools import get_data_loader
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding1
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
    file_address = r"./Data/hamster.gml"

if torch.cuda.is_available():
    GPU = True
else:
    GPU = False

G, A, edges, nodes, neighbors = process_gml_file(file_address)
# A = generate_random_graph(10, 0.1)
train_loader, test_loader, A_test = get_data_loader(A, radio, batchSize)
if GPU:
    train_loader = train_loader.cuda()
    test_loader = test_loader.cuda()
A_shape = A.shape
N = A_shape[0]
print("邻接矩阵尺寸：[{0}, {1}]".format(A_shape[0], A_shape[1]))



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
            if i != 0 and i % 2000 == 0:
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

def Test(module):
    TP = 0
    FP = 0
    TN = 0
    FN = 0
    predictionsList = []
    labelsList = []
    for i, data in enumerate(test_loader, 0):
        edges, labels = data
        predictions = module.test(edges)
        labelsList.append(labels)
        predictionsList.append(predictions)
    predictionResult = torch.cat(predictionsList, dim = 0)
    predictionResult = predictionResult.detach().numpy()
    labelResult = torch.cat(labelsList, dim = 0)
    labelResult = labelResult.numpy()
    if len(predictionResult) != len(labelResult):
        print("error! The size of prediction is not equal the size of real!")
    test_size = len(labelResult)
    for i in range(test_size):
        p = predictionResult[i]
        l = labelResult[i]
        if (p >= 0.5 and l == 1):
            TP = TP + 1
        if (p < 0.5 and l == 1):
            FN = FN + 1
        if (p >= 0.5 and l == 0):
            FP = FP + 1
        if (p < 0.5 and l == 0):
            TN = TN + 1
    print("TP: {0}".format(TP))
    print("FP: {0}".format(FP))
    print("TN: {0}".format(TN))
    print("FN: {0}".format(FN))

if __name__ == '__main__':
    module = MihGNNEmbedding1(A = A, N = N, d = embedding_size, layers = layers, steps = steps, delay = delays, GPU = False)
    Train(module, epochs)
    Test(module)


