#  贝叶斯分类算法
from collections import namedtuple
import numpy
import random as rand
import math

# 产生100组随机数据
datas = []
data = namedtuple("data", ["attr_1", "attr_2", "attr_3", "attr_4", "attr_5", "attr_6", "attr_7",
                           "attr_8", "result"])
attr_1_range = 3
attr_2_range = 3
attr_3_range = 3
attr_4_range = 3
attr_5_range = 3
attr_6_range = 3
result_range = 2
for i in range(10):
    item = data(attr_1=numpy.random.randint(1, 4),
                attr_2=numpy.random.randint(1, 4),
                attr_3=numpy.random.randint(1, 4),
                attr_4=numpy.random.randint(1, 4),
                attr_5=numpy.random.randint(1, 4),
                attr_6=numpy.random.randint(1, 4),
                attr_7=rand.random(),
                attr_8=rand.random(),
                result=numpy.random.randint(1, 3))
    datas.append(item)


class bayesian:
    def __init__(self, datas):
        self.datas = datas

    # 返回distribute_map
    # index 表示统计第几个属性
    # distribute_map中key的命名方式："attr_第几个属性_属性值_这条数据的结果"
    def get_dsitribute_map(self, datas, index, distribute_map):
        attrs = distribute_map.keys()
        for data in datas:
            attr = data[index]
            result = data[-1]
            if (index + 1 == 7 or index + 1 == 8):
                attr_name = "attr_{0}_{1}".format(index + 1, result)
                if attr_name not in attrs:
                    distribute_map[attr_name] = [attr]
                else:
                    distribute_map[attr_name].append(attr)
                continue
            else:
                attr_name = "attr_{0}_{1}_{2}".format(index + 1, attr, result)
                if attr_name not in attrs:
                    distribute_map[attr_name] = 1
                else:
                    distribute_map[attr_name] += 1
        return distribute_map

    # 初始化每一个属性在每一种结果中的概率分布
    def init_distribute(self, datas):
        distribute_map = {}
        data_number = len(datas)
        attr_numbers = len(datas[0])
        # 结果命名： "result_结果类型"
        result_map = {}
        for data in datas:
            result = data[-1]
            attr_name = "result_{0}".format(result)
            if attr_name not in result_map.keys():
                result_map[attr_name] = 1
            else:
                result_map[attr_name] += 1
        for index in range(attr_numbers - 1):
            self.get_dsitribute_map(datas, index, distribute_map)
        for key in distribute_map.keys():
            print("{0}:{1}".format(key, distribute_map[key]))
        for key in distribute_map.keys():
            if len(key.split("_")) == 3:
                values = distribute_map.get(key)
                average = math.fsum(values) / len(values)
                sum = 0
                for value in values:
                    sum += (value - average) ** 2
                variance = sum / len(values)
                distribute_map[key] = [average, variance]
                print("{0}:{1}".format(key, distribute_map[key]))
            else:
                result_type = key.split("_")[-1]
                result_name = "result_{0}".format(result_type)
                distribute_map[key] = distribute_map[key] / result_map[result_name]
                print("{0}:{1}".format(key, distribute_map[key]))

    def predict(self, data):
        probability = lambda average, variance, x: 1 / (2 * math.pi ** 0.5 * variance) * math.e ** (
                    -(x - average) ** 2 / (2 * variance ** 2))


if __name__ == "__main__":
    print("the datas are : -------------------------------------")
    for data in datas:
        print(data)
    bayesian = bayesian(datas)
    bayesian.init_distribute(datas)
