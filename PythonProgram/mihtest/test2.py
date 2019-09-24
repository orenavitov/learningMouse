from math import hypot
from array import array
# 一个简单的二维向量类
class Vector:
    typecode = 'd'
    def __init__(self, x = 0, y = 0):
        self.x = x
        self.y = y

    def __iter__(self):
        return (i for i in (self.x, self.y))

    def __repr__(self):
        return "Vector(%r, %r)" %(self.x , self.y)

    def __bytes__(self):
        return (bytes([ord(self.typecode)]) + bytes(array(self.typecode, self)))

    def __abs__(self):
        # hypot 返回 sqrt(x^2 + y ^ 2)
        return hypot(self.x, self.y)

    def __bool__(self):
        return bool(abs(self))

    def __add__(self, other):
        x = self.x + other.x
        y = self.y + other.y

    def __mul__(self, other):
        return Vector(self.x * other, self.y * other)

    def __format__(self, format_spec):
        components = ["{}".format(c, format_spec) for c in self]
        result = ''
        result.join(components)
        return result

class test():
    def __init__(self):
        pass

    # def __repr__(self):
    #     return "repr"

    def __str__(self):
        return "str"

class test1:
    __a = 1
    __b = 2

    @property
    def a(self):
        return self.__a

    @property
    def b(self):
        return self.__b
if __name__ == '__main__':
    print(test1.__b)