'''
@Time: 2019/11/30 11:02
@Author: mih
@Des: 
'''
from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
import matplotlib.pyplot as plt
from sklearn.metrics import classification_report
from sklearn import svm
from sklearn.metrics import confusion_matrix
from sklearn.naive_bayes import GaussianNB
from sklearn import linear_model
from sklearn.model_selection import cross_val_score
from sklearn.linear_model import LogisticRegression
import pandas as pd
import seaborn as sns
import numpy

def loadData(test_size, random_state):
    digits = datasets.load_digits()
    x = digits.data
    y = digits.target
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=test_size, random_state=random_state)
    return x_train, x_test, y_train, y_test

def accuracy(prediction_data, real_data):
    right_number = 0.0
    for prediction, real in zip(prediction_data, real_data):
        if prediction == real:
            right_number = right_number + 1
    return (float)(right_number) / (float)(len(real_data))

# KNN
def test1():
    # digits = datasets.load_digits()
    # x = digits.data
    # y = digits.target
    x_train, x_test, y_train, y_test = loadData(test_size=0.2, random_state=6)
    k = 1
    x_range = range(1, 9, 1)
    train_accuracys = []
    test_accuracys = []
    while k <= 8:
        knn = KNeighborsClassifier(n_neighbors=k)
        knn.fit(x_train, y_train)
        result_train = knn.predict(x_train)
        result_test = knn.predict(x_test)
        train_right_number = 0
        test_right_number = 0
        # for y_predict, y_real in zip(result_train, y_train):
        #     if y_predict == y_real:
        #         train_right_number = train_right_number + 1
        # train_accuracy = (float)(train_right_number) / (float)(len(y_train))
        # train_accuracys.append(train_accuracy)
        #
        # for y_predict, y_real in zip(result_test, y_test):
        #     if y_predict == y_real:
        #         test_right_number = test_right_number + 1
        # test_accuracy = (float)(test_right_number) / (float)(len(y_test))
        # test_accuracys.append(test_accuracy)
        train_accuracys.append(accuracy(result_train, y_train))
        test_accuracys.append(accuracy(result_test, y_test))
        k = k + 1
    plt.plot(x_range, train_accuracys, label = 'Training Accuracy', color = 'r')
    plt.plot(x_range, test_accuracys, label='Testing Accuracy', color='b')
    plt.xlabel("Number of Neigubors")
    plt.ylabel("Accuracy")
    plt.title("k-NN Varying Number of Neighbors")
    plt.legend()
    plt.show()

    return test_accuracys

# SVM
def test2():
    # digits = datasets.load_digits()
    # x = digits.data
    # y = digits.target
    accuracys = []
    x_train, x_test, y_train, y_test = loadData(test_size=0.2, random_state=6)
    target_names = [str(i) for i in range(10)]

    svc_rbf = svm.SVC(kernel='rbf')
    svc_rbf.fit(x_train, y_train)
    result_rbf = svc_rbf.predict(x_test)
    print("the result of SVC when the kernel is rbf:\n")
    print("{0}".format(classification_report(y_test, result_rbf, target_names=target_names)))
    accuracys.append(accuracy(result_rbf, y_test))

    # svc_liner = svm.SVC(kernel='liner')
    # svc_liner.fit(x_train, y_train)
    # result_liner = svc_liner.predict(x_test)
    # print("the result of SVC when the kernel is liner:\n")
    # print("{0}".format(classification_report(y_test, result_liner, target_names=target_names)))


    svc_poly = svm.SVC(kernel='poly')
    svc_poly.fit(x_train, y_train)
    result_poly = svc_poly.predict(x_test)
    print("the result of SVC when the kernel is poly:\n")
    print("{0}".format(classification_report(y_test, result_poly, target_names=target_names)))
    accuracys.append(accuracy(result_poly, y_test))

    # svc_sigmoid = svm.SVC(kernel='sigmoid')
    # svc_sigmoid.fit(x_train, y_train)
    # result_sigmoid = svc_sigmoid.predict(x_test)
    # print("the result of SVC when the kernel is sigmoid:\n")
    # print("{0}".format(classification_report(y_test, result_sigmoid, target_names=target_names)))


    # svc_precomputed = svm.SVC(kernel='precomputed')
    # svc_precomputed.fit(x_train, y_train)
    # result_precomputed = svc_precomputed.predict(x_test)
    # print("the result of SVC when the kernel is precomputed:\n")
    # print("{0}".format(classification_report(y_test, result_precomputed, target_names=target_names)))

    return accuracys

