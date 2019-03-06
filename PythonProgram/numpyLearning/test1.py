# k临近算法
from numpy import *
import operator
import matplotlib
import matplotlib.pylab as plt
from os import listdir


def createDataSet():
    group = array([
        [1.0, 1.1],
        [1.0, 1.0],
        [0, 0],
        [0, 0.1]
    ])
    labels = ['A', 'A', 'B', 'B']
    return group, labels


# inX表示输入向量
def classify0(inX, dataSet, labels, k):
    # shape[0]表示返回矩阵第一个维度元素的数量
    dataSetSize = dataSet.shape[0]
    # tile将inX向量在行上重复dataSetSize次， 在列上重复1次
    diffMat = tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffmat = diffMat ** 2
    # 比方说矩阵的shape为（2， 3， 4）， sum(axis = 0)表示将矩阵第一个维度2去掉（改为0），
    # sum(axis = 1)表示将矩阵的第二个维度3去掉（改为0）
    sqDistances = sqDiffmat.sum(axis=1)
    distances = sqDistances ** 0.5
    # argsort()对矩阵排序，返回的是索引值
    sortedDistIndicies = distances.argsort()
    classCount = {}
    for i in range(k):
        voteIlabel = labels[sortedDistIndicies[i]]
        # classCount.get(voteIlabel, 0) 在字典classCount中查找关键字voteIlabel， 如果没有找到默认为0
        classCount[voteIlabel] = classCount.get(voteIlabel, 0) + 1
    # classCount.items()
    sortedClassCount = sorted(classCount.items(),
                              key=operator.itemgetter(1),
                              reverse=True)
    return sortedClassCount[0][0]


def file2matrix(filename):
    love_dictionary = {'largeDoses': 3, 'smallDoses': 2, 'didntLike': 1}
    fr = open(filename)
    # read(size) 读取size个字节， 若无参数读取全部内容
    # readline() 读取一行数据， 返回string
    # readlines() 读取所有行返回List
    arrayOlines = fr.readlines()
    numberOfLines = len(arrayOlines)

    returnMat = zeros((numberOfLines, 3))
    classLabelVector = []
    index = 0
    for line in arrayOlines:
        # 删除Line首尾空格
        line = line.strip()
        listFromLine = line.split('\t')
        # returnMat[index, :]表示returnMat矩阵的第Index行的元素进行赋值
        returnMat[index, :] = listFromLine[0:3]
        if (listFromLine[-1].isdigit()):
            classLabelVector.append(int(listFromLine[-1]))
        else:
            classLabelVector.append(love_dictionary.get(listFromLine[-1]))
        index = index + 1
    return returnMat, classLabelVector


def autoNorm(dataSet):
    # 对于矩阵dataSet
    # dataSet.min[0] 返回矩阵每一列的最小值
    # dataSet.min[1] 返回矩阵每一行的最小值
    # dataSet.max[0] 返回矩阵每一列的最大值
    # dataSet.max[1] 返回矩阵每一行的最大值
    minVals = dataSet.min(0)
    maxVals = dataSet.max(0)
    ranges = maxVals - minVals
    normDataSet = zeros(shape(dataSet))
    # 返回dataSet的行数
    m = dataSet.shape[0]
    normDataSet = dataSet - tile(minVals, (m, 1))
    normDataSet = normDataSet / tile(ranges, (m, 1))
    return normDataSet, ranges, minVals


def datingClassTest():
    hoRatio = 0.10
    datingDataMat, datingLabels = file2matrix("")
    normMat, ranges, minVals = autoNorm(datingDataMat)
    m = normMat.shape[0]
    numTestVecs = int(m * hoRatio)
    errorCount = 0.0
    for i in range(numTestVecs):
        classifierResult = classify0(normMat[i, :],
                                     normMat[numTestVecs:m, :],
                                     datingLabels[numTestVecs:m],
                                     3)
        print("the classifier came back with: %d, the real answer is: %d"
              % (classifierResult, datingLabels[i]))
        if (classifierResult != datingLabels[i]):
            errorCount = errorCount + 1.0
    print("the total error rate is: %f" % (errorCount / float(numTestVecs)))


def draw(returnMat, classLabelVector):
    fig = plt.figure()
    ax = fig.add_subplot(111)
    # returnMat[:, 1]取第二列数据 returnMat[:, 2]取第三列数据
    # 15.0 * array(classLabelVector)设置每个散列点的大小
    # 15.0 * array(classLabelVector)设置每个散列点的颜色
    ax.scatter(returnMat[:, 1], returnMat[:, 2], 15.0 * array(classLabelVector), 15.0 * array(classLabelVector))
    plt.show()


def img2vector(filename):
    returnVect = zeros((1, 1024))
    fr = open(filename)
    for i in range(32):
        lineStr = fr.readline()
        for j in range(32):
            returnVect[i, 32 * i + j] = int(lineStr[j])
    return returnVect


def handwritingClassTest():
    hwLabels = []
    # listdir(path) 返回Path指定路径下的文件名称
    trainingFileList = listdir('')
    m = len(trainingFileList)
    trainingMat = zeros((m, 1024))
    for i in range(m):
        fileNameStr = trainingFileList[i]
        fileStr = fileNameStr.split('.')[0]
        classNumStr = int(fileStr.split('_')[0])
        hwLabels.append(classNumStr)
        trainingMat[i, :] = img2vector('trainingDigits/%s' % (fileNameStr))

    testFileList = listdir('testDigits')
    errorCount = 0.0
    mTest = len(testFileList)
    for i in range(testFileList):
        fileNameStr = testFileList[i]
        fileStr = fileNameStr.split('.')[0]
        classNumStr = int(fileStr.split('_'))[0]
        vectorUnderTest = img2vector('testDigits/%s' % (fileNameStr))
    classifierResult = classify0(vectorUnderTest, trainingMat, hwLabels, 3)
    print("the classifier came back with:%d, the real answer is %d" % (classifierResult, classNumStr))
    if (classifierResult != classNumStr):
        errorCount += 1.0
    print("\nthe total number of error is: %d" % (errorCount))
    print("\nthe total error rate is: %f" % (errorCount / float(mTest)))
