class number():
    def __init__(self, detial):
        self.detial = detial

    def GetDetial(self):
        return self.detial



    def SetDetial(self, detial):
        self.detial = detial

    def SetCount(self, count):
        self.count = count

def test(target):

    pre_number = number([[1, 0, 0]])
    current = 2
    while(current <= target):
        detial = pre_number.GetDetial()
        for d in detial:
            d[0] = d[0] + 1

        for d in detial:
            if d[0] == 5:
                if current % 5 == 0 and current % 10 != 0:
                    detial.append([0, d[1] + 1, d[2]])
            if d[0] == 10:
                if current % 10 == 0:
                    detial.append([0, d[1], d[2] + 1])
                    detial.append([0, d[1] + 2, d[2]])



        current = current + 1
    detial = pre_number.GetDetial()
    print("target:{0}".format(target))
    print("count:{0}".format(len(detial)))
    print("detials:{0}".format(detial))

def f(n):
    V = [1, 5, 10]
    C = [0 for _ in range(n + 1)]
    C[0] = 1
    for v in V:
        for i in range(v, n + 1):
            if i - v >= 0:
                C[i] += C[i - v]
    print("count: {0}".format(C[-1]))

if __name__ == '__main__':
    target = 25
    test(target)
    f(target)
