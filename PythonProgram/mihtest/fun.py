# Random Walk & Random Walk With ReStarting
import numpy
import math
import array

repert_time = 30
A = numpy.array([
    [0, 1, 1, 0, 1],
    [1, 0, 0, 1, 0],
    [1, 0, 0, 0, 1],
    [0, 1, 0, 0, 1],
    [1, 0, 1, 1, 0]
])
# A: 邻接矩阵
# p_x: 时间t， x点到各点的概率
def Random_Walk(A, p_x):
    A = numpy.array([row / sum(row) for row in A])
    for i in range(repert_time):
        p_x = numpy.matmul(A.T, p_x)
        print("the {0} time, the p_x is {1}".format(i + 1, p_x))


# A: 邻接矩阵
# p_x: 时间t， x点到各点的概率
# c: 不直接回到起点， 往下走的概率
def Random_Walk_Restarting(A, p_x, c):
    A = numpy.array([row / sum(row) for row in A])
    for i in range(repert_time):
        p_x = c * numpy.matmul(a = A, b = p_x) + (1 - c) * numpy.array(p_x)
        print("the {0} time, the p_x is {1}".format(i + 1, p_x))
    pass;


if __name__ == '__main__':
    # 假设均从第一个节点开始
    p_x = numpy.array([1, 0, 0, 0, 0])
    c = 0.8
    Random_Walk(A, p_x)
    # p_x_ = [1, 0, 0, 0, 0]
    # Random_Walk_Restarting(A, p_x_, c)
