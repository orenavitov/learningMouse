'''
@Time: 2019/11/14 14:18
@Author: mih
@Des: 
'''

import numpy
from Tools import auc
from Tools import process_gml_file

G, A, edges, nodes, neighbors = process_gml_file(
        r"D:\ComplexNetworkData\Complex Network Datasets\For Link Prediction\metabolic\metabolic.gml")
node_number = len(nodes)


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

def RW_(MatrixAdjacency_Train):
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    Matrix_similarity = numpy.eye(N = M.shape[0], dtype=float)
    # 只计算3步可以到达的情况
    for i in range(3):
        Matrix_similarity = numpy.matmul(M.T, Matrix_similarity)
        # Matrix_similarity = Matrix_similarity + M
        # M = numpy.matmul(M, M)
    threshold = numpy.sum(Matrix_similarity) / (node_number * node_number - node_number)
    return Matrix_similarity, threshold

def RWR(MatrixAdjacency_Train):
    # 不返回起始点， 继续下一步的概率
    alpha = 0.6
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    one_matrix = numpy.eye(N=M.shape[0], dtype=float)
    Matrix_similarity = numpy.eye(N=M.shape[0], dtype=float)
    result = numpy.zeros(shape=M.shape[0], dtype=float)
    # 同样只计算3步以内的情况
    for i in range(3):
        Matrix_similarity = alpha * numpy.matmul(M.T, Matrix_similarity) + (1 - alpha) * one_matrix
        result = result + Matrix_similarity
    threshold = numpy.sum(Matrix_similarity) / (node_number * node_number - node_number)
    return result, threshold

def RW_Continuity(MatrixAdjacency_Train):
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    Matrix_similarity = numpy.eye(N = M.shape[0], dtype=float)
    result = numpy.zeros(shape=M.shape[0], dtype=float)
    # 只计算3步可以到达的情况
    for i in range(3):
        Matrix_similarity = numpy.matmul(M.T, Matrix_similarity)
        result = result + Matrix_similarity
        # Matrix_similarity = Matrix_similarity + M
        # M = numpy.matmul(M, M)
    threshold = numpy.sum(Matrix_similarity) / (node_number * node_number - node_number)
    return result, threshold

@auc
def Other_RW(MatrixAdjacency_Train):
    RA_Train = sum(MatrixAdjacency_Train)
    RA_Train.shape = (RA_Train.shape[0], 1)
    MatrixAdjacency_Train_Log = MatrixAdjacency_Train / RA_Train
    MatrixAdjacency_Train_Log = numpy.nan_to_num(MatrixAdjacency_Train_Log)
    Matrix_similarity = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train_Log)
    return Matrix_similarity

if __name__ == '__main__':
    A_similarity, threshold = RWR(A)
    TP = 0
    TN = 0
    FP = 0
    FN = 0

    for row in range(node_number):
        for column in range(node_number):
            if (row != column):
                if (A_similarity[row][column] > threshold):
                    if (A[row][column] == 1):
                        TP = TP + 1
                    if (A[row][column] == 0):
                        TN = TN + 1
                if (A_similarity[row][column] <= threshold):
                    if (A[row][column] == 1):
                        FP = FP + 1
                    if (A[row][column] == 0):
                        FN = FN + 1
    right_number = TP + FN
    right_radio = right_number / (node_number ** 2 - node_number)

    print("TP: {0}\n".format(TP))
    print("TN: {0}\n".format(TN))
    print("FP: {0}\n".format(FP))
    print("FN: {0}\n".format(FN))
    print("准确率： {0}%".format(100 * right_radio))