'''
@Time: 2019/11/14 14:35
@Author: mih
@Des: 
'''

import numpy
from sklearn.metrics import roc_auc_score

# Katz
def Katz(MatrixAdjacency_Train, MatrixAdjacency_Real):
    N = MatrixAdjacency_Train.shape[0]
    # 影响因子, 影响因子越小auc越大， 当影响因子很小时其实就是CN
    parameter = 0.01
    identity_matrix = numpy.eye(MatrixAdjacency_Train.shape[0])
    temp_matrix = identity_matrix - parameter * MatrixAdjacency_Train
    inv_matrix = numpy.linalg.inv(temp_matrix)
    Matrix_similarity = inv_matrix - identity_matrix
    link_label = []
    link_score = []
    for i in range(N):
        for j in range(N):
            link_score.append(Matrix_similarity[i][j])
            link_label.append(MatrixAdjacency_Real[i][j])
    auc = roc_auc_score(link_label, link_score)
    return auc

def Katz_(MatrixAdjacency_Train):
    # 影响因子, 影响因子越小auc越大， 当影响因子很小时其实就是CN
    parameter = 0.01
    identity_matrix = numpy.eye(MatrixAdjacency_Train.shape[0])
    temp_matrix = identity_matrix - parameter * MatrixAdjacency_Train
    inv_matrix = numpy.linalg.inv(temp_matrix)
    similarity_matrix = inv_matrix - identity_matrix
    threhold = numpy.sum(similarity_matrix)
    return similarity_matrix, threhold

