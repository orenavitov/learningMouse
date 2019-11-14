'''
@Time: 2019/11/14 14:33
@Author: mih
@Des: 
'''

import math
import numpy
from sklearn.metrics import roc_auc_score
from linkPrediction.Main import find_neighbors

neighbors = []
commend_neighbors = []
edge_number = 0
node_number = 0

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
    threshold = numpy.sum(Matrix_similarity) / (node_number * node_number - node_number)
    return Matrix_similarity, threshold

