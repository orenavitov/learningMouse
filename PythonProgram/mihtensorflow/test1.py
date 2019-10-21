import numpy





if __name__ == '__main__':
    a = numpy.arange(12).reshape((4, 3))
    print("a:{0}".format(a))
    b = a[..., :2]
    print("b:{0}".format(b))