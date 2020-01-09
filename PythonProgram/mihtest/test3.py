import numpy
import scipy
class A():
    def __init__(self, d = 100):
        self.a = d

    def c(self):
        return A(self)

    def method(self):
        print(self.a)

class B(A):
    def __init__(self, d):
        self.a = d

if __name__ == '__main__':
    a_ = A(1)
    b = a_.c()
    print(a_)
    print(a_.a)
    print(b)
    print(int(b.a))
