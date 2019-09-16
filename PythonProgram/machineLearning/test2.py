# 决策树算法
from math import log
import operator
import matplotlib.pyplot as plt


# 计算熵
def calcShannonEnt(dataSet):
    numEntries = len(dataSet)
    labelCounts = {}
    for featVec in dataSet:
        currentLabel = featVec[-1]
        if (currentLabel not in labelCounts):
            labelCounts[currentLabel] = 0
        labelCounts[currentLabel] += 1
    shannonEnt = 0.0
    for key in labelCounts:
        prob = float(labelCounts[key]) / numEntries
        shannonEnt -= prob * log(prob, 2)
    return shannonEnt


# 返回每一行中在axis位置为value的行 形成的新的矩阵
def splitDataSet(dataSet, axis, value):
    retDataSet = []
    for featVec in dataSet:
        if (featVec[axis] == value):
            reduceFeatVec = featVec[: axis]
            reduceFeatVec.extend(featVec[axis + 1:])
            retDataSet.append(reduceFeatVec)
    return retDataSet


def chooseBestFeatureToSplit(dataSet):
    numFeatures = len(dataSet[0]) - 1
    baseEntropy = calcShannonEnt(dataSet)
    bestInfoGain = 0.0
    bestFeature = -1
    for i in range(numFeatures):
        # 相当于取第i列
        featList = [example[i] for example in dataSet]
        # 为了遍历featList所以去除featList中的重复值
        uniqueVals = set(featList)
        newEntropy = 0.0
        for value in uniqueVals:
            subDataSet = splitDataSet(dataSet, i, value)
            prob = len(subDataSet) / float(len(dataSet))
            newEntropy += prob * calcShannonEnt(subDataSet)
        infoGain = baseEntropy - newEntropy
        if (infoGain > bestInfoGain):
            bestInfoGain = infoGain
            bestFeature = i
    return bestFeature


# 返回一个列表中出现次数最多的元素
def majorityCnt(classList):
    classCount = {}
    for vote in classList:
        if (vote not in classCount.keys()):
            classCount[vote] = 0
            classCount[vote] += 1
    sortedClassCount = sorted(classCount.iteritems(),
                              key=operator.itemgetter(1),
                              reverse=True)
    return sortedClassCount[0][0]


# 构建决策树
def createTree(dataSet, labels):
    classList = [example[-1] for example in dataSet]
    #
    if (classList.count(classList[0]) == len(classList)):
        return classList
    #
    if len(dataSet[0]) == 1:
        return majorityCnt(classList)

    bestFeat = chooseBestFeatureToSplit(dataSet)
    bestFeatLabel = labels[bestFeat]
    myTree = {bestFeatLabel: {}}
    del (labels[bestFeat])
    featValues = [example[bestFeat] for example in dataSet]
    uniqueVals = set(featValues)
    for value in uniqueVals:
        subLabels = labels[:]
        myTree[bestFeatLabel][value] = createTree(
            splitDataSet(dataSet, bestFeat, value), subLabels
        )
    return myTree

decisionNode = dict(boxstyle="sawtooth", fc="0.8")
leafNode = dict(boxstyle="round4", fc="0.8")
arrow_args = dict(arrowstyle="<-")


def plotNode(nodeTxt, centerPt, parentPt, nodeType):
    # nodeTxt 标注文本的内容
    #
    createPlot.ax1.annotate(nodeTxt, xy=parentPt,  xycoords='axes fraction',
             xytext=centerPt, textcoords='axes fraction',
             va="center", ha="center", bbox=nodeType, arrowprops=arrow_args )

def createPlot():
    fig = plt.figure(1, facecolor='white')
    # 清除所有轴，但窗口打开
    fig.clf()
    # 在全局定义一个变量ax1
    createPlot.ax1 = plt.subplot(111, frameon=False)
    plotNode('a decision node', (0.5, 0.1), (0.1, 0.5), decisionNode)
    plotNode('a leaf node', (0.8, 0.1), (0.3, 0.8), leafNode)
    plt.show()

if __name__ == '__main__':
    createPlot()
    print(type(createPlot.ax1))