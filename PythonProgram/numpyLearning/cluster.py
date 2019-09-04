# 2019.9.2
import numpy as num
import random
from collections import namedtuple

'''
聚类算法：
样本集: D = {x_1, x_2, ... , x_m} 每个样本都是无标记样本；
每个样本 x_i = (x_i1, x_i2, ... , x_in)是一个n维特征向量；
据类算法将样本D划分为k个不相交的簇{C_l | l = 1, 2, ... , k};
lambda_j表示x_j的簇标记， lambda_j 属于 {1， 2， 3， ... , k};

性能度量：
对于数据集D， 假设通过聚类算法得到的簇划分为C = {C_1, C_2, ... C_k},
参考模型给出的簇划分为C_star = {C_star_1, C_star_2, ... , C_star_k}

a = |SS| SS =  {(x_i, x_j) | lambda_i = lambda_j, lambda_star_i = lambda_star_j}
b = |SD| SD =  {(x_i, x_j) | lambda_i = lambda_j, lambda_star_i != lambda_star_j}
c = |DS| DS =  {(x_i, x_j) | lambda_i != lambda_j, lambda_star_i = lambda_star_j}
d = |DD| DD =  {(x_i, x_j) | lambda_i != lambda_j, lambda_star_i != lambda_star_j}
a表示在C中属于同一簇， 在C_star中属于同一簇的样本数；
b表示在C中属于同一簇， 在C_star中不属于同一簇的样本数；
c表示在C中不属于同一簇， 在C_star中属于同一簇的样本数；
d表示在C中不属于同一簇， 在C_star中不属于同一簇的样本数；、

聚类性能度量外部指标：
Jaccard系数：
JC = a / (a + b + c)
FM指数：
FMI = ((a / (a + b)) * (a / (a + c))) ** 0.5
Rand指数：
RI =  (2 * (a + d)) / (m * (m - 1)) m表示样本总数

avg(C) = (2 / (|C| * (|C| - 1))) * Sum(dist(x_i, x_j))   1<=i<=j<=|C|
diam(C) = Max(dist(x_i, x_j))   1<=i<=j<=|C|
d_min(Ci, Cj) = Min(dist(x_i, x_j))   x_i属于Ci, x_j属于Cj
d_cen(Ci, Cj) = dist(u_i, u_j), u = (1 / |C|) * (Sum(x_i)), u代表C的中心点

聚类性能度量内部指标：
DB指数：
DBI = (1 / k) * Sum(Max((avg(Ci) + avg(Cj)) / d_cen(u_i, u_j)))    1<=i<=k
Dunn指数：
DI = Min(Min(d_min(Ci, Cj) / Max(diam(Cl))))


这个算法有可能在执行分类的时候不会执行指定的轮数
'''


class cluster:

    def __init__(self, D, k, loop):
        self.D = D
        self.k = k
        self.loop = loop

    # 根据现在的均值, 返回距离最近均值的索引
    def find_min_dist(self, d, u):
        index = 1
        min_dist = 0
        u_0 = u[0]
        for value in d - u_0:
            min_dist += value ** 2

        for d_u in u[1:]:
            dist = 0

            # 计算距离
            for value in d - d_u:
                dist += value ** 2
            if dist < min_dist:
                index += 1
                min_dist = dist
        return index

    def update_u(self, u, result):
        i = 0
        for C in result:
            # items = C.items
            lenght = len(C.items)
            if lenght > 0:
                sum = [0, 0]
                for item in C.items:
                    sum = sum + item
                average = sum / lenght
                u[i] = average
            C.items.clear()
            i = i + 1

    def k_means(self, ):
        # 随机选择样本点作为聚类的中心点
        examples = []
        while (len(examples) < self.k):
            example = random.randint(0, 30)
            if examples.__contains__(example):
                continue
            else:
                examples.append(example)
        # 初始均值
        u = []
        # 聚类结果, 结果中的元素使用nametuple表示
        result = []
        Result_Item = namedtuple("result", ["type", "items"])
        i = 1
        time = 1
        for example in examples:
            u.append(self.D[example])
            type = 'C_{0}'.format(i)
            item = Result_Item(type=type, items=[])
            result.append(item)
            i += 1
        while (time < self.loop):
            for d in self.D:
                index = self.find_min_dist(d, u)
                result[index - 1].items.append(d)
            time = time + 1
            if (time != self.loop):
                self.update_u(u, result)
        return result

# Gaussian分布
class Gaussian:
    def __init__(self, average, covariance_matrix):
        self.average = average
        self.covariance = covariance_matrix

    @property
    def average(self):
        return self.average

    @property
    def covariance_matrix(self):
        return self.covariance_matrix


class Mixture_Gaussian_Cluster:
    mixture_gaussian = []
    # D表示训练数据集
    # k表示要求最终模型由k个高斯分布混合而成
    def __init__(self, D, k):
        self.D = D,
        self.k = k
        self.weights = [1 / k for i in range(k)]
        examples_index = []
        for i in range(k):
            example_index = num.random.randint(0, 30)
            if example_index not in examples_index:
                examples_index.append(example_index)
        for i in range(k):
            gaussian = Gaussian(D[examples_index[i], num.array([[0.1, 0], [0, 0.1]])])
            self.mixture_gaussian.append(gaussian)

    def train(self):
        pass

D = num.random.random(size=[30, 2])


if __name__ == "__main__":
    print("the source data is\n {0}".format(D))
    test = cluster(D, 3, 20)
    result = test.k_means()
    print("-------------------------------------------------")
    for type in result:
        print("the type is {0}".format(type.type))
        print("the number of {0} is {1}".format(type.type, len(type.items)))
        print(type.items)
