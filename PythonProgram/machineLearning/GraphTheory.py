import numpy
import array
import math
from sklearn.metrics import roc_auc_score

neighbors = []
commend_neighbors = []

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

def find_neighbors(A, node):
    for i in range(len(A[node - 1])):
        if A[node - 1][i] == 1:
            neighbors.append(i + 1)


def proper_data(file):
    #
    v = array.array("i", [])
    line_count = 0
    edge_count = 0
    with open(file, "r") as f:
        # 第一次读取文件
        for line in f.readlines():
            if (len(line) > 0):
                line_count += 1
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if srcNode not in v:
                    v.append(srcNode)
                if dstNode not in v:
                    v.append(dstNode)
        A = numpy.zeros(shape=(len(v), len(v)))
        f.seek(0, 0)
        # 第二次读取文件
        for line in f.readlines():
            if (len(line) > 0):
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if (A[srcNode - 1][dstNode - 1] != 1):
                    edge_count += 1
                    A[srcNode - 1][dstNode - 1] = 1
                    A[dstNode - 1][srcNode - 1] = 1
        return A


# Random_walk
# auc: 0.9938454563903012
@auc
def RW(MatrixAdjacency_Train):
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    Matrix_similarity = numpy.zeros(M.shape)
    # 只计算3步可以到达的情况
    for i in range(3):
        Matrix_similarity = Matrix_similarity + M
        M = numpy.matmul(M, M)
    return Matrix_similarity

@auc
def Other_RW(MatrixAdjacency_Train):
    RA_Train = sum(MatrixAdjacency_Train)
    RA_Train.shape = (RA_Train.shape[0], 1)
    MatrixAdjacency_Train_Log = MatrixAdjacency_Train / RA_Train
    MatrixAdjacency_Train_Log = numpy.nan_to_num(MatrixAdjacency_Train_Log)
    Matrix_similarity = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train_Log)
    return Matrix_similarity


# CommonNeighbors
# auc: 0.8021184857335069
@auc
def CN(MatrixAdjacency_Train):
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    Matrix_similarity = A_sqrt - MatrixAdjacency_Train
    return Matrix_similarity


# Adamic-Adar Index
# auc: 0.9626856751933148
def find_neighbors_between_two_nodes(MatrixAdjacency_Train, i, j):
    line_i = MatrixAdjacency_Train[i]
    line_j = MatrixAdjacency_Train[j]
    for index in range(len(line_i)):
        if line_i[index] == 1 and line_j[index] == 1:
            commend_neighbors.append(index + 1)


def AA(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    for i in range(N):
        for j in range(N):
            if (i != j):
                link_label.append(MatrixAdjacency_Train[i][j])
                if (A_sqrt[i][j] != 0):
                    score = 0.0
                    find_neighbors_between_two_nodes(MatrixAdjacency_Train, i, j)
                    for neighbor in commend_neighbors:
                        find_neighbors(MatrixAdjacency_Train, neighbor)
                        if len(neighbors) != 0:
                            score = score + 1 / (math.log(len(neighbors), 2))
                        neighbors.clear()
                    link_score.append(score)
                    commend_neighbors.clear()
                    print("{0}:{1} {2}".format(i, j, score))
                else:
                    link_score.append(0.0)
                    print("{0}:{1} {2}".format(i, j, 0.0))
    auc = roc_auc_score(link_label, link_score)
    return auc


def Other_AA(MatrixAdjacency_Train):
    logTrain = numpy.log(sum(MatrixAdjacency_Train))
    logTrain = numpy.nan_to_num(logTrain)
    logTrain.shape = (logTrain.shape[0], 1)
    MatrixAdjacency_Train_Log = MatrixAdjacency_Train / logTrain
    MatrixAdjacency_Train_Log = numpy.nan_to_num(MatrixAdjacency_Train_Log)
    Matrix_similarity = numpy.dot(MatrixAdjacency_Train, MatrixAdjacency_Train_Log)
    return Matrix_similarity


# Katz
@auc
def Katz(MatrixAdjacency_Train):
    # 影响因子, 影响因子越小auc越大， 当影响因子很小时其实就是CN
    parameter = 0.01
    identity_matrix = numpy.eye(MatrixAdjacency_Train.shape[0])
    temp_matrix = identity_matrix - parameter * MatrixAdjacency_Train
    inv_matrix = numpy.linalg.inv(temp_matrix)
    similarity_matrix = inv_matrix - identity_matrix
    return similarity_matrix


# AverageCommuteTime
# auc: 0.8987037529479779
def ACT(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    Matrix_D = numpy.diag(sum(MatrixAdjacency_Train))
    Matrix_Laplacian = Matrix_D - MatrixAdjacency_Train
    INV_Matrix_Laplacian = numpy.linalg.pinv(Matrix_Laplacian)
    # Matrix_similarity = numpy.zeros(MatrixAdjacency_Train.shape)
    for i in range(N):
        for j in range(N):
            if i != j:
                link_label.append(MatrixAdjacency_Train[i][j])
                score = 1 / (INV_Matrix_Laplacian[i][i] + INV_Matrix_Laplacian[j][j] -
                             2 * INV_Matrix_Laplacian[i][j])
                link_score.append(score)
    auc = roc_auc_score(link_label, link_score)
    return auc

# The Hub Promoted Index
# auc:0.9312839848633754
def HPI(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    for i in range(N):
        for j in range(N):
            if i != j:
                link_label.append(MatrixAdjacency_Train[i][j])
                score = 2 * A_sqrt[i][j] / min(sum(MatrixAdjacency_Train[i]), sum(MatrixAdjacency_Train[j]))
                link_score.append(score)
    auc = roc_auc_score(link_label, link_score)
    return auc


# TheHubDepressed Index
# auc: 0.9210816798183674
def HDI(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    for i in range(N):
        for j in range(N):
            if i != j:
                link_label.append(MatrixAdjacency_Train[i][j])
                score = 2 * A_sqrt[i][j] / max(sum(MatrixAdjacency_Train[i]), sum(MatrixAdjacency_Train[j]))
                link_score.append(score)
    auc = roc_auc_score(link_label, link_score)
    return auc


if __name__ == '__main__':
    A = proper_data(r"C:\Users\mih\Desktop\bio-CE-GT.edges")
    print(HDI(A))

