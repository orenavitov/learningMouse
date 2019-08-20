import numpy as np
from inspect import signature
from collections import *
from operator import *
def factorial(n):
    '''return n!'''
    if n < 2:
        return 1
    else:
        return n * factorial(n - 1)


def testfaction(a, *list, b = None, **map):
    if a is not None:
        print("a:{0}\n".format(a))
    if list is not None:
        print("list:{0}\n".format(list))
    if b is not None:
        print("b:{0}\n".format(b))
    if map is not None:
        print("map:{0}\n".format(map))

#itemgetter返回列表元组的第n-1 个字段
def itemgettertest():
    test = [
        (1, 'a'), (2, 'b'), (3, 'c')
    ]
    secondChar = itemgetter(1)
    for item in test:
        print(secondChar(item))


#展示nametuple、attrgetter的使用：
def attrgettertest():
    data = [('ming', 11, '123', ('cn', 'hb')),
               ('hong', 12, '132', ('cn', 'hn')),
               ('qiang', 13, '232', ('cn', 'sj')),
               ('hao', 22, '2313', ('cn', 'kj'))]
    Person = namedtuple("person", "name, age, telephoneNumber, address")
    Address = namedtuple("address", "country, city")
    persons = [Person(name, age, teleNumber, Address(country, city))
               for name, age, teleNumber, (country, city) in data]
    nameAndaddress = attrgetter('name', 'address')
    for person in sorted(persons, key=attrgetter('age')):
        print(nameAndaddress(person))


# 下面展示join函数的使用
def joinTests(splitChar):
    result = splitChar.join([str(item) for item in [1, 1, 1, 1, 1]])
    print(result)

class TestMagicMeth:
    def __init__(self):
        self.attr = "name"

if __name__ == '__main__':

    # 下面示例展示列表生成器
    # numbers = [1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 21, 33, 41]
    # resulList = [number for number in numbers
    #                         if number > 10]
    # print(resulList)

    # 输出函数的参数
    # print("params:", testfaction.__code__.co_varnames, "\n")
    # print("params numbers:", testfaction.__code__.co_argcount, "\n")
    # print("params defaults:", testfaction.__defaults__)

    # 输出函数的参数
    # 获取testfaction的签名
    # sig = signature(testfaction)
    # for name, value in sig.parameters.items():
    #     print("{0}:{1}\n".format(name, value))

    # 绑定函数参数， 然后输出传入的参数
    # sig = signature(testfaction)
    # param = {'a': 1, 'b': 2, 'c': 3, 'd': 4}
    # bound_args = sig.bind(**param)
    # for name, value in bound_args.arguments.items():
    #     print("{0}:{1}\n".format(name, value))

    # attrgettertest()
    # magicMethTest  = TestMagicMeth()
    # print(magicMethTest.attr)
    joinTests("-")