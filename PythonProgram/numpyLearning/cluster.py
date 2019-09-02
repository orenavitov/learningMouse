# 2019.9.2
import numpy as num
import random
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
'''

class cluster:


    def __init__(self, D, k):
        self.D = D
        self.k = k

    # 根据现在的均值, 返回距离最近均值的索引
    def find_max_dist(self, d, u):
        index = 0
        max_dist = 0
        for d_u in u:
            dist = 0

            # 计算距离
            for value in d - d_u:
                dist += value ** 2
            if dist > max_dist:
                index += 1
                max_dist = dist
        print("the max dist is {0}".format(dist))
        return index

    def k_means(self):

        #
        examples= []
        while(len(examples) < self.k):
            example = random.randint(0, 30)
            if examples.__contains__(example):
                continue
            else:
                examples.append(example)

        # 初始均值
        u = []
        # 聚类结果
        result = {}
        i = 1
        for example in examples:

            u.append(self.D[example])
            key = 'C_{0}'.format(i)
            value = [self.D[example]]
            result[key] = value
            i += 1
        #
        i = 1
        for d in self.D:

            print("the {0} data is :{1}".format(i, d))
            index = self.find_max_dist(d, u)
            value = result.get('C_{0}'.format(index)).append(d)
            result.__setitem__('C_{0}'.format(index), value)
            i += 1
D = num.random.random(size = [30, 2])
if __name__ == "__main__":

    print("the source data is {0}".format(D))

    test = cluster(D, 3)
    test.k_means()
    pass