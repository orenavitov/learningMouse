'''
@Time: 2019/11/14 14:33
@Author: mih
@Des: 
'''

import numpy
from sklearn.metrics import roc_auc_score

def AA(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
    logTrain = numpy.log(sum(MatrixAdjacency_Train))
    logTrain = numpy.nan_to_num(logTrain)
    logTrain.shape = (logTrain.shape[0], 1)
    MatrixAdjacency_Train_Log = numpy.zeros_like(MatrixAdjacency_Train)
    # MatrixAdjacency_Train_Log = MatrixAdjacency_Train / logTrain
    # MatrixAdjacency_Train_Log = numpy.nan_to_num(MatrixAdjacency_Train_Log)
    for i in range(N):
        for j in range(N):
            if(logTrain[i] == 0):
                MatrixAdjacency_Train_Log[i][j] = 0
            else:
                MatrixAdjacency_Train_Log[i][j] = MatrixAdjacency_Train[i][j] / logTrain[i]

    Matrix_similarity = numpy.dot(MatrixAdjacency_Train, MatrixAdjacency_Train_Log)
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(Matrix_similarity[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

