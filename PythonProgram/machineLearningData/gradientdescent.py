
import numpy as np
import math as ma
import decimal
from matplotlib import pyplot as plt

class GradientDescentTest:


    effectiveDigits = 6
    def __init__(self):
        pass


    #假设一个函数为f(x) = x + 2 * x^2 + 3 * x^3 + 4 * x^4 + 5 * x^5 + 6 * x^6 + 7 * x^7 + 8 * x^8
    #使用梯度分析求出x^n前的系数
    def f(self, x):
        return 3 + x + 2 * x ** 2 + 3 * x ** 3# + 4 * x **4 + 5 * x ** 5 + 6 * x ** 6 + 7 * x ** 7 + 8 * x ** 8

    def dataFunctory(self, xList):
        return 3 + xList + 2 * xList ** 2 + 3 * xList ** 3
        # result = []
        # for x in xList:
        #     result.append(self.f(x))
        #
        # return result



    def FullGradientDescent(self, xList, yList):
        file = open(r"C:\Users\mihao\Desktop\result.txt", "w+", encoding='utf-8')
        b = 5.0
        w1 = 5.0
        w2 = 5.0
        w3 = 5.0
        rate = 0.00001
        times = 500
        xLength = len(xList)

        for time in range(times):
            b_grad = 0.0
            w1_grad = 0.0
            w2_grad = 0.0
            w3_grad = 0.0

            for i in range(xLength):
                x = xList[i]
                y = yList[i]
                x_exp_3_round = round(x ** 3, 2)
                b_grad = b_grad + round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-1), 2)
                file.write("b_grad: {0}\n".format(b_grad))
                w1_grad = w1_grad + round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-x), 2)
                file.write("w1_grad: {0}\n".format(w1_grad))
                w2_grad = w2_grad + round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-(x ** 2)), 2)
                file.write("w2_grad: {0}\n".format(w2_grad))
                w3_grad = w3_grad + round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-(x ** 3)), 2)
                file.write("w3_grad: {0}\n".format(w3_grad))

            b = round(b - rate * b_grad, self.effectiveDigits)
            w1 = round(w1 - rate * w1_grad, self.effectiveDigits)
            w2 = round(w2 - rate * w2_grad, self.effectiveDigits)
            w3 = round(w3 - rate * w3_grad, self.effectiveDigits)
        return  b, w1, w2, w3


    def ChangeRataeByGrad(self, grad):
        gradAbs = ma.fabs(grad)
        point = 0
        while gradAbs >= 1:
            gradAbs = gradAbs / 10
            point += 1
        return ma.pow(10, -point)

    def getLoss(self, xList, yList, b_List, w1_List, w2_List, w3_List):
        loss_list  =[]
        length = len(xList)
        for i in range(length):
            x = xList[i]
            y = yList[i]
            b_ = b_List[i]
            w1_ = w1_List[i]
            w2_ = w2_List[i]
            w3_ = w3_List[i]
            y_loss = y - (b_ + w1_ * x + w2_ * x ** 2 + w3_ * x ** 3)
            loss_list.append(ma.pow(y_loss, 2))
        return loss_list

    def NewGradientDescent(self, xList, yList):
        file = open(r"C:\Users\mihao\Desktop\result.txt", "w+", encoding='utf-8')
        file.write("NewGradientDescent:----------------------------------------\n")
        b = 5.0
        w1 = 5.0
        w2 = 5.0
        w3 = 5.0
        rate = 0.1
        ada_b_grad_sum_sqr = 0.0
        ada_w1_grad_sum_sqr = 0.0
        ada_w2_grad_sum_sqr = 0.0
        ada_w3_grad_sum_sqr = 0.0
        xLength = len(xList)

        b_list = []
        w1_list = []
        w2_list = []
        w3_list = []
        for i in range(xLength):

            x = xList[i]
            y = yList[i]
            x_exp_3_round = round(x ** 3, 2)
            b_grad = round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-1), 2)
            ada_b_grad_sum_sqr = ada_b_grad_sum_sqr + ma.pow(b_grad, 2)
            # file.write("b_grad: {0}\n".format(b_grad))
            w1_grad = round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-x), 2)
            ada_w1_grad_sum_sqr = ada_w1_grad_sum_sqr + ma.pow(w1_grad, 2)
            # file.write("w1_grad: {0}\n".format(w1_grad))
            w2_grad = round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-(x ** 2)), 2)
            ada_w2_grad_sum_sqr = ada_w2_grad_sum_sqr + ma.pow(w2_grad, 2)
            # file.wrte("w2_grad: {0}\n".format(w2_grad))
            w3_grad = round(2 * (y - (b + w1 * x + w2 * x ** 2 + w3 * x_exp_3_round)) * (-(x ** 3)), 2)
            ada_w3_grad_sum_sqr = ada_w3_grad_sum_sqr + ma.pow(w3_grad, 2)
            # file.write("w3_grad: {0}\n".format(w3_grad))
            b = round(b - (rate / ma.pow(ada_b_grad_sum_sqr, 0.5)) * b_grad, self.effectiveDigits)
            b_list.append(b)
            w1 = round(w1 - (rate / ma.pow(ada_w1_grad_sum_sqr, 0.5)) * w1_grad, self.effectiveDigits)
            w1_list.append(w1)
            w2 = round(w2 - (rate / ma.pow(ada_w2_grad_sum_sqr, 0.5)) * w2_grad, self.effectiveDigits)
            w2_list.append(w2)
            w3 = round(w3 - (rate / ma.pow(ada_w3_grad_sum_sqr, 0.5)) * w3_grad, self.effectiveDigits)
            w3_list.append(w3)
            file.write("time{0}------------ b: {1}, w1: {2}, w2: {3}, w3: {4}\n".format(i, b, w1, w2, w3))
        loss_list = self.getLoss(xList, yList, b_list, w1_list, w2_list, w3_list)

        plt.figure()
        plt.title("b&loss")
        plt.xlabel("b")
        plt.ylabel("loss")
        plt.scatter(b_list, loss_list, s = 0.1)
        plt.show()

        plt.figure()
        plt.title("w1&loss")
        plt.xlabel("w1")
        plt.ylabel("loss")
        plt.scatter(w1_list, loss_list)
        plt.show()

        plt.figure()
        plt.title("w2&loss")
        plt.xlabel("w2")
        plt.ylabel("loss")
        plt.scatter(w2_list, loss_list)
        plt.show()

        plt.figure()
        plt.title("w3&loss")
        plt.xlabel("w3")
        plt.ylabel("loss")
        plt.scatter(w3_list, loss_list)
        plt.show()
        return b, w1, w2, w3
if __name__ == '__main__':
    decimal.getcontext().prec = 6
    test = GradientDescentTest()
    xList = np.arange(-100, 100, step=0.01)
    yList = test.dataFunctory(xList)
    b, w1, w2, w3 = test.NewGradientDescent(xList, yList)
    print("b:{0}\nw1:{1}\nw2:{2}\nw3:{3}".format(b, w1, w2, w3))
