import json
import torch
from torch import optim
import numpy
from GraphEmbedding_DeepLearning.PolysemousNetworkEmbedding import PriorDistribution
from GraphEmbedding_DeepLearning.PolysemousNetworkEmbedding import PolysemousNetwork
from GraphEmbedding_DeepLearning.PolysemousNetworkEmbedding import MihPolysemousNetwork
from Tools import process_gml_file
from Tools import get_data_loader
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding1
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding2
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding3
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding4
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding5
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding6
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding7
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding8
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding9
from GraphEmbedding_DeepLearning.MihGNNEmbeddingModules import MihGNNEmbedding10
from tqdm import tqdm

with open(r"./params.json", 'r') as file:

    params = json.load(file)
    data_set_name = params["data_set"]
    module_name = params["module_name"]
    batchSize = params["batchSize"]
    epochs = params["epochs"]
    radio = params["radio"]
    learning_rate = params["learning_rate"]
    print("module_name: {0}".format(module_name))
    if module_name == "MihGNNEmbedding1":
        module_params = params["MihGNNEmbedding1"]
    if module_name == "MihGNNEmbedding2":
        module_params = params["MihGNNEmbedding2"]
    if module_name == "MihGNNEmbedding3":
        module_params = params["MihGNNEmbedding3"]
    if module_name == "MihGNNEmbedding4":
        module_params = params["MihGNNEmbedding4"]
    if module_name == "MihGNNEmbedding5":
        module_params = params["MihGNNEmbedding5"]
    if module_name == "MihGNNEmbedding6":
        module_params = params["MihGNNEmbedding6"]
    if module_name == "MihGNNEmbedding7":
        module_params = params["MihGNNEmbedding7"]
    if module_name == "MihGNNEmbedding8":
        module_params = params["MihGNNEmbedding8"]
    if module_name == "MihGNNEmbedding9":
        module_params = params["MihGNNEmbedding9"]
    if module_name == "MihGNNEmbedding10":
        module_params = params["MihGNNEmbedding10"]
    if module_name == "PolysemousNetworkEmbedding":
        module_params = params["PolysemousNetworkEmbedding"]
    if module_name == "MihPolysemousNetworkEmbedding":
        module_params = params["MihPolysemousNetworkEmbedding"]

    embedding_size = module_params["embedding_size"]
    layers = module_params["layers"]
    if module_name != 'PolysemousNetworkEmbedding' and module_name != "MihPolysemousNetworkEmbedding":
        steps = module_params["steps"]
        delays = module_params["delays"]
        print("steps: {0}".format(steps))
        print("delays: {0}".format(delays))
    if module_name == 'PolysemousNetworkEmbedding':
        K = module_params["K"]
        alpha = module_params["alpha"]
        P_epochs = module_params["P_epochs"]
        print("K: {0}".format(K))
        print("alpha: {0}".format(alpha))
        print("P_epochs: {0}".format(P_epochs))
    if module_name == 'MihPolysemousNetworkEmbedding':
        K = module_params["K"]
        alpha = module_params["alpha"]
        P_epochs = module_params["P_epochs"]
        print("K: {0}".format(K))
        print("alpha: {0}".format(alpha))
        print("P_epochs: {0}".format(P_epochs))
    print("dataSet: {0}".format(data_set_name))
    print("radio: {0}".format(radio))
    print("batchSize: {0}".format(batchSize))
    print("epochs: {0}".format(epochs))
    print("embedding_size: {0}".format(embedding_size))
    print("layers: {0}".format(layers))

file_address = r"../Data/{0}.gml".format(data_set_name)


if torch.cuda.is_available():
    GPU = True
else:
    GPU = False

G, A, nodes, all_nodes_neighbors, As = process_gml_file(file_address)
# A = generate_random_graph(10, 0.1)
train_loader, test_loader, A_test = get_data_loader(A, radio, batchSize, "under_sample", GPU)

