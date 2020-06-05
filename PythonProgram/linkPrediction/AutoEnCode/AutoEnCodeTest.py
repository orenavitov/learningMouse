import numpy
import torch
import torch.utils.data as Data
from torch import nn
from Tools import generate_random_graph
from Tools import get_test_matrix
import torch.optim as optim


A = generate_random_graph(12, 0.1)
shape = A.shape
N = shape[0]
A_test = get_test_matrix(A, 0.7)
print("A:")
print(A)
print("A_test:")
print(A_test)


def getDataLoader(A, radio):
    data = []
    label = []
    A_test = numpy.zeros_like(A)
    N = len(A)
    for i in range(N):
        for j in range(N):
            if i != j:
                data.append([i, j])
                label.append(A[i][j])
    train_indexes = data[: int(N * (N - 1) * radio)]
    train_label = label[: int(N * (N - 1) * radio)]
    for i, index in enumerate(train_indexes):
        row = index[0]
        col = index[1]
        A_test[row][col] = train_label[i]

    train_data = torch.tensor(train_indexes)
    train_label = torch.tensor(train_label)

    test_data = torch.tensor(data[int(N * (N - 1) * radio):])
    test_label = torch.tensor(label[int(N * (N - 1) * radio):])
    train_dataset = Data.TensorDataset(train_data, train_label)
    test_dataset = Data.TensorDataset(test_data, test_label)

    train_loader = Data.DataLoader(
        dataset=train_dataset,
        batch_size=16,
        shuffle=True,
    )

    test_loader = Data.DataLoader(
        dataset=test_dataset,
        batch_size=16,
        shuffle=True,
    )
    return train_loader, test_loader, A_test

class AutoEnCode1(nn.Module):
    def __init__(self, A, L, layers_d, penalty):
        super(AutoEnCode1, self).__init__()
        self.A = torch.tensor(A, dtype = torch.float)
        self.penalty = torch.tensor(penalty, dtype = torch.float)
        self.layers = []
        for l in range(L):
            input_features = layers_d[l][0]
            output_features = layers_d[l][1]
            layer = nn.Linear(in_features = input_features, out_features = output_features)
            self.add_module(name = 'layer{0}'.format(l + 1), module = layer)
            self.layers.append(layer)

        self.sigmoid = nn.Sigmoid()

    def forward(self, *input):
        edges = input[0]
        labels = input[1]
        nodes_i = [edge[0] for edge in edges]
        nodes_i = torch.tensor(nodes_i, dtype = torch.long)
        nodes_j = [edge[1] for edge in edges]
        nodes_j = torch.tensor(nodes_j, dtype = torch.long)
        X_i = self.A.index_select(index = nodes_i, dim = 0)
        X_j = self.A.index_select(index = nodes_j, dim = 0)
        loss_1 = []
        loss_2 = []
        loss_r = []
        for index, label in enumerate(labels):
            node_i = X_i[index]
            node_i_ = node_i
            node_j = X_j[index]
            node_j_ = node_j
            for layer in self.layers:
                node_i_ = layer(node_i_)
                node_i_ = self.sigmoid(node_i_)
                node_j_ = layer(node_j_)
                node_j_ = self.sigmoid(node_j_)
                loss_1.append(torch.norm((node_i_ - node_j_)) * label)
                for name, parameters in layer.named_parameters():
                    if name == 'weight':
                        r = torch.norm(parameters, dim = 1)
                        loss_r.append(torch.sum(r))
                    if name == 'bias':
                        r = torch.norm(parameters, dim = 0)
                        loss_r.append(r)
            if label >= 1:
                loss_2.append(torch.norm((node_i - node_i_) * self.penalty))
                loss_2.append(torch.norm((node_j - node_j_) * self.penalty))
            else:
                loss_2.append(torch.norm(node_i - node_i_))
                loss_2.append(torch.norm(node_j - node_j_))

        loss_1 = torch.stack(loss_1, dim = 0)
        loss_1 = torch.sum(loss_1, dim = 0)
        loss_2 = torch.stack(loss_2, dim = 0)
        loss_2 = torch.sum(loss_2, dim = 0)
        loss_r = torch.stack(loss_r, dim = 0)
        loss_r = torch.sum(loss_r, dim = 0)
        loss = loss_1 + loss_2 + loss_r
        return loss

    def test(self, input):
        nodes_i = [edge[0] for edge in input]
        nodes_j = [edge[1] for edge in input]
        nodes_i_tensor = torch.tensor(nodes_i, dtype = torch.long)
        nodes_j_tensor = torch.tensor(nodes_j, dtype = torch.long)
        predictions = []
        nodes_i_prediction = self.A.index_select(dim = 0, index = nodes_i_tensor)
        for layer in self.layers:
            nodes_i_prediction = layer(nodes_i_prediction)
            nodes_i_prediction = self.sigmoid(nodes_i_prediction)
        for index, node_i_prediction in enumerate(nodes_i_prediction):
            node_j = nodes_j[index]
            node_j_prediction = node_i_prediction[node_j]
            predictions.append(node_j_prediction)
        predictions = torch.stack(predictions, dim = 0)
        return predictions

def Test():
    train_loader, test_loader, A_test = getDataLoader(A, 0.8)
    module = AutoEnCode1(A=A, L = 4, layers_d = [(N, 8), (8, 5), (5, 8), (8, N)], penalty = 100.0)
    epochs = 1000
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
    for test_data in test_loader:
        inputs, labels = test_data
        labels = labels.to(torch.long)
        inputs = inputs.numpy()
        prediction = module.test(inputs)
        print("labels: {0}".format(labels))
        print("prediction: {0}".format(prediction))
if __name__ == '__main__':
    Test()