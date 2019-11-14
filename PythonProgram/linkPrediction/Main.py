'''
@Time: 2019/11/14 14:29
@Author: mih
@Des: 
'''
import numpy
import networkx
import array
from sklearn.metrics import roc_auc_score

neighbors = []
commend_neighbors = []
edge_number = 0
node_number = 0
CN_threshold = 1

# Auc 计算
def auc(func):
    def process(A):
        # 节点数
        N = A.shape[0]
        # link标签， 存在为1， 不存在为0
        link_label = []
        # link得分
        link_score = []
        Matrix_similarity = func(A)
        for i in range(N):
            for j in range(N):
                if i != j:
                    link_label.append(A[i][j])
                    link_score.append(Matrix_similarity[i][j])
        auc = roc_auc_score(link_label, link_score)
        return auc
    return process

# 准确率计算
def precision(A, Matrix_similarity, train_A, threshold):
    # 节点数
    N = A.shape[0]
    i = 0
    j = i + 1
    T_P = 0
    T_N = 0
    F_P = 0
    F_N = 0
    while (i < N):
        while (j < N):
            if (train_A[i][j] != 1 and Matrix_similarity[i][j] >= threshold):
                if A[i][j] == 1:
                    T_P += 1
                else:
                    T_N += 1
            if (train_A[i][j] != 1 and Matrix_similarity[i][j] < threshold):
                if A[i][j] == 1:
                    F_P += 1
                else:
                    F_N += 1
            if (train_A[i][j] == 1 and Matrix_similarity[i][j] >= threshold):
                T_P += 1
            if (train_A[i][j] == 1 and Matrix_similarity[i][j] < threshold):
                F_P += 1
            j += 1
        i += 1
        j = i + 1
    return T_P, T_N, F_P, F_N

def find_neighbors(A, node):
    for i in range(len(A[node - 1])):
        if A[node - 1][i] == 1:
            neighbors.append(i + 1)

def proper_data(file):
    #
    v = array.array("i", [])
    global node_number
    global edge_number
    with open(file, "r") as f:
        # 第一次读取文件
        for line in f.readlines():
            if (len(line) > 0):
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if srcNode not in v:
                    v.append(srcNode)
                if dstNode not in v:
                    v.append(dstNode)
        node_number =len(v)
        A = numpy.zeros(shape=(len(v), len(v)))
        f.seek(0, 0)
        # 第二次读取文件
        for line in f.readlines():

            if (len(line) > 0):
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if (A[srcNode - 1][dstNode - 1] != 1):
                    edge_number += 1
                    A[srcNode - 1][dstNode - 1] = 1
                    A[dstNode - 1][srcNode - 1] = 1
        return A

def get_train_test_data(A):
    # 节点数
    N = A.shape[0]

    train_A = numpy.zeros(shape=(N, N))
    i = 0
    j = i + 1
    while(i < N):
        while (j < N):
            rand_number = numpy.random.random() + 0.1;
            if rand_number <= 0.4 and A[i][j] == 1:
                train_A[i][j] = 0
                train_A[j][i] = 0
            else:
                train_A[i][j] = A[i][j]
                train_A[j][i] = A[j][i]
            j += 1
        i += 1
        j = i + 1

    return train_A

if __name__ == '__main__':
    A = proper_data(r"C:\Users\mih\Desktop\文件\bio-CE-GT.edges")

    # auc = Other_ACT(A);
    # print("the auc is : {0}".format(auc));
    # trainA = get_train_test_data(A)
    # Matrix_similarity, threshold = Other_ACT(trainA)
    # T_P, T_N, F_P, F_N = precision(A, Matrix_similarity, trainA, 0.001)
    # print("Threshold:{0}".format(threshold))
    # print("TP:{0}".format(T_P))
    # print("TN:{0}".format(T_N))
    # print("FP:{0}".format(F_P))
    # print("FN:{0}".format(F_N))