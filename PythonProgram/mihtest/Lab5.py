'''
@Time: 2019/12/2 14:39
@Author: mih
@Des: 
'''
from matplotlib import pyplot as plt
import numpy
import copy
from sklearn import linear_model
import pandas as pd
import math
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.model_selection import train_test_split

def test1_1():
    fig = plt.figure()
    x = range(1, 13, 1)
    y = [5384, 8081, 10282, 9156, 6118, 9139, 12460, 10717, 7825, 9693, 15177, 10990]
    plt.scatter(x, y, color='blue')
    plt.show()


def test1_2():
    x = list(range(1, 13, 1))
    y = [5384, 8081, 10282, 9156, 6118, 9139, 12460, 10717, 7825, 9693, 15177, 10990]
    y_average_move = copy.deepcopy(y)
    y_exponential_smoothing = copy.deepcopy(y)
    y_linear_regression = copy.deepcopy(y)
    # Average Move
    for i in range(4):
        y_average_move.append(numpy.sum(y_average_move[-3:]) / 3)
    print("y_average_move:{0}".format([i for i in y_average_move]))

    # Exponential Smoothing
    alph = 0.8

    def exponential_smoothing(X):
        result = alph * X[-1]
        start = -2
        for i in range(len(X) - 1):
            result = result + X[start] * alph * (1 - alph) ** (i + 1)
            start = start - 1
        return result

    for i in range(4):
        y_exponential_smoothing.append(exponential_smoothing(y_exponential_smoothing))
    print("y_exponential_smoothing:{0}".format([i for i in y_exponential_smoothing]))
    # linear regression
    x_reshape = numpy.array(x)
    x_reshape = x_reshape.reshape(len(x_reshape), 1)
    liner_regression_model = linear_model.LinearRegression()
    liner_regression_model.fit(x_reshape, y_linear_regression)
    x_prediction = numpy.array(range(13, 17, 1))
    x_prediction = x_prediction.reshape((len(x_prediction), 1))
    y_prediction = liner_regression_model.predict(x_prediction)
    print("y_prediction:{0}".format([i for i in y_prediction]))


def test1_3():
    x = list(range(1, 13, 1))
    y = [5384, 8081, 10282, 9156, 6118, 9139, 12460, 10717, 7825, 9693, 15177, 10990]
    y_1 = y[0: 4]
    y_2 = y[4: 8]
    y_3 = y[8: 12]
    y_1_average = numpy.average(y_1)
    y_2_average = numpy.average(y_2)
    y_3_average = numpy.average(y_3)
    seasonal_indices_1 = y_1 / y_1_average
    seasonal_indices_2 = y_2 / y_2_average
    seasonal_indices_3 = y_3 / y_3_average
    liner_regression_model = linear_model.LinearRegression()
    liner_regression_model.fit([[1], [2], [3]], [y_1_average, y_2_average, y_3_average])
    y_4_prediction = liner_regression_model.predict(4)
    seasonal_indices_4 = []
    for i in range(len(seasonal_indices_1)):
        seasonal_indices_4.append((seasonal_indices_1[i] + seasonal_indices_2[i] + seasonal_indices_3[i]) / 3)
    print("y_4: {0}".format(seasonal_indices_4 * y_4_prediction))

# 计算向量的模
def ab_vecor(x):
    result = 0.0
    for i in range(len(x)):
        if not math.isnan(x[i]):
            result = result + x[i] ** 2
    return result ** 0.5


# 计算预先相似性
def cos_similary(x, _x):
    result = 0.0
    x_ab = ab_vecor(x)
    _x_ab = ab_vecor(_x)
    for i in range(len(x)):
        x_i = x[i]
        _x_i = _x[i]
        if ((not math.isnan(x_i)) and (not math.isnan(_x_i))):
            result = result + x_i * _x_i
    return result / (x_ab * _x_ab)


# 从大到小
def sort_index_score(index_score):
    size = len(index_score)

    for i in range(size - 1):
        for j in range(size - i - 1):
            if index_score[j][1] < index_score[j + 1][1]:
                temp = index_score[j]
                index_score[j] = index_score[j + 1]
                index_score[j + 1] = temp

    return index_score


