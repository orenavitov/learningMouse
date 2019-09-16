# logistic回归
from math import *
from numpy import *
import matplotlib.pylab as plt

# 梯度上升算法
def loadDataSet():
    dataMat = []
    labelMat = []
    fr = open(r'E:\mihCode\learningMouse\PythonProgram\machineLearningData\machinelearninginaction\Ch05\testSet.txt')
    for line in fr.readlines():
        lineArr = line.strip().split()
        dataMat.append([1.0, float(lineArr[0]), float(lineArr[1])])
        labelMat.append(int(lineArr[2]))
    return dataMat, labelMat


def sigmoid(inX):
    return 1.0 / (1 + exp(-inX))


def gradAscent(dataMatIn, classLabels):
    # 将dataMatIn 和 classLabels转化成矩阵
    # mat（） 将列表转化成矩阵 transpose求矩阵的转置
    dataMatrix = mat(dataMatIn)
    labelMat = mat(classLabels).transpose()
    m, n = shape(dataMatrix)
    alpha = 0.001
    maxCycles = 500
    weights = ones((n, 1))
    for k in range(maxCycles):
        h = sigmoid(dataMatrix * weights)
        error = (labelMat - h)
        weights = weights + alpha * dataMatrix.transpose() * error
    return weights

def plotBestFit(weights):
    dataMat, labelMat = loadDataSet()
    dataArr = array(dataMat)
    n = shape(dataArr)[0]
    xcord1 = []
    ycord1 = []
    xcord2 = []
    ycord2 = []
    for i in range(n):
        if (int(labelMat[i]) == 1):
            xcord1.append(dataArr[i, 1])
            ycord1.append(dataArr[i, 2])
        else:
            xcord2.append(dataArr[i, 1])
            ycord2.append(dataArr[i, 2])
    fig = plt.figure()
    ax = fig.add_subplot(111)
    ax.scatter(xcord1, ycord1, s = 30, c = 'red', marker = 's')
    ax.scatter(xcord2, ycord2, s = 30, c = 'green')
    x = arange(-3.0, 3.0, 0.1)
    y = (-weights[0] - weights[1] * x) / weights[2]
    ax.plot(x, y)
    plt.xlabel('x1')
    plt.ylabel('x2')
    plt.show()


if(__name__ == '__main__'):
    print('test4')
    dataArr, labelMat = loadDataSet()
    weights = gradAscent(dataArr, labelMat)
    # weigths.getA()将weights由矩阵转成数组
    plotBestFit(weights.getA())