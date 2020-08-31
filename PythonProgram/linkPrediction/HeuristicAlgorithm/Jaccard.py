'''
@Time: 2019/11/14 14:39
@Author: mih
@Des: 
'''

import numpy
from sklearn.metrics import roc_auc_score


# Jaccard
def jaccard(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
    Matrix_similarity = numpy.dot(MatrixAdjacency_Train, MatrixAdjacency_Train)
    deg_row = sum(MatrixAdjacency_Train)
    deg_row.shape = (deg_row.shape[0], 1)
    deg_row_T = deg_row.T
    tempdeg = deg_row + deg_row_T
    temp = tempdeg - Matrix_similarity
    Matrix_similarity = Matrix_similarity / temp
    Matrix_similarity = numpy.nan_to_num(Matrix_similarity)
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(Matrix_similarity[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc
