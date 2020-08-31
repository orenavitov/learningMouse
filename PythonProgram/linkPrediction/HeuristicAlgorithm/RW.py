'''
@Time: 2019/11/14 14:18
@Author: mih
@Des: 
'''

import numpy
from Tools import auc
from sklearn.metrics import roc_auc_score

# Random_walk
def RW(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
    # MatrixAdjacency_Train_D = sum(MatrixAdjacency_Train)
    # M = numpy.zeros_like(MatrixAdjacency_Train)
    # for i in range(N):
    #     for j in range(N):
    #         if (MatrixAdjacency_Train_D[i] == 0):
    #
    #             M[i][j] = 0
    #         else:
    #             M[i][j] = MatrixAdjacency_Train[i][j] / MatrixAdjacency_Train_D[i]
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    Matrix_similarity = numpy.zeros(M.shape)
    # 只计算3步可以到达的情况
    for i in range(3):
        Matrix_similarity = Matrix_similarity + M
        M = numpy.matmul(M, M)
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(Matrix_similarity[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

def RW_(MatrixAdjacency_Train):
    M = MatrixAdjacency_Train / sum(MatrixAdjacency_Train)
    M = numpy.nan_to_num(M)
    Matrix_similarity = numpy.eye(N = M.shape[0], dtype=float)
    # 只计算3步可以到达的情况
    for i in range(3):
        Matrix_similarity = numpy.matmul(M.T, Matrix_similarity)
        # Matrix_similarity = Matrix_similarity + M
        # M = numpy.matmul(M, M)
    return Matrix_similarity

def RWR(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
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
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(result[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

def RW_Continuity(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
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
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(result[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

@auc
def Other_RW(MatrixAdjacency_Train):
    RA_Train = sum(MatrixAdjacency_Train)
    RA_Train.shape = (RA_Train.shape[0], 1)
    MatrixAdjacency_Train_Log = MatrixAdjacency_Train / RA_Train
    MatrixAdjacency_Train_Log = numpy.nan_to_num(MatrixAdjacency_Train_Log)
    Matrix_similarity = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train_Log)
    return Matrix_similarity