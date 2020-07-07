'''
@Time: 2019/11/14 14:32
@Author: mih
@Des: 
'''

from Tools import auc
import numpy
from Tools import process_gml_file


G, A, nodes, all_neighbors, As = process_gml_file(
        r"D:\ComplexNetworkData\Complex Network Datasets\For Link Prediction\metabolic\metabolic.gml")
node_number = len(nodes)
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

if __name__ == '__main__':
    A_similarity, threshold = C_N(A)
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