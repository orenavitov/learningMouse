'''
@Time: 2019/11/14 14:35
@Author: mih
@Des: 
'''

import numpy
from linkPrediction.Main import auc

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

def Katz_(MatrixAdjacency_Train):
    # 影响因子, 影响因子越小auc越大， 当影响因子很小时其实就是CN
    parameter = 0.01
    identity_matrix = numpy.eye(MatrixAdjacency_Train.shape[0])
    temp_matrix = identity_matrix - parameter * MatrixAdjacency_Train
    inv_matrix = numpy.linalg.inv(temp_matrix)
    similarity_matrix = inv_matrix - identity_matrix
    threhold = numpy.sum(similarity_matrix)
    return similarity_matrix, threhold
