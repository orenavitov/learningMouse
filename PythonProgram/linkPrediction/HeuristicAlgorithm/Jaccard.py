'''
@Time: 2019/11/14 14:39
@Author: mih
@Des: 
'''

import numpy

# 没试
# Jaccard
def Jaccavrd(MatrixAdjacency_Train):
    Matrix_similarity = numpy.dot(MatrixAdjacency_Train, MatrixAdjacency_Train)
    deg_row = sum(MatrixAdjacency_Train)
    deg_row.shape = (deg_row.shape[0], 1)
    deg_row_T = deg_row.T
    tempdeg = deg_row + deg_row_T
    temp = tempdeg - Matrix_similarity

    Matrix_similarity = Matrix_similarity / temp

    return Matrix_similarity
