import torch
from torch import nn
import numpy
from torch.nn import Parameter
import torch.utils.data as Data
import torch.optim as optim
import networkx
from Tools import process_gml_file
from Tools import get_test_matrix
from Tools import write_matrix
import copy
import math

numpy.random.seed(1)

# G, A, edges, nodes, neighbors = process_gml_file(
#     r"C:\Users\mihao\Desktop\米昊的东西\dataset\petster-friendships-hamster\hamster.gml")
# shape = A.shape
# N = shape[0]
#
# A_test = get_test_matrix(A, keep_radio=0.7)


nodes = list(range(10))
edges = [
    [0, 1],
    [1, 2],
    [1, 7],
    [1, 9],
    [2, 3],
    [2, 4],
    [2, 8],
    [1, 5],
    [2, 4],
    [3, 4],
    [3, 7],
    [3, 9],
    [4, 7],
    [4, 8],
    [5, 6],
    [5, 7],
    [6, 9],
    [8, 9]
]
G = networkx.Graph()
G.add_nodes_from(nodes)
G.add_edges_from(edges)
A = numpy.array(networkx.adjacency_matrix(G).todense())
shape = A.shape
N = shape[0]
A_test = get_test_matrix(A, 0.7)
print("A:")
print(A)
print("A_test:")
print(A_test)

# G_density = len(edges) / (N * N - N)

def getDataLoader(A, radio):
    data = []
    label = []
    N = len(A)
    for i in range(N):
        for j in range(N):
            if i != j:
                data.append([i, j])
                label.append(A[i][j])
    train_data = torch.tensor(data[: int(N * (N - 1) * radio)])
    train_data_size = len(train_data)
    train_label = torch.tensor(label[: int(N * (N - 1) * radio)])

    test_data = torch.tensor(data[int(N * (N - 1) * radio):])
    test_data_size = len(test_data)
    test_label = torch.tensor(label[int(N * (N - 1) * radio):])
    train_dataset = Data.TensorDataset(train_data, train_label)
    test_dataset = Data.TensorDataset(test_data, test_label)

    train_loader = Data.DataLoader(
        dataset=train_dataset,
        batch_size=64,
        shuffle=True,
    )

    test_loader = Data.DataLoader(
        dataset=test_dataset,
        batch_size=64,
        shuffle=True,
    )
    return train_loader, test_loader


# 寻找图中的孤立点
def finde_single_nodes(A):
    single_nodes = []
    D = numpy.sum(A, axis=1, keepdims=False)
    for i in range(N):
        if D[i] == 0:
            single_nodes.append(i)
    return single_nodes


def A_pre_handle(A, steps, delay):
    A_s = []
    I = numpy.eye(N)
    for step in range(steps):
        A_current = copy.deepcopy(A)
        e = 1
        while (e < (step + 1)):
            A_current = numpy.matmul(A_current, A)
            e = e + 1
        for i in range(N):
            for j in range(N):
                if (i == j):
                    A_current[i][j] = 0
                if (A_current[i][j] != 1):
                    A_current[i][j] = 0
        A_s.append(delay[step] * A_current + I)
        result = numpy.sum(A_s, axis=0)
    return result


# 处理最终相似矩阵的孤立点和自连接
def A_star_handle(A, A_star):
    single_nodes = finde_single_nodes(A)
    # 处理孤立点
    for i in range(N):
        if i in single_nodes:
            for j in range(N):
                A_star[i][j] = 0.0
    # 处理自连接
    for i in range(N):
        A_star[i][i] = 0.0
    # 相似矩阵中的取绝对值
    for i in range(N):
        for j in range(N):
            A_star[i][j] = math.fabs(A_star[i][j])
    # 将训练矩阵中的0 变为 -1
    A_ = copy.deepcopy(A)
    for i in range(N):
        for j in range(N):
            if A_[i][j] == 0:
                A_[i][j] = -1

    A_devide = A_ * A_star
    return A_devide


