# 集成学习
import numpy
import random


# 根据每个数据的权重进行采样
# D: 输入数据
# weights: 数据权重
# count: 采样数
# 返回数据索引列表
def get_datas_by_weights(D, weights, count):
    indexes = []
    distrituble_map = {}
    start = 0.0
    sum_weights = 0.0
    for i in range(len(weights)):
        distrituble_map[i] = (start, start + weights[i])
        start = start + weights[i]
        sum_weights += weights[i]
    while(count > 0):
        randomNumber = random.random() * sum_weights
        for index in distrituble_map.keys():
            if distrituble_map[index][0] <= randomNumber and distrituble_map[index][1] >= randomNumber:
                indexes.append(index)
                count -= 1
                break
    return indexes

if __name__ == '__main__':
    pass