import networkx
import numpy
import torch
import torch.optim as optim
from torch import nn
from torch.nn import Parameter
from Tools import get_test_matrix

# 以某一规则生成一张图
def generateGrap():
    pass


nodes = list(range(6))
edges = [
    [0, 1],
    [1, 5],
    [2, 4],
    [3, 4]
]
G = networkx.Graph()
G.add_nodes_from(nodes)
G.add_edges_from(edges)
A = numpy.array(networkx.adjacency_matrix(G).todense())
test_A = get_test_matrix(A, 0.7)
print("A:")
print(A)
print("A*:")
print(test_A)

def test1():
    N = 6
    d = 6
    U = numpy.random.random([N, d])
    V = numpy.random.random([N, d])
    U = torch.tensor(data=U, dtype=torch.float, requires_grad=True)
    V = torch.tensor(data=V, dtype=torch.float, requires_grad=True)
    A_ = torch.tensor(A, dtype=torch.float)
    A_star = torch.matmul(U, V.t())
    diffence = A_ - A_star
    L = torch.sum(diffence ** 2)
    epochs = 100000
    optimizer = optim.SGD([U, V], lr=0.00001)
    for epoch in range(epochs):
        optimizer.zero_grad()
        L.backward(retain_graph=True)
        optimizer.step()
        if ((epoch + 1) % 200 == 0):
            print("epoch: {0}".format(epoch + 1))
            current_U = U.detach().numpy()
            current_V = V.detach().numpy()
            current_A = numpy.matmul(current_U, current_V.T)
            current_loss = numpy.sum((A - current_A) ** 2)
            print("{0}\n".format(current_A))
            print("current_loss: {0}".format(current_loss))


def test2():
    print("A: {0}".format(A))
    N = 6
    A_star = numpy.ones([N, N]) * 0.5
    A_star = torch.tensor(data = A_star, dtype = torch.float, requires_grad = True)
    A_ = torch.tensor(data = A, dtype = torch.float, requires_grad = True)
    diffence = A_ - A_star
    L = torch.min(torch.sum(diffence ** 2))
    epochs = 1000
    optimizer = optim.Adam([A_star], lr=0.001)
    for epoch in range(epochs):
        optimizer.zero_grad()
        L.backward(retain_graph=True)
        optimizer.step()
        if ((epoch + 1) % 40 == 0):
            print("epoch: {0}".format(epoch + 1))
            current_A = A_star.detach().numpy()
            current_loss = numpy.sum((A - current_A) ** 2)
            print("{0}\n".format(current_A))
            print("current_loss: {0}".format(current_loss))

class Embedding_1(nn.Module):
    def __init__(self, A, N):
        super(Embedding_1, self).__init__()
        self.A = A
        self.A_ = torch.tensor(self.A, dtype = torch.float)
        self.N = N
        A_star = numpy.ones([N, N]) * 0.5
        self.A_star = Parameter(torch.tensor(data=A_star, dtype=torch.float), requires_grad = True)


    def forward(self, input):
        diffence = self.A_ - self.A_star
        L = torch.sum(diffence ** 2)
        return L

class Embedding_2(nn.Module):
    def __init__(self, A, N, d):
        super(Embedding_2, self).__init__()
        self.A = A
        self.N = N
        self.d = d
        self.A_ = torch.tensor(self.A, dtype=torch.float)
        self.U = numpy.ones([self.N, self.d]) * 0.5
        self.U = Parameter(torch.tensor(self.U, dtype = torch.float), requires_grad = True)
        self.V = numpy.ones([self.N, self.d]) * 0.5
        self.V = Parameter(torch.tensor(self.V, dtype = torch.float), requires_grad = True)

    def forward(self, input):
        A_star = torch.matmul(self.U, self.V.t())
        difference = self.A_ - A_star
        L = torch.sum(difference ** 2)
        return L

class Embedding_3(nn.Module):
    def __init__(self, A, N, d):
        super(Embedding_3, self).__init__()
        self.A = A
        self.N = N
        self.d = d
        self.A_ = torch.tensor(self.A, dtype=torch.float)
        self.U = numpy.ones([1, self.d]) * 0.5
        self.U = Parameter(torch.tensor(self.U, dtype = torch.float), requires_grad = True)
        self.V = numpy.ones([1, self.d]) * 0.5
        self.V = Parameter(torch.tensor(self.V, dtype = torch.float), requires_grad = True)
        self.diag = numpy.ones([self.N, self.N, self.d]) * 0.5
        self.diag = Parameter(torch.tensor(self.diag, dtype = torch.float), requires_grad = True)

    def forward(self, input):
        A_star_U = torch.matmul(self.diag, self.U.t()).reshape([self.N, self.N])
        A_star_V = torch.matmul(self.diag, self.V.t()).reshape([self.N, self.N])
        A_star = A_star_U + A_star_V
        A_star = A_star.reshape(self.N, self.N)
        difference = self.A_ - A_star
        L = torch.sum(difference ** 2)
        return L


def test5():

    module = Embedding_3(A=test_A, N=6, d=6)
    epochs = 10000
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        optimizer.zero_grad()
        L = module(None)
        L.backward(retain_graph=True)
        optimizer.step()
        if ((epoch + 1) % 40 == 0):
            print("epoch: {0}".format(epoch + 1))

            current_U = module.state_dict()['U'].numpy()
            current_diag = module.state_dict()['diag'].numpy()
            current_V = module.state_dict()['V'].numpy()
            current_A_U = numpy.matmul(current_diag, current_U.T).reshape([6, 6])
            current_A_V = numpy.matmul(current_diag, current_V.T).reshape([6, 6])
            current_A = current_A_U + current_A_V

            current_loss = numpy.sum((A - current_A) ** 2)
            print("{0}\n".format(current_A))
            print("current_loss: {0}".format(current_loss))

def test3():
    module = Embedding_1(A, 6)
    epochs = 10000
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        optimizer.zero_grad()
        L = module(None)
        L.backward(retain_graph = True)
        optimizer.step()
        if ((epoch + 1) % 40 == 0):
            print("epoch: {0}".format(epoch + 1))

            current_A = module.state_dict()['A_star'].numpy()
            current_loss = numpy.sum((A - current_A) ** 2)
            print("{0}\n".format(current_A))
            print("current_loss: {0}".format(current_loss))

def test4():
    module = Embedding_2(A = A, N = 6, d = 6)
    epochs = 10
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        optimizer.zero_grad()
        L = module(None)
        L.backward(retain_graph=True)
        optimizer.step()
        # if ((epoch + 1) % 40 == 0):
        #     print("epoch: {0}".format(epoch + 1))
        #
        #     current_U = module.state_dict()['U'].numpy()
        #     current_V = module.state_dict()['V'].numpy()
        #     current_A = numpy.matmul(current_U, current_V.T)
        #     current_loss = numpy.sum((A - current_A) ** 2)
        #     print("{0}\n".format(current_A))
        #     print("current_loss: {0}".format(current_loss))
        print("epoch: {0}".format(epoch + 1))

        current_U = module.state_dict()['U'].numpy()
        current_V = module.state_dict()['V'].numpy()
        current_A = numpy.matmul(current_U, current_V.T)
        current_loss = numpy.sum((A - current_A) ** 2)
        print("{0}\n".format(current_A))
        print("current_loss: {0}".format(current_loss))




if __name__ == '__main__':
    test5()