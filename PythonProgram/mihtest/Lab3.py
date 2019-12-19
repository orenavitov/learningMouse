'''
@Time: 2019/11/28 20:27
@Author: mih
@Des: 
'''
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
from sklearn.decomposition import PCA as sklearnPCA

def test1():
    # fig, ax = plt.subplots(figsize=(10, 5))

    city_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab3/Lab3/city_data.csv")
    print("city_data:\n{0}".format(city_data))
    ride_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab3/Lab3/ride_data.csv")
    print("ride_data:\n{0}".format(ride_data))
    merged_data = pd.merge(city_data, ride_data, on='city')
    print("merged_data:\n {0}".format(merged_data.head()))

    ride_data_group_by_city = ride_data.groupby('city').describe().reset_index()

    print("ride_data_group_by_city:\n {0}".format(ride_data_group_by_city))

    ride_data['fare'] = pd.to_numeric(ride_data['fare'])

    print("ride_data dtypes: {0}".format(ride_data.dtypes))

    ride_data_fare_mean = ride_data['fare'].groupby(ride_data['city']).mean()
    ride_data_fare_mean_dataframe = pd.DataFrame({"city": ride_data_fare_mean.index, 'Average Fare':ride_data_fare_mean.values})

    ride_data_rides_count = ride_data['ride_id'].groupby(ride_data['city']).count()
    ride_data_rides_count_dataframe = pd.DataFrame({'city': ride_data_rides_count.index, 'Number of Rides': ride_data_rides_count.values})
    new_data_frame = pd.DataFrame({
        'city': ride_data_group_by_city['city'],
        'Number of Drivers': merged_data['driver_count'],
    })
    temp_data_frame = pd.merge(ride_data_fare_mean_dataframe, ride_data_rides_count_dataframe, on='city')
    new_data_frame = pd.merge(new_data_frame, temp_data_frame, on='city')
    new_data_frame = new_data_frame.set_index('city')
    print("new_data_frame:\n {0}".format(new_data_frame))

    labels = ['Suburban', 'Rural', 'Urban']

    colors = ['blue', 'yellow', 'red']

    total_fares_distritube = merged_data['fare'].groupby(merged_data['type']).sum()
    total_rides_distritube = merged_data['ride_id'].groupby(merged_data['type']).sum()
    total_fares_values = []
    total_rides_values = []
    for label in labels:
        total_fares_values.append(total_fares_distritube[label])
        total_rides_values.append(total_rides_distritube[label])
    fig_ = plt.figure()
    fig_.add_subplot(121)
    plt.pie(total_fares_values, labels=labels, colors=colors, autopct='%1.1f%%')
    plt.title(r"% of Total Fares")
    fig_.add_subplot(122)
    plt.pie(total_rides_distritube, labels=labels, colors=colors, autopct='%1.1f%%')
    plt.title(r"% of Total Rides")
    plt.show()
def test2():
    titanic_data = sns.load_dataset('titanic')
    # print("titanic_data_fare:\n{0}".format(titanic_data['fare']))
    print("titanic_data:\n{0}".format(titanic_data))

    # sns.distplot(titanic_data['fare'], kde=False)

    # Create a barchart plot for “fare”.
    # sns.barplot(x = 'fare', data=titanic_data)

    # Statistic and compare between age and sex.
    fig = plt.figure()
    fig.add_subplot(121)
    print("groups:{0}".format(titanic_data['age'].groupby(titanic_data['sex']).groups))
    groups = titanic_data['age'].groupby(titanic_data['sex']).groups
    # print("age_distribute:\n{0}".format(age_distribute))

    # Create a boxplot between “age” and “class”
    # sns.boxplot(x='class', y='age',data=titanic_data)

    # Use swarmplot to draw following chart.
    # sns.swarmplot(x='sex', y='age',hue='survived', data=titanic_data, dodge=True)

    plt.show()
    print("end!")
def test3():
    wine_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/lab3/Lab3/wine.data.csv")
    print("wine_data:\n{0}".format(wine_data))
    labels = wine_data['Label']
    wine_data.drop(labels=['Label'], axis=1, inplace=True)
    print("wine_data:\n{0}".format(wine_data))
    label_kinds = []
    for label in labels:
        if label not  in label_kinds:
            label_kinds.append(label)
    print("label_kinds: {}".format(label_kinds))
    print("columns: {}".format(wine_data.columns))
    feature_lenght = len(wine_data.columns)
    #
    fig, ax = plt.subplots(figsize = (feature_lenght, feature_lenght))
    # fig = plt.figure(feature_lenght, feature_lenght)
    # gs = gridspec.GridSpec(feature_lenght, feature_lenght)
    # fig.set_figheight(12)
    # fig.set_figwidth(12)
    # # ax = plt.subplot(feature_lenght+1, feature_lenght+1, 1)
    # row = 0
    # 
    # for columns_row in wine_data.columns:
    #     column = 0
    #     for columns_column in wine_data.columns:
    #         if (row != column):
    #             ax = plt.subplot(gs[row, column])
    #             ax.scatter(wine_data[columns_row], wine_data[columns_column])
    #         column = column + 1
    #     row = row + 1
    # plt.legend()
    # plt.grid(True)
    # plt.subplots_adjust(top=0.0, bottom = 0.0, right = 0.0, left = 0.0, hspace = 0, wspace = 0)
    score = wine_data.corr()
    # for row in wine_data.columns:
    #     row_data = wine_data[row]
    #     _score = []
    #     for column in wine_data.columns:
    #         column_data = wine_data[column]
    #         _score.append()

    # im = ax.imshow(score, cmap='plasma_r')
    # ax.xaxis.set_ticks_position('top')
    # ax.set_xticks(np.arange(feature_lenght))
    # ax.set_yticks(np.arange(feature_lenght))
    # ax.set_xticklabels(wine_data.columns)
    # ax.set_yticklabels(wine_data.columns)
    # fig.colorbar(im, pad=0.03)
    # plt.margins(0.0, 0.0)
    sns.heatmap(score, square=True, annot=True)
    plt.show()
    # plt.scatter()

    print("end!")
if __name__ == '__main__':
    test3()



