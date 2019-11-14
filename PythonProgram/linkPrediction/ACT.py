'''
@Time: 2019/11/14 14:35
@Author: mih
@Des: 
'''
import numpy
from sklearn.metrics import roc_auc_score

# AverageCommuteTime
# auc: 0.8987037529479779
def ACT(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    Matrix_D = numpy.diag(sum(MatrixAdjacency_Train))
    Matrix_Laplacian = Matrix_D - MatrixAdjacency_Train
    INV_Matrix_Laplacian = numpy.linalg.pinv(Matrix_Laplacian)
    # Matrix_similarity = numpy.zeros(MatrixAdjacency_Train.shape)
    for i in range(N):
        for j in range(N):
            if i != j:
                link_label.append(MatrixAdjacency_Train[i][j])
                score = 1 / (INV_Matrix_Laplacian[i][i] + INV_Matrix_Laplacian[j][j] -
                             2 * INV_Matrix_Laplacian[i][j])
                link_score.append(score)
    auc = roc_auc_score(link_label, link_score)
    return auc

def ACT_(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    Matrix_D = numpy.diag(sum(MatrixAdjacency_Train))
    Matrix_Laplacian = Matrix_D - MatrixAdjacency_Train
    INV_Matrix_Laplacian = numpy.linalg.pinv(Matrix_Laplacian)
    matrix_similarity = numpy.zeros(shape=(N, N), dtype=float)
    for i in range(N):
        for j in range(N):
            if i != j:
                temp = INV_Matrix_Laplacian[i][i] + INV_Matrix_Laplacian[j][j] - 2.0 * INV_Matrix_Laplacian[i][j]

                matrix_similarity[i][j] = 1.0 / temp
    threhold = numpy.sum(matrix_similarity)
    return matrix_similarity, threhold

# 有问题！
# auc: < 0.2

def Other_ACT(MatrixAdjacency_Train):

    Matrix_D = numpy.diag(sum(MatrixAdjacency_Train))
    Matrix_Laplacian = Matrix_D - MatrixAdjacency_Train
    INV_Matrix_Laplacian = numpy.linalg.pinv(Matrix_Laplacian)

    Array_Diag = numpy.diag(INV_Matrix_Laplacian)
    Matrix_ONE = numpy.ones([MatrixAdjacency_Train.shape[0], MatrixAdjacency_Train.shape[0]])
    Matrix_Diag = Array_Diag * Matrix_ONE

    Matrix_similarity = Matrix_Diag + Matrix_Diag.T - (2 * Matrix_Laplacian)

    Matrix_similarity = Matrix_ONE / Matrix_similarity
    Matrix_similarity = numpy.nan_to_num(Matrix_similarity)

    threhold = numpy.sum(Matrix_similarity)
    return Matrix_similarity, threhold