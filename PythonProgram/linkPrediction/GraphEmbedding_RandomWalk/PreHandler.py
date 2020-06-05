'''
@Time: 2020/1/6 9:53
@Author: mih
@Des:
在分类中如何处理训练集中不平衡问题:http://blog.csdn.net/heyongluoyao8/article/details/49408131
imbalance-learn:https://imbalanced-learn.org/stable/
'''
import numpy
from imblearn.over_sampling import SMOTE
X = numpy.array([
    [1., 2., 3.],
    [1.1, 2., 3.],
    [0.9, 2., 3.],
    [1., 1.9, 3.],
    [1., 2.1, 3.],
    [4., 5., 6.],
    [4.1, 5., 6.]
])
X = numpy.asarray(X, dtype=numpy.float)
Y = [1, 1 ,1, 1, 1, 0, 0]
Y = numpy.asarray(Y, dtype=numpy.float)

if __name__ == '__main__':
    smote = SMOTE(k_neighbors=1)
    X_res, Y_res = smote.fit_resample(X=X, y=Y)
    print("X_res: {0}\n".format(X_res))
    print("Y_res: {0}\n".format(Y_res))