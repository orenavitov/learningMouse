'''
@Time: 2019/11/14 14:32
@Author: mih
@Des: 
'''

import numpy
from sklearn.metrics import roc_auc_score

def CN(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
    Matrix_similarity = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    # Matrix_similarity = A_sqrt - MatrixAdjacency_Train
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            if i == j:
                continue
            link_score.append(Matrix_similarity[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

