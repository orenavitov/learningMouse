'''
@Time: 2019/12/25 19:29
@Author: mih
@Des: 
'''
import scipy.sparse as sp
import numpy
import networkx
import matplotlib.pyplot as plt

from copy import copy
from Tools import get_test_matrix
import torch
from GraphEmbedding_RandomWalk.NN import LineNetwork
import openpyxl
from openpyxl.styles import PatternFill

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
# 随机生成一幅图
def Test6(N, density):
    nodes = [i for i in range(N)]
    edges = []
    for src in range(N):
        for dst in range(src + 1, N):
            randomValue = numpy.random.random()
            if (randomValue <= density):
                edges.append([src, dst])
    G = networkx.Graph()
    G.add_nodes_from(nodes)
    G.add_edges_from(edges)
    networkx.draw(G, with_labels=True)
    plt.show()
    A = numpy.array(networkx.adjacency_matrix(G).todense())
    return A

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

    a = numpy.arange(1, 11).reshape([2, 5])
    b = numpy.arange(10, 20).reshape([2, 5])
    s = [a, b]
    print("a:{0}".format(a))
    print("b:{0}".format(b))
    print(numpy.sum(s, axis = 0))
    print(numpy.sum(s, axis = 1))
def Test10():
    a = numpy.random.randn(3, 3)
    a = round(a, 2)
    print(a)


# https://www.cnblogs.com/wanglle/p/11455758.html
#
def Test11():

    numbers = numpy.random.randn(3, 3)
    red_fill = PatternFill("solid", fgColor="FF0000")
    excel_file = openpyxl.load_workbook(r'C:\Users\mihao\Desktop\米昊的东西\result\show.xlsx')
    if (excel_file['A_star1'] == None):
        excel_file.create_sheet("A_star1")
    A_star1_sheet = excel_file['A_star1']

    row_start = 1
    col_start = 1
    for col in range(1, 3 + 1):
        A_star1_sheet.cell(row = row_start, column = col + 1, value = col)
    for row in range(1, 3 + 1):
        A_star1_sheet.cell(row = row + 1, column = col_start, value = row)

    for row in range(3):
        for col in range(3):
            value = numbers[row][col]
            if value <= 0:
                A_star1_sheet.cell(row = row + 2, column = col + 2).fill = red_fill

            A_star1_sheet.cell(row = row + 2, column = col + 2, value = numbers[row][col])

    # 做了修改后要保存
    excel_file.save(r'C:\Users\mihao\Desktop\米昊的东西\result\show.xlsx')
    excel_file.close()

# 展示一维卷积的过程
# pytorch中一维tensor为（batch_size, channels, width）
# 卷积的过程和二维一样
def Test12():
    a = numpy.arange(start = 0, stop = 40)
    a = a.reshape([2, 4, 5])
    a = torch.tensor(a, dtype = torch.float)
    cov1d = torch.nn.Conv1d(in_channels = 4, out_channels = 2, kernel_size = 2)
    pool = torch.nn.MaxPool1d(kernel_size = 3, stride = 2)
    b = cov1d(a)
    print(b)
    print(b.shape)
    b = pool(b)
    print(b)
    print(b.shape)

def Test13():
    b = 2
    F_ = 4
    input = numpy.arange(0, 8).reshape([b, F_]);
    input = torch.tensor(input, dtype = torch.float)
    input1 = input.repeat(1, b)
    input2 = input1.view(b * b, -1)
    input3 = input.repeat(b, 1)
    print("input:\n {0}".format(input))
    print("input1:\n {0}".format(input1))
    print("input2:\n {0}".format(input2))
    print("input3:\n {0}".format(input3))

# unsqueeze(index) 会在tensor的index这个维度上增加一个维度， 大小为1；
# squeeze(index) 会去掉tensor的index这维度上大小为1的维度， 如果大小不为1， 不会发生变化；
def Test14():
    input = numpy.arange(0, 8).reshape([2, 4])
    input = torch.tensor(input, dtype = torch.float)
    print("input:\n{0}".format(input))
    input = input.unsqueeze(1)
    print("input:\n{0}".format(input))
    input = input.unsqueeze(1)
    print("input:\n{0}".format(input))
    input = input.squeeze(1)
    print("input:\n{0}".format(input))

def Test15():
    a = numpy.array([
        [1, 2],
        [2, 3],
        [3, 4]
    ])
    a = a - 1
    print(a)

if __name__ == '__main__':
    Test15()