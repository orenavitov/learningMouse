import numpy
import scipy
if __name__ == '__main__':
    a = {
        'a':1,
        'b':2
    }
    b = {
        'b':100,
        'c':3
    }
    a.update(b)
    print(a)