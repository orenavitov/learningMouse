from numpyLearning.test1 import *
if __name__ == '__main__':
    print("hello world!\n")
    datingDataMat, datingLabels = file2matrix("D:\machineLearningData\machinelearninginaction\Ch02\datingTestSet.txt")
    normMat, ranges, minVals = autoNorm(datingDataMat)
    print(normMat)
    # draw(datingDataMat, datingLabels)
