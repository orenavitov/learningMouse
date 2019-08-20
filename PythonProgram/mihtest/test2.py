from math import hypot

# 一个简单的二维向量类
class Vector:
    def __init__(self, x = 0, y = 0):
        self.x = x
        self.y = y

    def __repr__(self):
        return "Vector(%r, %r)" %(self.x , self.y)

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


class test():
    def __init__(self):
        pass

    # def __repr__(self):
    #     return "repr"

    def __str__(self):
        return "str"

if __name__ == '__main__':
    a = test()
    print(str(a))
    print(repr(a))