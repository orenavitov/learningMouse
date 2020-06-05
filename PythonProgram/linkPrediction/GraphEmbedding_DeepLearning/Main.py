import json
import torch
from torch import optim
from Tools import process_gml_file
from Tools import get_data_loader

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

if torch.cuda.is_available():
    GPU = True
else:
    GPU = False

G, A, edges, nodes, neighbors = process_gml_file(file_address)
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
    for i, data in enumerate(test_loader, 0):
        edges, labels = data
        predictions = module.test(edges)

if __name__ == '__main__':
    pass