'''
user_base CF
data: 输入数据， DataFrame
row： 缺失数据的行号
column： 缺失数据的列号
k: 计算相似性时， 选取k行最接近的数据
row_data_average: 每一行数据的平均值
similar_score: 余弦相似性
'''


def user_base_CF(data, row, column, k):
    data_row_number = data.shape[0]
    similar_score = []
    row_data_average = []
    row_data = data.iloc[row, 1:].values

    for i in range(data_row_number):
        _row_data = data.iloc[i, 1:].values
        result = 0
        number = 0
        for j in _row_data:
            if not math.isnan(j):
                result = result + j
                number = number + 1
        row_data_average.append(result / number)
    for i in range(data_row_number):
        if (i != row):
            _row_data = data.iloc[i, 1:].values
            similar_score.append((i, cos_similary(row_data, _row_data)))

    special_row_average = row_data_average[row]
    similar_score = sort_index_score(similar_score)
    similar_score = similar_score[:k]
    for sim in similar_score:
        most_similary_rate = data.iloc[sim[0], column]
        if (math.isnan(most_similary_rate)):
            most_similary_rate = 1.0
        special_row_average = special_row_average + sim[1] * ((most_similary_rate - row_data_average[sim[0]]) /
                                                              numpy.sum([i for _, i in similar_score]))
    return special_row_average


# item_base CF
def item_base_CF(data, row, column, k):
    data_column_number = data.shape[1]
    similar_score = []
    column_data_average = []
    column_data = data.iloc[:, column].values
    for i in range(1, data_column_number, 1):
        _column_data = data.iloc[:, i].values
        result = 0
        number = 0
        for j in _column_data:
            if not math.isnan(j):
                result = result + j
                number = number + 1
        column_data_average.append(result / number)
    for i in range(1, data_column_number, 1):
        if (i != column):
            _column_data = data.iloc[:, i].values
            similar_score.append((i, cos_similary(column_data, _column_data)))
    special_row_average = column_data_average[column - 1]
    similar_score = sort_index_score(similar_score)
    similar_score = similar_score[:k]
    for sim in similar_score:
        most_similary_rate = data.iloc[row, sim[0]]
        if (math.isnan(most_similary_rate)):
            most_similary_rate = 1.0
        special_row_average = special_row_average + sim[1] * (
                    (most_similary_rate - column_data_average[sim[0] - 1]) /
                    numpy.sum([i for _, i in similar_score]))
    return special_row_average


def test2_1():
    data = pd.DataFrame(data={'Student from:': ['ICT', 'Medicine', 'Business', 'Environment'],
                              'Desperados': [4, 1, None, 4],
                              'Guinness': [3, 2, 2, 3],
                              'chimay triple': [2, 3, 1, None],
                              'Leffe': [3, 1, None, None]})

    print("data:")
    print("{0}".format(data))
    print("shape of data:{0}".format(data.shape))
    select_data = data.iloc[1, 1:]
    print("indexes:{0} values:{1}".format(select_data.index, select_data.values))
    print("select data: {0}".format(data.iloc[1, 1:]))
    missing_data_2_1 = user_base_CF(data, 2, 1, 2)
    print("the missing data of (2, 1) is {0}".format(missing_data_2_1))
    data.iloc[2, 1] = missing_data_2_1
    missing_data_2_4 = user_base_CF(data, 2, 4, 2)
    print("the missing data of (2, 4) is {0}".format(missing_data_2_4))
    data.iloc[2, 4] = missing_data_2_4
    missing_data_3_3 = user_base_CF(data, 3, 3, 2)
    print("the missing data of (3, 3) is {0}".format(missing_data_3_3))
    data.iloc[3, 3] = missing_data_3_3
    missing_data_3_4 = user_base_CF(data, 3, 4, 2)
    print("the missing data of (3, 4) is {0}".format(missing_data_3_4))
    data.iloc[3, 4] = missing_data_3_4