def find_devide_value(A_devide, trust_value):
    positive = []
    negitive = []
    for i in range(N):
        for j in range(N):
            if A_devide[i][j] < 0.0:
                negitive.append(A_devide[i][j])
            if A_devide[i][j] > 0.0:
                positive.append(A_devide[i][j])
    large_value = numpy.min(positive)
    small_value = numpy.max(negitive)
    devide_value = large_value - (large_value - small_value) * trust_value
    return devide_value


def cal_cos_similary(src, dst):
    result = numpy.matmul(src, dst.T)
    x_1 = (numpy.sum(src ** 2, axis=1) ** 0.5).reshape([-1, 1])

    x_2 = (numpy.sum(src ** 2, axis=1) ** 0.5).reshape([1, -1])
    result = result / numpy.matmul(x_1, x_2)
    return result


def evel(A, A_star, devide_value):
    TP = 0
    FP = 0
    TN = 0
    FN = 0
    for i in range(N):
        for j in range(N):
            if i == j:
                continue
            similary = A_star[i][j]
            if similary >= devide_value:
                if A[i][j] == 1:
                    TP = TP + 1
                if A[i][j] == 0:
                    FP = FP + 1
            if similary < devide_value:
                if A[i][j] == 1:
                    FN = FN + 1
                if A[i][j] == 0:
                    TN = TN + 1
    print("TP: {0}".format(TP))
    print("FP: {0}".format(FP))
    print("TN: {0}".format(TN))
    print("FN: {0}".format(FN))


# GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
# 改进负样本的嵌入状态更新
class MihGNNEmbeddingTest1(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay):
        super(MihGNNEmbeddingTest1, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(A_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)
        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
        self.relu = nn.ReLU()

    def forward(self, *input):
        edges = input[0].numpy()
        src = [edge[0] for edge in edges]
        dst = [edge[1] for edge in edges]
        labels = input[1]
        labels_scalar = labels.numpy()
        batch_size = len(edges)

        src_tensor = torch.tensor(src, dtype=torch.long)
        dst_tensor = torch.tensor(dst, dtype=torch.long)

        neighbors_src = self.A_s.index_select(dim = 0, index = src_tensor)
        neighbors_dst = self.A_s.index_select(dim = 0, index = dst_tensor)

        embedding_states_src_list = []
        embedding_states_dst_list = []

        for index, label in enumerate(labels_scalar):
            src_index = src[index]
            dst_index = dst[index]
            if label == 1:
                src_embedding_state = torch.matmul(neighbors_src[index], self.embedding_state)
                embedding_states_src_list.append(src_embedding_state)
                dst_embedding_state = torch.matmul(neighbors_dst[index], self.embedding_state)
                embedding_states_dst_list.append(dst_embedding_state)

            if label == 0:
                embedding_states_src_list.append(self.embedding_state[src_index])
                embedding_states_dst_list.append(self.embedding_state[dst_index])

        embedding_states_src_list = torch.stack(embedding_states_src_list, dim = 0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim = 0)

        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(embedding_states_src_list)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(embedding_states_dst_list)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = torch.tensor(math.e) ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss, predicts

def test1(A):
    train_loader, test_loader = getDataLoader(A, 0.8)
    N = A.shape[0]
    module = MihGNNEmbeddingTest1(A=A, N=N, d=128, layers=6, steps=2, delay=[1, 0.5])
    epochs = 20
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer.zero_grad()
            loss, _ = module(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

    devide_value = 0.0
    while (devide_value < 1.0):
        TP = 0
        FP = 0
        TN = 0
        FN = 0
        print('-------------devide value: {0}----------------'.format(str(devide_value)))
        for test_data, test_label in test_loader:
            _, Y = module(test_data, test_label)
            Y = Y.detach().numpy()
            labels = test_label.numpy()
            num = len(Y)
            for i in range(num):
                if Y[i] >= devide_value:
                    if labels[i] == 1:
                        TP = TP + 1
                    if labels[i] == 0:
                        FP = FP + 1
                if Y[i] < devide_value:
                    if labels[i] == 1:
                        FN = FN + 1
                    if labels[i] == 0:
                        TN = TN + 1
        print("TP: {0}".format(TP))
        print("FP: {0}".format(FP))
        print("TN: {0}".format(TN))
        print("FN: {0}".format(FN))
        devide_value = devide_value + 0.1
def test1_(A):
    train_loader, test_loader = getDataLoader(A, 0.8)
    N = A.shape[0]
    module1 = MihGNNEmbeddingTest1(A=A, N=N, d=20, layers=6, steps=2, delay=[1, 0.5])
    module2 = MihGNNEmbeddingTest2(A=A, N=N, d=20, layers=6, steps=2, delay=[1, 0.5])
    epochs = 1000
    optimizer1 = optim.Adam(module1.parameters(), lr=0.001)
    indexs_1 = []
    indexs_2 = []
    labels_1 = []
    labels_2 = []
    for epoch in range(epochs):
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer1.zero_grad()
            loss, predictions = module1(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer1.step()
            if (epoch + 1) == epochs:
                indexs_1.extend(inputs.numpy())
                labels_1.extend(predictions.detach().numpy())
    for test_data, test_label in test_loader:
        _, predictions = module1(test_data, test_label)
        indexs_1.extend(test_data.numpy())
        labels_1.extend(predictions.detach().numpy())
    for epoch in range(epochs):
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer1.zero_grad()
            loss, predictions = module2(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer1.step()
            if (epoch + 1) == epochs:
                indexs_2.extend(inputs.numpy())
                labels_2.extend(predictions.detach().numpy())
    for test_data, test_label in test_loader:
        _, predictions = module2(test_data, test_label)
        indexs_2.extend(test_data.numpy())
        labels_2.extend(predictions.detach().numpy())
    print("test_data: {0}".format(test_data.numpy()))
    print("label: {0}".format(test_label.numpy()))
    A_star1 = numpy.zeros([N, N])
    for i, index in enumerate(indexs_1):
        A_star1[index[0]][index[1]] = labels_1[i]
    A_star1 = numpy.array([round(number, 4) for row in A_star1 for number in row]).reshape([N, N])
    A_star2 = numpy.zeros([N, N])
    for i, index in enumerate(indexs_2):
        A_star2[index[0]][index[1]] = labels_2[i]
    A_star2 = numpy.array([round(number, 4) for row in A_star2 for number in row]).reshape([N, N])
    write_matrix(A_star1, r'C:\Users\mihao\Desktop\米昊的东西\result\A_star1.txt')
    write_matrix(A_star2, r'C:\Users\mihao\Desktop\米昊的东西\result\A_star2.txt')


# GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
class MihGNNEmbeddingTest2(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay):
        super(MihGNNEmbeddingTest2, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(A_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)
        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
        self.relu = nn.ReLU()

    def forward(self, *input):
        edges = input[0]
        labels = input[1]
        batch_size = len(edges)
        src = [edge[0] for edge in edges]  # [batch_size, ]
        dst = [edge[1] for edge in edges]  # [batch_size, ]
        src = torch.tensor(src, dtype=torch.long)
        dst = torch.tensor(dst, dtype=torch.long)

        neighbors_src = self.A_s.index_select(dim=0, index=src)
        neighbors_dst = self.A_s.index_select(dim=0, index=dst)
        embedding_states_src = torch.matmul(neighbors_src, self.embedding_state)
        embedding_states_dst = torch.matmul(neighbors_dst, self.embedding_state)
        embedding_states_src_list = []
        embedding_states_dst_list = []
        current_embedding_states_src = embedding_states_src
        current_embedding_states_dst = embedding_states_dst
        for line in self.layer_lines:
            current_embedding_states_src = line(current_embedding_states_src)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list.append(current_embedding_states_src)
            current_embedding_states_dst = line(current_embedding_states_dst)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list.append(current_embedding_states_dst)
        embedding_states_src_list = torch.stack(embedding_states_src_list, dim=0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list, dim=0)
        # embedding_states_src = embedding_states_src / torch.sum(embedding_states_src)
        # embedding_states_dst = embedding_states_dst / torch.sum(embedding_states_dst)
        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = torch.tensor(math.e) ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss, predicts

def test2(A):
    train_loader, test_loader = getDataLoader(A, 0.8)
    N = A.shape[0]
    module = MihGNNEmbeddingTest2(A=A, N=N, d=128, layers=6, steps=2, delay=[1, 0.5])
    epochs = 20
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer.zero_grad()
            loss, _ = module(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

    devide_value = 0.0
    while (devide_value < 1.0):
        TP = 0
        FP = 0
        TN = 0
        FN = 0
        print('-------------devide value: {0}----------------'.format(str(devide_value)))
        for test_data, test_label in test_loader:
            _, Y = module(test_data, test_label)
            Y = Y.detach().numpy()
            labels = test_label.numpy()
            num = len(Y)
            for i in range(num):
                if Y[i] >= devide_value:
                    if labels[i] == 1:
                        TP = TP + 1
                    if labels[i] == 0:
                        FP = FP + 1
                if Y[i] < devide_value:
                    if labels[i] == 1:
                        FN = FN + 1
                    if labels[i] == 0:
                        TN = TN + 1
        print("TP: {0}".format(TP))
        print("FP: {0}".format(FP))
        print("TN: {0}".format(TN))
        print("FN: {0}".format(FN))
        devide_value = devide_value + 0.1


# GNN 使用余弦相似度作为目标函数
class MihGNNEmbeddingTest3(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay):
        super(MihGNNEmbeddingTest3, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(A_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)

        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)
        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))

        self.relu = nn.ReLU()

    def forward(self, edges):
        batch_size = len(edges)
        src = [edge[0] for edge in edges]  # [batch_size, ]
        dst = [edge[1] for edge in edges]  # [batch_size, ]
        src = torch.tensor(src, dtype=torch.long)
        dst = torch.tensor(dst, dtype=torch.long)
        neighbors_src = self.A_s.index_select(dim=0, index=src)
        neighbors_dst = self.A_s.index_select(dim=0, index=dst)
        embedding_states_src = torch.matmul(neighbors_src, self.embedding_state)
        embedding_states_src = self.layer_lines(embedding_states_src)
        # embedding_states_src = self.relu(embedding_states_src)
        embedding_states_dst = torch.matmul(neighbors_dst, self.embedding_state)
        embedding_states_dst = self.layer_lines(embedding_states_dst)
        # embedding_states_dst = self.relu(embedding_states_dst)
        # cal A_star
        cos_similary = embedding_states_src * embedding_states_dst
        cos_similary = torch.sum(cos_similary, dim=1).reshape([-1, 1])
        cos_similary = torch.norm(cos_similary, dim=1).reshape([-1, ])
        x_1 = torch.norm(embedding_states_src, dim=1)
        x_2 = torch.norm(embedding_states_dst, dim=1)
        cos_similary = cos_similary / (x_1 * x_2)
        similary = self.relu(cos_similary)
        return similary


def test3(A):
    train_loader, test_loader = getDataLoader(A, 0.8)
    N = A.shape[0]
    module = MihGNNEmbeddingTest3(A=A, N=N, d=20, layers=4, steps=2, delay=[1.0, 0.5])
    epochs = 20
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer.zero_grad()
            cos_similary = module(inputs)
            loss = torch.sum((labels - cos_similary) ** 2)
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

    devide_value = 0.0
    while (devide_value < 1.0):
        TP = 0
        FP = 0
        TN = 0
        FN = 0
        print('-------------devide value: {0}----------------'.format(str(devide_value)))
        for test_data, test_label in train_loader:
            cos_similary = module(test_data)
            cos_similary = cos_similary.detach().numpy()
            labels = test_label.numpy()

            num = len(cos_similary)
            for i in range(num):
                if cos_similary[i] >= devide_value:
                    if labels[i] == 1:
                        TP = TP + 1
                    if labels[i] == 0:
                        FP = FP + 1
                if cos_similary[i] < devide_value:
                    if labels[i] == 1:
                        FN = FN + 1
                    if labels[i] == 0:
                        TN = TN + 1
        print("TP: {0}".format(TP))
        print("FP: {0}".format(FP))
        print("TN: {0}".format(TN))
        print("FN: {0}".format(FN))
        devide_value = devide_value + 0.1


# GF
class MihGNNEmbeddingTest4(nn.Module):
    def __init__(self, N, d, lambda_1):
        super(MihGNNEmbeddingTest4, self).__init__()
        self.N = N
        self.d = d
        self.lambda_1 = lambda_1
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)

    def forward(self, *input):
        edges = input[0]
        labels = input[1]
        src = [edge[0] for edge in edges]  # [batch_size, ]
        src = torch.tensor(src, dtype=torch.long)
        dst = [edge[1] for edge in edges]  # [batch_size, ]
        dst = torch.tensor(dst, dtype=torch.long)
        embedding_states_src = self.embedding_state.index_select(dim=0, index=src)  # [batch_size, d]
        embedding_states_dst = self.embedding_state.index_select(dim=0, index=dst)  # [batch_size, d]
        Y = torch.mul(embedding_states_src, embedding_states_dst)  # [batch_size, d]
        Y = torch.sum(Y, dim=1)
        L = 0.5 * torch.sum((labels - Y) ** 2) + (self.lambda_1 / 2) * torch.norm(embedding_states_src, dim=1)
        L = torch.sum(L, dim=0)
        return L, Y


def test4(A):
    train_loader, test_loader = getDataLoader(A, 0.8)
    N = A.shape[0]
    module = MihGNNEmbeddingTest4(N=N, d=20, lambda_1=0.3)
    epochs = 20
    optimizer = optim.Adam(module.parameters(), lr=0.001)
    for epoch in range(epochs):
        running_loss = 0.0
        print("--------------epoch : {0} ------------------".format(epoch + 1))
        for i, data in enumerate(train_loader, 0):
            inputs, labels = data
            labels = labels.to(torch.long)
            optimizer.zero_grad()
            loss, _ = module(inputs, labels)
            loss.backward(retain_graph=True)
            optimizer.step()
            running_loss += loss.item()
            if i != 0 and i % 2000 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss))
                running_loss = 0.0

    devide_value = 0.0
    while (devide_value < 1.0):
        TP = 0
        FP = 0
        TN = 0
        FN = 0
        print('-------------devide value: {0}----------------'.format(str(devide_value)))
        for test_data, test_label in test_loader:
            _, Y = module(test_data, test_label)
            Y = Y.detach().numpy()
            labels = test_label.numpy()
            num = len(Y)
            for i in range(num):
                if Y[i] >= devide_value:
                    if labels[i] == 1:
                        TP = TP + 1
                    if labels[i] == 0:
                        FP = FP + 1
                if Y[i] < devide_value:
                    if labels[i] == 1:
                        FN = FN + 1
                    if labels[i] == 0:
                        TN = TN + 1
        print("TP: {0}".format(TP))
        print("FP: {0}".format(FP))
        print("TN: {0}".format(TN))
        print("FN: {0}".format(FN))
        devide_value = devide_value + 0.1


if __name__ == '__main__':
    test1_(A)
    # print("nodes:{0}".format(len(nodes)))
    # print("edges:{0}".format(len(edges)))