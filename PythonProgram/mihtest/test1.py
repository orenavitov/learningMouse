from abc import *
import time
from functools import singledispatch
import copy
import weakref
import gc
#annumul、dog、sheep演示抽象类与实现抽象类
# class annimul(ABC):
#     @abstractmethod
#     def sound(self):
#         pass
#
# class dog(annimul):
#     def sound(self):
#         print("wang wang!")
#
# class sheep(annimul):
#     def sound(self):
#         print("mian mian!")
#
# def sound(annimul):
#     annimul.sound()


#下面演示将函数作为参数在函数中传递
# def dog_sound():
#     print("wang wang!")
#
# def sheep_sound():
#     print("mian mian!")
#
# def show_all_annimuls_sound(*annimuls):
#     for annimul in annimuls:
#         annimul()
#
#
# # 下面展示使用globals()显示此模块（test1.py）中所有的函数
# def showAllFunction():
#     # globals()返回的是一个字典
#     for name in globals():
#         print("{0}:{1}".format(name, globals()[name]))


# 下面展示装饰器的使用
# decorate是一个装饰器， 定义装饰器时， 入参是一个函数索引， 返回也是一个函数索引， 装饰器在模块导入时就会执行，
# 装饰器的一个作用就是将被装饰的函数替换为另一个函数， 如下因为decorate返回的函数是function, 所以function1 和 function2实际
# 执行fucntion
# def decorate(func):
#     print("decorate function is running!")
#     return function
#
# def function():
#     print("function is running!")
#
# # @decorate
# def function1():
#     print("function1 is running!")
#
# # @decorate
# def function2():
#     print("function2 is running!")

# 下面展示一个复杂一些的修饰器情况
# 修饰器函数在加载时只会执行外层代码， 不会执行里面的函数
# def decorate(function):
#     print("decorate function is running1!")
#     time.sleep(5)
#     # 使用修饰器后被修饰的函数decorated_function会变成inner_function, 如果想保存原函数的一些性质， 添加@functools.wraps(function)
#     # @functools.wraps(function)
#     def inner_function(*args):
#         print("inner function is running1!")
#         print("inner function is running2!")
#         # function()
#         print("inner function is running3!")
#         print(('-').join(str(arg) for arg in args))
#
#     print("decorate function is running2!")
#     return inner_function
#
# @decorate
# def decorated_function():
#     print("decorated function is running!")

# 下面展示如何在修饰器里面传递参数
# def decorateOuter(text):
#     def decorate(fun):
#         print("hello {0}".format(text))
#         return fun
#     return decorate
#
# @decorateOuter(text="mihao")
# def input():
#     pass

# 使用@singledispatch实现方法重载
# @singledispatch
# def handleFunc(obj):
#     pass
#
# @handleFunc.register(str)
# def _(tuple):
#     print("tuple")
#
# @handleFunc.register(int)
# def _(n, text):
#     print("int @ String")


# 下面展示一个关于作用域的例子
# 首先声明全局作用域的变量b， 在scopeTest1中， 如果去掉global b， b = 9函数不会出错， b会使用全局变量的值， 如果保留b = 9 这一行， 去掉
# global b 会出错， 如果都保留不会出错；
# b = 6
# def scopeTest1(a):
#     print(a)
#     # global b
#     print(b)
#     # b = 9

# 下面例子展示is、 id()、 == 的使用
# is 和id()的作用是相同的， 检查变量的内存地址是否相同， id()返回一个该变量的唯一整数， ==实际会使用__eq__()方法进行比较
# def test():
#     a = (1, 2, [3, 4, 5])
#     b = a
#     c = (1, 2, [3, 4, 5])
#
#     print("a==b? {0}".format(a == b))
#     print("a is b? {0}".format(a is b))
#
#     print("a==c? {0}".format(a == c))
#     print("a is c? {0}".format(a is c))

# 下面例子展示+= 对列表和元组的影响
# 可以看出对于列表+= 不会创建新的对象， 对于元组+= 会创建新的对象
# def test():
#     a = [1, 2, 3]
#     print("the id of a is: {0}".format(id(a)))
#     b = (1, 2, 3)
#     print("the id of b is: {0}".format(id(b)))
#     a += [4, 5]
#     print("now the id of a is: {0}".format(id(a)))
#     b += (4, 5)
#     print("now the id of b is: {0}".format(id(b)))

# 下面例子展示列表默认的复制都是浅复制， 即复制索引， 元组默认的复制是深复制， 即复制元素
# def test():
#     a = [1, [1, 2, 3], (1, 2, 3)]
#     b = list(a)
#     print("b:{0}".format(b))
#     print("a[1] is b[1]? {0}\na[2] is b[2]? {1}".format(a[1] is b[1], a[2] is b[2]))
#     a_ = (1, [1, 2, 3], (1, 2, 3))
#     b_ = tuple(a)
#     print("b_:{0}".format(b_))
#     print("a_[1] is b_[1]? {0}\na_[2] is b_[2]? {1}".format(a_[1] is b_[1], a_[2] is b_[2]))
#     print("a_[1] id:{0}".format(id(a_[1])))
#     print("b_[1] id:{0}".format(id(b_[1])))
#     print("a_[2] id:{0}".format(id(a_[2])))
#     print("b_[2] id:{0}".format(id(b_[2])))

# 下面的例子展示使用copy.copy()实现浅复制， 使用copy.deepcopy()实现深复制
# def test():
#     a = [1, [1, 2, 3], [1, 2, 3]]
#     a_ = copy.deepcopy(a)
#     print("a_:{0}".format(a_))
#     print("a[1] is a_[1]? {0}\na[2] is a_[2]? {1}".format(a[1] is a_[1], a[2] is a_[2]))
#     b = (1, [1, 2, 3], (1, 2 ,3))
#     b_ = copy.copy(b);
#     print("b_:{0}".format(b_))
#     print("b[1] is b_[1]? {0}\nb[2] is b_[2]? {1}".format(b[1] is b_[1], b[2] is b_[2]))

# 下面的例子展示弱引用的使用
class MyList(list):
    def __init__(self, object):
        list(object)
def test():
    a = MyList([1, 2, 3])
    a_ = weakref.ref(a)
    print("a_:{0}".format(a_))
    print("a_ is a:{0}".format(a_ is a))
    a = [1, 23, 4]
    print("a_ is None?{0}".format(a_() is None))

if __name__ == '__main__':
    test()