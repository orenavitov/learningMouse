'''
@Time: 2019/12/25 19:29
@Author: mih
@Des: 
'''
import scipy.sparse as sp
import numpy
import networkx
import matplotlib.pyplot as plt
import json
from copy import deepcopy
from copy import copy
from Tools import get_test_matrix
from Tools import process_gml_file
import torch
from NN import LineNetwork
import math
# 定义一张用于测试的图, A表示改图的邻接矩阵
A = numpy.array([
    [0, 1, 1, 1, 0, 0, 0],
    [1, 0, 1, 0, 0, 0, 1],
    [1, 1, 0, 0, 1, 0, 1],
    [1, 0, 0, 0, 1, 1, 0],
    [0, 0, 1, 1, 0, 1, 0],
    [0, 0, 0, 1, 1, 0, 1],
    [0, 1, 1, 0, 0, 1, 0]
])
# 根据图的邻接矩阵绘制改图
def draw_graph(A):
    # 节点数
    N = len(A)
    nodes = [i + 1 for i in range(N)]
    edges = []
    for i in range(N):
        j = i
        while(j < N):
            if A[i][j] == 1:
                edges.append((i + 1, j + 1))
            j = j + 1
    # 绘制无向图
    G = networkx.Graph()
    G.add_nodes_from(nodes)
    G.add_edges_from(edges)
    for edge in G.edges:
        print(edge)
    networkx.draw(G, with_labels=True)
    plt.show()

def Test3():
    G = networkx.Graph()
    G.add_edges_from([[1, 2], [2, 1]])
    for edge in G.edges:
        print(edge)
def Test1():
    A_ = sp.csr_matrix(A, dtype=int)
    print(A_)
def Test2():
    A_ = numpy.array(A).flatten()
    print(A_)

def JsonRead():
    with open(r"./params.json", 'r') as f:
        params = json.load(f)
        batchSize = params["batchSize"]
        epochs = params["epochs"]
        print("batchSize: {0}".format(batchSize))
        print("epochs: {0}".format(epochs))

def Test4():
    c_A = copy(A)
    c_A[0][0] = 1
    print(A)
    print(c_A)
    file = r"C:\Users\mihao\Desktop\米昊的东西\result_matrix.txt"
    with open(file, 'w') as file:
        for row in A:
            file.write(str(row))
            file.write('\n')

def Test5():
    _A = get_test_matrix(A, 0.8)
    print(_A)

def Test6():
    G, X, edges, nodes, neighbors = process_gml_file(
        r"C:\Users\mihao\Desktop\米昊的东西\input.gml")
    A = []
    with open(r"C:\Users\mihao\Desktop\米昊的东西\X_hat_file.txt", "r") as file:
        context = file.read()
        rows = context.split("]")
        for row in rows:
            if row == '':
                continue
            a = []
            print("row: {0}".format(row))
            _row = row.split('[')[1]
            _row = list(_row.split(" "))
            for element in _row:
                if element == '':
                    continue
                if element.endswith("\n"):
                    a.append(float(element[:-1]))
                    continue
                a.append(float(element))
            A.append(a)
    A = numpy.array(A)
    shape = A.shape
    rows = shape[0]
    cols = shape[1]
    for row in range(rows):
        for col in range(cols):
            if A[row][col] >= 0.01:
                A[row][col] = 1
            else:
                A[row][col] = 0
    TP = 0
    TN = 0
    FP = 0
    FN = 0
    for row in range(rows):
        for col in range(cols):
            if A[row][col] == 1 and X[row][col] == 1:
                TP = TP + 1
            if A[row][col] == 1 and X[row][col] == 0:
                FP = TN + 1
            if A[row][col] == 0 and X[row][col] == 1:
                FN = FP + 1
            if A[row][col] == 0 and X[row][col] == 0:
                TN = FN + 1
    print("TP: {0}".format(TP))
    print("TN: {0}".format(TN))
    print("FP: {0}".format(FP))
    print("FN: {0}".format(FN))
    print("精确率: {0}".format(TP / (TP + FP)))

def Test7():
    A = numpy.array(range(9)).reshape([3, 3])
    print("A:{0}".format(A))

    D = numpy.diag([numpy.sum(row) for row in A])
    print("D:{0}".format(D))

    A = torch.tensor(A, dtype = torch.float)
    D = torch.tensor(D, dtype = torch.float)

    reuslt1 = A * D
    result2 = torch.mul(A, D)
    result3 = torch.mm(A, D)
    result4 = torch.matmul(A, D)
    print("result1: {0}".format(reuslt1))
    print("result2: {0}".format(result2))
    print("result3: {0}".format(result3))
    print("result4: {0}".format(result4))

def Test8():
    ln = LineNetwork(6, 6, 6)
    file = r'C:\Users\mihao\Desktop\米昊的东西\train\ln.pkl'
    torch.save(ln.state_dict(), file)

def Test9():
    a = torch.tensor([
        [1, 2],
        [3, 4]
    ],dtype = torch.float)
    b = torch.tensor([
        [1, 2],
        [3, 4]
    ], dtype = torch.float)
    c = torch.cat([a, b], dim = 0)
    print(c)

def Test10():
    a = numpy.random.randn(3, 3)
    a = round(a, 2)
    print(a)


# https://www.cnblogs.com/wanglle/p/11455758.html
#
if __name__ == '__main__':
    Test10()