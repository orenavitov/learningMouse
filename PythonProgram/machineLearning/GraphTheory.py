import numpy
import array
from sklearn.metrics import roc_auc_score

def find_neighbors(A, node):
    neighbors = array.array("i", [])
    for i in range(len(A[node - 1])):
        if A[node - 1][i] == 1:
            neighbors.append(i + 1)

def proper_data(file):
    #
    v = array.array("i", [])
    line_count = 0
    edge_count = 0
    with open(file, "r") as f:
        # 第一次读取文件
        for line in f.readlines():
            if (len(line) > 0):
                line_count += 1
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if srcNode not in v:
                    v.append(srcNode)
                if dstNode not in v:
                    v.append(dstNode)
        A = numpy.zeros(shape=(len(v), len(v)))
        f.seek(0, 0)
        # 第二次读取文件
        for line in f.readlines():
            if (len(line) > 0):
                srcNode, dstNode, weight = line.split(" ")
                srcNode = int(srcNode)
                dstNode = int(dstNode)
                if (A[srcNode - 1][dstNode - 1] != 1):
                    edge_count += 1
                    A[srcNode - 1][dstNode - 1] = 1
                    A[dstNode - 1][srcNode - 1] = 1
        return A
def random_walk():
    D = numpy.diag([1 / 2, 1 / 2, 1, 1])
    print("D:{0}".format(D))
    A = numpy.array([
        [0, 1, 0, 1],
        [1, 0, 1, 0],
        [0, 1, 0, 0],
        [1, 0, 0, 0]
    ])
    print("A:{0}".format(A))
    P_0 = numpy.array([
        1, 0, 0, 0
    ])
    M = numpy.matmul(D, A)
    print("M:{0}".format(M))
    M_T = M.T
    for i in range(4):
        print("P_{0}:".format(i + 1))
        print(numpy.matmul(M_T, P_0))
        M_T = numpy.matmul(M_T, M_T)


def AA(MatrixAdjacency_Train):
    # 节点数
    N = MatrixAdjacency_Train.shape[0]
    A_sqrt = numpy.matmul(MatrixAdjacency_Train, MatrixAdjacency_Train)
    A_score = A_sqrt - MatrixAdjacency_Train
    # link标签， 存在为1， 不存在为0
    link_label = []
    # link得分
    link_score =[]
    for i in range(N):
        for j in range(N):
            if (i != j):
                link_label.append(MatrixAdjacency_Train[i][j])
                link_score.append(A_score[i][j])

    auc = roc_auc_score(link_label, link_score)
    return auc

if __name__ == '__main__':
    A = proper_data(r"C:\Users\mihao\Desktop\bio-CE-GT.edges")
    print(AA(A))