def test2_2():
    data = pd.DataFrame(data={'Student from:': ['ICT', 'Medicine', 'Business', 'Environment'],
                              'Desperados': [4, 1, None, 4],
                              'Guinness': [3, 2, 2, 3],
                              'chimay triple': [2, 3, 1, None],
                              'Leffe': [3, 1, None, None]})

    print("data:")
    print("{0}".format(data))
    print("shape of data:{0}".format(data.shape))

    missing_data_2_1 = item_base_CF(data, 2, 1, 2)
    print("the missing data of (2, 1) is {0}".format(missing_data_2_1))
    data.iloc[2, 1] = missing_data_2_1
    missing_data_2_4 = item_base_CF(data, 2, 4, 2)
    print("the missing data of (2, 4) is {0}".format(missing_data_2_4))
    data.iloc[2, 4] = missing_data_2_4
    missing_data_3_3 = item_base_CF(data, 3, 3, 2)
    print("the missing data of (3, 3) is {0}".format(missing_data_3_3))
    data.iloc[3, 3] = missing_data_3_3
    missing_data_3_4 = item_base_CF(data, 3, 4, 2)
    print("the missing data of (3, 4) is {0}".format(missing_data_3_4))
    data.iloc[3, 4] = missing_data_3_4

def test3_1():
    data_movies = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab5/movies.csv")
    print("data:\n")
    print("{0}".format(data_movies))
    listGen = []
    data_shape = data_movies.shape
    for line_number in range(data_shape[0]):
        geners = data_movies.iloc[line_number, -1]
        try:
            geners = geners.split('|')
            for gener in geners:
                if gener not in listGen:
                    listGen.append(gener)
        except:
            continue
    # Find list of used genres which is used to category the movies
    print("listGen: {0}".format(listGen))

    # Vectorize the relationship between movies and genres
    Ij = []
    for line_number in range(data_shape[0]):
        geners = data_movies.iloc[line_number, -1]
        try:
            ij = numpy.zeros((len(listGen),))
            geners = geners.split('|')
            for gener in geners:
                index = listGen.index(gener)
                ij[index] = 1
            Ij.append(list(ij))
        except:
            ij = numpy.zeros((len(listGen),))
            Ij.append(list(ij))
            continue
    print("Ij:{0}".format(Ij))
    # Vectorize the relationship between users and genres Uj (if user rate for a movie, he/she has the related
    # history with the movies’genres)
    Uj = []
    data_users = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab5/ratings.csv")
    user_movies_map = {}
    _users = data_users['user_id'].values
    line_number = len(_users)
    for line in range(0,line_number, 1):
        user_id, movie_id = data_users.iloc[line, 0:2]
        user_id = int(user_id)
        movie_id = int(movie_id)
        if user_id not in user_movies_map.keys():
            user_movies_map[user_id] = []
        user_movies_map[user_id].append(movie_id)
    for user in user_movies_map.keys():
        temp = numpy.zeros((len(listGen),))
        movie_ids = user_movies_map[user]
        for movie_id in movie_ids:
            _movie_geners = Ij[movie_id - 1]
            temp = [int(temp[i]) | int(_movie_geners[i]) for i in range(len(listGen))]
        Uj.append(temp)
    print("Uj:{0}".format(Uj))

    # Compute the cosine_similarity between movies and users
    cosine_users_movies = cosine_similarity(Ij, Uj)
    print("cosine similarity between users and movies:{0}".format(cosine_users_movies))

def test3_2():
    data_users = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab5/users.csv")
    data_ratings = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab5/ratings.csv")
    data_ratings_shape = data_ratings.shape
    user_ratings_map = {}
    _user_ids = data_ratings['user_id'].values
    user_ids = []
    for id in _user_ids:
        if id not in user_ids:
            user_ids.append(id)

    for user_id in user_ids:
        if user_id not in user_ratings_map.keys():
            user_ratings_map[user_id] = list(numpy.zeros(100,))
        for row_number in range(data_ratings_shape[0]):
            id, movie_id, rating = data_ratings.iloc[row_number, :]
            if (id == user_id):
                user_ratings_map[user_id][movie_id - 1] = rating
    for key in user_ratings_map.keys():
        print("{0} {1}".format(key, user_ratings_map[key]))

    # x_train, x_test, y_train, y_test = train_test_split()

if __name__ == '__main__':
    test1_3()
