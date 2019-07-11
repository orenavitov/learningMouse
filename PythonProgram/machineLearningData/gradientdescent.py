
import numpy as np

class GradientDescentTest:

    def __init__(self):
        pass


    #假设一个函数为f(x) = x + 2 * x^2 + 3 * x^3 + 4 * x^4 + 5 * x^5 + 6 * x^6 + 7 * x^7 + 8 * x^8
    #使用梯度分析求出x^n前的系数
    def f(self, x):
        return x + 2 * x ** 2 #+ 3 * x ** 3 + 4 * x **4 + 5 * x ** 5 + 6 * x ** 6 + 7 * x ** 7 + 8 * x ** 8

    def dataFunctory(self, xList):
        result = []
        for x in xList:
            result.append(self.f(x))

        return result



    def GradientDescent(self, xList, yList):
        b = 5.0
        w1 = 5.0
        w2 = 5.0
        #w3 = 5.0
        #w4 = 5.0
        #w5 = 5.0
        #w6 = 5.0
        #w7 = 5.0
        #w8 = 5.0

        rate = 0.01
        times = 50000

        xLength = len(xList)
        yLength = len(yList)

        for time in range(times):
            b_grad = 0.0
            w1_grad = 0.0
            w2_grad = 0.0
            #w3_grad = 0.0
            #w4_grad = 0.0
            #w5_grad = 0.0
            #w6_grad = 0.0
            #w7_grad = 0.0
            #w8_grad = 0.0
            for i in range(xLength):
                x = xList[i]
                y = yList[i]
                b_grad = b_grad + 2 * (y - (b + w1 * x + w2 * x ** 2)) * (-1)
                w1_grad = w1_grad + 2 * (y - (b + w1 * x + w2 * x ** 2)) * (-x)
                w2_grad = w2_grad + 2 * (y - (b + w1 * x + w2 * x ** 2)) * (-(x ** 2))
                #w3_grad = w3_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 3))
                #w4_grad = w4_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 4))
                #w5_grad = w5_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 5))
                #w6_grad = w6_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 6))
                #w7_grad = w7_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 7))
                #w8_grad = w8_grad + 2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x ** 3 + w4 * x ** 4 + w5 * x ** 5 +
                #                              w6 * x ** 6 + w7 * x ** 7 + w8 * x ** 8)) * (-(x ** 8))
            b = b - rate * b_grad
            w1 = w1 - rate * w1_grad
            w2 = w2 - rate * w2_grad
            #w3 = w3 - rate * w3_grad
            #w4 = w4 - rate * w4_grad
            #w5 = w5 - rate * w5_grad
            #w6 = w6 - rate * w6_grad
            #w7 = w7 - rate * w7_grad
            #w8 = w1 - rate * w8_grad
        return  b, w1, w2#, w3, w4, w5, w6, w7, w8

if __name__ == '__main__':
    test = GradientDescentTest()
    xList = np.arange(-10, 10, step=0.1)
    yList = test.dataFunctory(xList)
    b, w1, w2 = test.GradientDescent(xList, yList)
    print("b: " + b + "\n" +
          "w1: " + w1 + "\n" +
          "w2: " + w2
          )
