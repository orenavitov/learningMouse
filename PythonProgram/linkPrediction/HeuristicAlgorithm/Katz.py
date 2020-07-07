'''
@Time: 2019/11/14 14:35
@Author: mih
@Des: 
'''

import numpy
from Tools import auc
from Tools import process_gml_file

G, A, nodes, all_neighbors, As = process_gml_file(
        r"D:\ComplexNetworkData\Complex Network Datasets\For Link Prediction\metabolic\metabolic.gml")
node_number = len(nodes)

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

if __name__ == '__main__':
    A_similarity, threshold = Katz_(A)
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