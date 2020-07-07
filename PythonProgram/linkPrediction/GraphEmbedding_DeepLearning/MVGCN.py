import torch
from torch import nn
from torch.nn import Parameter
import numpy
from torch.utils import data as Data
from Tools import process_gml_file
import torch.optim as optim

G, A, nodes, all_neighbors, As = process_gml_file(
        r"C:\Users\mihao\Desktop\米昊的东西\input.gml")

def getDataLoader(A, radio):
    data = []
    label = []
    N = len(A)
    for i in range(N):
        for j in range(N):
            if i != j:
                data.append([i, j])
                label.append(A[i][j])
    train_data = torch.tensor(data[: int(N * (N - 1)  * radio)])
    train_label = torch.tensor(label[: int(N * (N - 1) * radio)])

    train_dataset = Data.TensorDataset(train_data, train_label)

    train_loader = Data.DataLoader(
        dataset=train_dataset,
        batch_size=16,
        shuffle=True,
    )


    return train_loader

class MVGCN(nn.Module):
    def __init__(self, A, embedding_size, lambda_1, lambda_2):
        super(MVGCN, self).__init__()
        self.A = A
        shape = self.A.shape
        self.N = shape[0]
        self.lambda_1 = lambda_1
        self.lambda_2 = lambda_2
        self.D = numpy.diag([numpy.sum(row) for row in A])
        self._D = self.D ** (-1 / 2)
        self._D[numpy.isinf(self._D)] = 0
        self._D = torch.tensor(self._D, dtype = torch.float)

        self._A = torch.tensor(A, dtype = torch.float)
        self._A = self._A + torch.tensor(numpy.eye(N=self.N), dtype = torch.float)

        self.embedding_size = embedding_size


        self.W_G1 = torch.randn(size = [self.N, embedding_size])
        self.W_G1 = Parameter(self.W_G1, requires_grad = True)
        # self.register_parameter(name = 'mih_W_G1', param = self.W_G1)

        self.W_G2 = torch.randn(size = [embedding_size, embedding_size])
        self.W_G2 = Parameter(self.W_G2, requires_grad = True)
        # self.register_parameter(name = 'mih_W_G2', param = self.W_G2)

        self.F = torch.randn(size = [self.N, embedding_size])
        self.F = Parameter(self.F, requires_grad = True)
        # self.register_parameter(name= 'mih_F', param = self.F)

        self.W_F1 = torch.randn(size = [embedding_size, embedding_size])
        self.W_F1 = Parameter(self.W_F1, requires_grad = True)
        # self.register_parameter(name = "mih_W_F1", param = self.W_F1)

        self.W_F2 = torch.randn(size = [embedding_size, embedding_size])
        self.W_F2 = Parameter(self.W_F2, requires_grad = True)
        # self.register_parameter(name = 'mih_W_F2', param = self.W_F2)

        self.b1 = torch.randn(size = [self.N, embedding_size])
        self.b1 = Parameter(self.b1, requires_grad = True)
        # self.register_parameter(name = 'mih_b1', param = self.b1)

        self.b2 = torch.randn(size = [self.N, embedding_size])
        self.b2 = Parameter(self.b2, requires_grad = True)
        # self.register_parameter(name='mih_b2', param = self.b2)

        self.lap = torch.matmul(self._D, self._A)
        self.lap = torch.matmul(self.lap, self._D)

        self.active_function1 = nn.ReLU()
        self.active_function2 = nn.ReLU()

    # input: [batch_size, 2]
    def forward(self, *input):
        data = input[0]
        label = input[1]
        input_i = [item[0] for item in data]
        input_j = [item[1] for item in data]

        H = torch.matmul(torch.tensor(self.A, dtype = torch.float), self.W_G1)
        H = torch.matmul(self.lap, H)
        H = self.active_function1(H)

        P_G = torch.matmul(H, self.W_G2)
        P_G = torch.matmul(self.lap, P_G)

        P_G = self.active_function2(P_G)

        P_F_1 = torch.matmul(self.F, self.W_F1) + self.b1
        P_F_1 = self.active_function1(P_F_1)
        P_F_2 = torch.matmul(P_F_1, self.W_F2) + self.b2
        P_F = self.active_function2(P_F_2)
        L_GF = self.lambda_2 * torch.sum((P_G - P_F) ** 2)
        P = P_G + P_F
        # one_hot
        index_i = []
        for i in input_i:
            index = numpy.zeros(shape = [self.N,])
            index[i] = 1
            index_i.append(index)
        index_i = torch.tensor(index_i, dtype = torch.float)
        index_j = []
        for j in input_j:
            index = numpy.zeros(shape = [self.N,])
            index[j] = 1
            index_j.append(index)
        index_j = torch.tensor(index_j, dtype = torch.float)
        P_i = torch.matmul(index_i, P)
        P_j = torch.matmul(index_j, P)
        L_1 = torch.sum(torch.mul(P_i, P_j), dim = 1, keepdim = False)
        # label = []
        # for i in input_i:
        #     for j in input_j:
        #         label.append(self.A[i][j])
        L_1 = 0.5 * torch.sum((L_1 - torch.tensor(label)) ** 2)
        L_2 = self.lambda_1 * (torch.sum(self.W_G1 ** 2) +
                               torch.sum(self.W_G2 ** 2) +
                               torch.sum(self.W_F1 ** 2) +
                               torch.sum(self.W_F2 ** 2))
        L = L_GF + L_1 + L_2
        return L
