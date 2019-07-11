import numpy as np
from matplotlib import pyplot as plt

#matplotlib学习
class matplotlibTest:
    def __init__(self):
        pass

    def test1(self):
        #numpy.arange(start, end) 跟range(start, end)基本相同
        #产生1到11的列表
        x = np.arange(1, 100, step=0.1)

        y = 2 * x + 5
        plt.title("demo")
        plt.xlabel("X")
        plt.ylabel("Y")
        plt.plot(x, y)
        plt.show()

    def test2(self):
        fig = plt.figure(num=1, figsize=(15, 8), dpi=80)
        x = np.arange(-2, 0, step=0.01)
        y1 = (2 * x + 5) ** x
        y2 = (3 * x + 3) ** 2
        plt.title("m")
        plt.xlabel("X")
        plt.ylabel("Y")
        plt.plot(x, y1)
        plt.plot(x, y2)
        plt.show()

class mih:

    def __init__(self):
        pass
    #输入斐波拉且数列
    def func_(self, pre, next, end):
        first = pre
        second = next

        if (second < end):
            temp = second
            second = first + second
            first = temp
            print(second, end=' ')
            self.func_(first, second, end)


if __name__ == '__main__':
    test = matplotlibTest()
    test.test2()
