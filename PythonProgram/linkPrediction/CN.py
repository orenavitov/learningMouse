'''
@Time: 2019/11/14 14:32
@Author: mih
@Des: 
'''

from linkPrediction.Main import auc
import numpy

neighbors = []
commend_neighbors = []
edge_number = 0
node_number = 0

# CommonNeighbors
# auc: 0.8021184857335069
@auc
def CN(MatrixAdjacency_Train):
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    Matrix_similarity = A_sqrt - MatrixAdjacency_Train
    return Matrix_similarity

def C_N(MatrixAdjacency_Train):
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    Matrix_similarity = A_sqrt - MatrixAdjacency_Train
    threshold = numpy.sum(Matrix_similarity) / (node_number * node_number - node_number)
    return Matrix_similarity, threshold