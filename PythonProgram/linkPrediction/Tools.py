'''
@Time: 2019/11/14 14:29
@Author: mih
@Des: 
'''
import numpy
import array
from sklearn.metrics import roc_auc_score
import networkx
from copy import deepcopy

commend_neighbors = []
edge_number = 0
node_number = 0
CN_threshold = 1

# Auc 计算
def auc(func):
    def process(A):
        # 节点数
        N = A.shape[0]
        # link标签， 存在为1， 不存在为0
        link_label = []
        # link得分
        link_score = []
        Matrix_similarity = func(A)
        for i in range(N):
            for j in range(N):
                if i != j:
                    link_label.append(A[i][j])
                    link_score.append(Matrix_similarity[i][j])
        auc = roc_auc_score(link_label, link_score)
        return auc
    return process

# 准确率计算
def precision(A, Matrix_similarity, train_A, threshold):
    # 节点数
    N = A.shape[0]
    i = 0
    j = i + 1
    T_P = 0
    T_N = 0
    F_P = 0
    F_N = 0
    while (i < N):
        while (j < N):
            if (train_A[i][j] != 1 and Matrix_similarity[i][j] >= threshold):
                if A[i][j] == 1:
                    T_P += 1
                else:
                    T_N += 1
            if (train_A[i][j] != 1 and Matrix_similarity[i][j] < threshold):
                if A[i][j] == 1:
                    F_P += 1
                else:
                    F_N += 1
            if (train_A[i][j] == 1 and Matrix_similarity[i][j] >= threshold):
                T_P += 1
            if (train_A[i][j] == 1 and Matrix_similarity[i][j] < threshold):
                F_P += 1
            j += 1
        i += 1
        j = i + 1
    return T_P, T_N, F_P, F_N

def process_gml_file(file):
    G = networkx.read_gml(file)
    A = numpy.array(networkx.adjacency_matrix(G).todense())
    edges = G.edges()
    nodes = G.nodes()
    neighbors = {}
    for node in nodes:
        neighbors[node] = G[node]
    return G, A, edges, nodes, neighbors

# 生成GML文件
def generate_gml_file(srcfile, dstfile):
    G = networkx.Graph()
    edges = []
    with open(srcfile, "r") as file:
        for line in file.readlines():
            src = int(line.split(" ")[0])
            dst = int(line.split(" ")[1])
            edges.append((src, dst))
    G.add_edges_from(edges)
    networkx.write_gml(G, dstfile)

def find_common_neighbors(G, node1, node2):
    return networkx.common_neighbors(G, node1, node2)

def get_test_matrix(A, keep_radio):
    shape_A = A.shape
    c_A = deepcopy(A)
    _A = numpy.random.random(size=shape_A) - A
    row = shape_A[0]
    col = shape_A[1]
    for i in range(row):
        for j in range(col):
            if i != j and _A[i][j] > (keep_radio - 1):
                c_A[i][j] = 0
    return c_A

def write_matrix(A, file):
    with open(file, 'w') as file:
        for row in A:
            file.write(str(row))
            file.write('\n')

# 样本插值
def insert_process(self):
    pass

if __name__ == '__main__':
    generate_gml_file(r'C:\Users\mihao\Desktop\米昊的东西\dataset\petster-friendships-hamster\out.petster-friendships-hamster-uniq',
                      r'C:\Users\mihao\Desktop\米昊的东西\dataset\petster-friendships-hamster\hamster.gml')