def train(train_loader, module, epochs, optimizer):
    # 多批次循环
    for epoch in range(epochs):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            # 获取输入
            inputs, labels = data
            labels = labels.to(torch.long)
            # input = {'data': inputs, 'label': labels}
            # 梯度置0
            optimizer.zero_grad()
            # 正向传播，反向传播，优化
            loss = module(inputs, labels)
            # weights = torch.ones(size = loss.shape)
            loss.backward()
            optimizer.step()
            # 打印状态信息
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss / 2000))
                running_loss = 0.0
    W_G1 = module.state_dict()['W_G1']
    W_G2 = module.state_dict()['W_G2']
    F = module.state_dict()['F']
    W_F1 = module.state_dict()['W_F1']
    W_F2 = module.state_dict()['W_F2']
    b1 = module.state_dict()['b1']
    b2 = module.state_dict()['b2']

    return W_G1, W_G2, F, W_F1, W_F2, b1, b2

def produce_similary_matrix(A, W_G1, W_G2, F, W_F1, W_F2, b1, b2):
    shape = A.shape
    N = shape[0]
    D = numpy.diag([numpy.sum(row) for row in A])
    _D = D ** (-1 / 2)
    _D[numpy.isinf(_D)] = 0
    _D = torch.tensor(_D, dtype=torch.float)

    _A = torch.tensor(A, dtype=torch.float)
    _A = _A + torch.tensor(numpy.eye(N=N), dtype=torch.float)

    lap = torch.matmul(_D, _A)
    lap = torch.matmul(lap, _D)

    H = torch.matmul(torch.tensor(A, dtype=torch.float), W_G1)
    H = torch.matmul(lap, H)
    H = torch.relu(H)

    P_G = torch.matmul(H, W_G2)
    P_G = torch.matmul(lap, P_G)

    P_G = torch.relu(P_G)

    P_F_1 = torch.matmul(F, W_F1) + b1
    P_F_1 = torch.relu(P_F_1)
    P_F_2 = torch.matmul(P_F_1, W_F2) + b2
    P_F = torch.relu(P_F_2)

    P = P_G + P_F
    similary_matrix = torch.matmul(P, P.t())
    return similary_matrix

if __name__ == '__main__':
    train_loader = getDataLoader(A, 1.0)
    module = MVGCN(A = A, embedding_size = 6, lambda_1 = 0.5, lambda_2 = 0.5)
    # print(module.state_dict()['W_G1'].numpy())
    epochs = 1
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    W_G1, W_G2, F, W_F1, W_F2, b1, b2 = train(train_loader, module, epochs, optimizer)
    similary_matrix = produce_similary_matrix(A, W_G1, W_G2, F, W_F1, W_F2, b1, b2)
    similary_matrix = similary_matrix.numpy()
    with open(r'C:\Users\mihao\Desktop\米昊的东西\MVGCN_A.txt', 'w') as file:
        for row in similary_matrix:
            file.write(str(row))