# Bayes
def test3():
    accuracys = []
    digits = datasets.load_digits()
    x = digits.data
    y = digits.target
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=6)
    clf = GaussianNB()
    clf.fit(x_train, y_train)
    prediction_result = clf.predict(x_test)
    accuracys.append(accuracy(prediction_result, y_test))
    _confusion_matrix = confusion_matrix(y_test, prediction_result)
    labels = range(10)
    plt.imshow(_confusion_matrix, interpolation='nearest', cmap=plt.cm.Oranges)
    plt.colorbar()
    tick_marks = range(10)


    plt.xticks(tick_marks, labels)
    plt.yticks(tick_marks, labels)

    iters = numpy.reshape([[[i, j] for j in range(10)] for i in range(10)], (_confusion_matrix.size, 2))
    for i, j in iters:
        plt.text(j, i, format(_confusion_matrix[i, j]), fontsize=7)

    plt.show()
    return accuracys

def test3_2():
    label_list = ['Bayes', 'KNN', 'SVM']
    accuracys = []
    bayes_accuracy = numpy.max(test3())
    accuracys.append(bayes_accuracy)
    knn_accuracy = numpy.max(test1())
    accuracys.append(knn_accuracy)
    svm_accuracy = numpy.max(test2())
    accuracys.append(svm_accuracy)
    plt.bar(left = label_list, height = accuracys, color = 'red')
    plt.xlabel('Classifer')
    plt.ylabel('Accuracy')
    plt.show()

def test4():
    gapminder_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab4/gapminder.csv")
    # score = gapminder_data.corr()
    # # 绘制热量图
    # sns.heatmap(score, annot=False, square=True)
    x_train = gapminder_data['fertility'].values
    x_length = len(x_train)
    x_train = x_train.reshape(x_length, 1)
    y_train = gapminder_data['life'].values
    x_test = x_train[:3]
    line_regression = linear_model.LinearRegression()
    line_regression.fit(x_train, y_train)
    # 绘制散点图
    plt.scatter(x_train, y_train, color = 'blue')
    plt.plot(x_test, line_regression.predict(x_test), color='red')
    score = cross_val_score(line_regression, x_train, y_train, cv=5)
    print(score)
    plt.show()

# 使用多个特征进行线性回归
def test4_3():
    gapminder_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab4/gapminder.csv")
    features = ['population', 'fertility', 'HIV', 'CO2', 'BMI_male', 'GDP', 'BMI_female', 'child_mortality']
    x = gapminder_data[features]
    y = gapminder_data['life']
    line_regression = linear_model.LinearRegression()
    line_regression.fit(x, y)
    for feature, n in zip(features, line_regression.coef_):
        print("{0}:{1}".format(feature, n))

def test4_4():
    gapminder_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab4/gapminder.csv")
    features = ['population', 'fertility', 'HIV', 'CO2', 'BMI_male', 'GDP', 'BMI_female', 'child_mortality']
    x = gapminder_data[features]
    y = gapminder_data['life'].values
    line_regression = linear_model.LinearRegression()
    line_regression.fit(x, y)
    score = cross_val_score(line_regression, x, y, cv=5)
    print(score)

def test5():
    creditcard_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab4/creditcard.csv")
    frauds = creditcard_data.loc[creditcard_data['Class'] == 1]
    non_frauds = creditcard_data.loc[creditcard_data['Class'] == 0]
    print(len(frauds), 'frauds', len(non_frauds), 'nonfrauds')

    # 绘制散点图
    plt.scatter(frauds['Amount'], frauds['Class'], color='yellow', label = 'Frauds')
    plt.scatter(non_frauds['Amount'], non_frauds['Class'], color='blue', label='Normal')
    plt.legend()
    plt.show()

def test5_3():
    creditcard_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab4/creditcard.csv")
    logistic_regression = LogisticRegression()
    x = creditcard_data.iloc[:, :-2]
    y = creditcard_data.iloc[:, -1:]
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=6)
    logistic_regression.fit(x_train, y_train)
    prediction_result = logistic_regression.predict(x_test)
    _confusion_matrix = confusion_matrix(y_test, prediction_result)
    labels = range(2)
    plt.imshow(_confusion_matrix, interpolation='nearest', cmap=plt.cm.Oranges)
    plt.colorbar()
    tick_marks = range(2)

    plt.xticks(tick_marks, labels)
    plt.yticks(tick_marks, labels)

    iters = numpy.reshape([[[i, j] for j in range(2)] for i in range(2)], (_confusion_matrix.size, 2))
    for i, j in iters:
        plt.text(j, i, format(_confusion_matrix[i, j]), fontsize=7)

    plt.show()
if __name__ == '__main__':
    test5_3()