A_shape = A.shape
N = A_shape[0]
print("邻接矩阵尺寸：[{0}, {1}]".format(A_shape[0], A_shape[1]))




def Train(module, epochs):
    print("module parameters:")
    for name, parameter in module.named_parameters():
        print("name:{0}\nparameter:{1}".format(name, parameter))
    optimizer = optim.Adam(module.parameters(), lr=learning_rate)
    for epoch in tqdm(range(epochs)):
        sum_loss = 0.0
        for data in train_loader:
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer.zero_grad()
            loss = module(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer.step()
            sum_loss = sum_loss + loss
        print("epochs : {0} loss : {1}".format(epoch, sum_loss))

def Test1(module):
    for name, parameter in module.named_parameters():
        print("name:{0}\nparameter:{1}".format(name, parameter))
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
        print("AP: {0}".format((TP + TN) / (TP + FP + TN + FN)))
        print("AC: {0}".format((TP) / (TP + FP)))

def Test2(module):
    for name, parameter in module.named_parameters():
        print("name:{0}\nparameter:{1}".format(name, parameter))
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
    print("AP: {0}".format((TP + TN) / (TP + FP + TN + FN)))
    print("AC: {0}".format((TP) / (TP + FP)))

if module_name == "MihGNNEmbedding1":
    module = MihGNNEmbedding1(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding2":
    module = MihGNNEmbedding2(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding3":
    module = MihGNNEmbedding3(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding4":
    module = MihGNNEmbedding4(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding5":
    module = MihGNNEmbedding5(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding6":
    module = MihGNNEmbedding6(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding7":
    module = MihGNNEmbedding7(A = A_test, N = N, d = embedding_size, layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding8":
    module = MihGNNEmbedding8(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding9":
    module = MihGNNEmbedding9(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "MihGNNEmbedding10":
    module = MihGNNEmbedding10(A = A_test, As = As, all_nodes_neighbors = all_nodes_neighbors, N = N, d = embedding_size,
                              layers = layers, steps = steps, delay = delays, GPU = GPU)
if module_name == "PolysemousNetworkEmbedding":
    A_test = A_test + numpy.eye(N)
    P_module = PriorDistribution(A = A_test, K = K, alpha = alpha, GPU = GPU)
    optimizer = optim.Adam(P_module.parameters(), lr=0.001)
    for epoch in range(P_epochs):
        optimizer.zero_grad()
        loss = P_module()
        print("epoch : {0}   loss : {1}".format(epoch + 1, loss))
        loss.backward(retain_graph=True)
        optimizer.step()
    P = P_module.state_dict()["prior_distribution_matrix"]
    module = PolysemousNetwork(P = P, A = A_test, embedding_size = embedding_size, layers = layers, K = K, GPU = GPU)
if module_name == "MihPolysemousNetworkEmbedding":
    A_test = A_test + numpy.eye(N)
    P_module = PriorDistribution(A = A_test, K = K, alpha = alpha, GPU = GPU)
    optimizer = optim.Adam(P_module.parameters(), lr=0.001)
    for epoch in range(P_epochs):
        optimizer.zero_grad()
        loss = P_module()
        print("epoch : {0}   loss : {1}".format(epoch + 1, loss))
        loss.backward(retain_graph=True)
        optimizer.step()
    P = P_module.state_dict()["prior_distribution_matrix"]
    module = MihPolysemousNetwork(P = P, A = A_test, embedding_size = embedding_size, layers = layers, K = K, GPU = GPU)


cross_entropy_loss_function_Modules = ["MihGNNEmbedding4", "MihGNNEmbedding5", "MihGNNEmbedding6", "MihGNNEmbedding7",
                                       "MihGNNEmbedding9", "MihGNNEmbedding10", "MihPolysemousNetworkEmbedding"]

if __name__ == '__main__':
    Train(module, epochs)
    if (module_name in cross_entropy_loss_function_Modules):
        Test2(module)
    else:
        Test1(module)


