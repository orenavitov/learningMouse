import sklearn
from sklearn import cluster
import matplotlib.pyplot as plt
import numpy
from sklearn import datasets
from matplotlib.pyplot import MultipleLocator



def K_means():
    data_1 = numpy.random.normal(loc=0.0, scale=0.1, size=[100, 2])
    data_2 = numpy.random.normal(loc=0.1, scale=0.1, size=[100, 2])
    data = numpy.concatenate([data_1, data_2], axis=0)
    x = [item[0] for item in data]
    y = [item[1] for item in data]
    # K-Means
    module = cluster.KMeans(n_clusters = 2)
    y_pre = module.fit_predict(data)
    plt.scatter(x, y, s=5, c=y_pre)
    plt.show()

def dbscan():
    X1, y1 = datasets.make_circles(n_samples=5000, factor=.6,
                                   noise=.05)
    X2, y2 = datasets.make_blobs(n_samples=1000, n_features=2, centers=[[1.2, 1.2]], cluster_std=[[.1]],
                                 random_state=9)

    X = numpy.concatenate((X1, X2))
    plt.scatter(X[:, 0], X[:, 1], marker='o')
    plt.show()
    #
    module = cluster.DBSCAN(eps=0.1, min_samples=10)
    y_pre = module.fit_predict(X)
    #
    plt.scatter(X[:, 0], X[:, 1], s=5, c=y_pre)
    plt.show()

def birch():
    data_1 = numpy.random.normal(loc=0.0, scale=0.1, size=[100, 2])
    data_2 = numpy.random.normal(loc=0.1, scale=0.1, size=[100, 2])
    data = numpy.concatenate([data_1, data_2], axis=0)
    x = [item[0] for item in data]
    y = [item[1] for item in data]
    y_pre = cluster.Birch(threshold=0.05, branching_factor=50, n_clusters=2).fit_predict(data)
    plt.scatter(x, y, c=y_pre)
    plt.show()

def mean_shift():
    data_1 = numpy.random.normal(loc=0.0, scale=0.1, size=[100, 2])
    data_2 = numpy.random.normal(loc=1, scale=0.1, size=[100, 2])
    data = numpy.concatenate([data_1, data_2], axis=0)
    x = [item[0] for item in data]
    y = [item[1] for item in data]
    # bandwidth = cluster.estimate_bandwidth(data, quantile=0.5, n_samples=500)
    y_pre = cluster.MeanShift(bandwidth = 0.01).fit_predict(data)
    plt.scatter(x, y, c=y_pre)
    plt.show()
if __name__ == '__main__':
    x_major_locator = MultipleLocator(1)
    y_major_locator = MultipleLocator(1)
    ax = plt.gca()
    ax.xaxis.set_major_locator(x_major_locator)
    ax.yaxis.set_major_locator(y_major_locator)
    mean_shift()



