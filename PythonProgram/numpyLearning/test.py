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
    strResult = str(1) + ' ' + str(1) + ' '
    print(strResult, end='')
    m = mih()
    m.func_(1, 1, 100000)
