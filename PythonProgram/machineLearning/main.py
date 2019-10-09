import copy
import numpy

if __name__ == "__main__":
    a = numpy.array(
        [
            [1, 0, 0],
            [0, 2, 0],
            [0, 0, 3]
        ]
    )
    b = numpy.array(
        [
            [2, 2, 2],
            [2, 2, 2],
            [2, 2, 2]
        ]
    )
    print("{0}".format(numpy.matmul(a, b)))
