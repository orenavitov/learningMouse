'''
@Time: 2019/11/14 14:37
@Author: mih
@Des: 
'''

import numpy
from sklearn.metrics import roc_auc_score

# The Hub Promoted Index

def HPI(MatrixAdjacency_Train, MatrixAdjacency_Real):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score = []
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    for i in range(N):
        for j in range(N):
            if i != j:
                link_label.append(MatrixAdjacency_Real[i][j])
                temp = min(sum(MatrixAdjacency_Train[i]), sum(MatrixAdjacency_Train[j]))
                if (temp == 0):
                    link_score.append(0)
                else:
                    score = 2 * A_sqrt[i][j] / temp
                    link_score.append(score)
    auc = roc_auc_score(link_label, link_score)
    return auc