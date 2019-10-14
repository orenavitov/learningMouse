import numpy
import scipy
if __name__ == '__main__':
    a = numpy.arange(0, 10)
    b = numpy.arange(0, 60).reshape([10, 6])
    print("a:{0}".format(a))
    print("b:{0}".format(b))
    c = scipy.diag(a).dot(b)
    print(c)