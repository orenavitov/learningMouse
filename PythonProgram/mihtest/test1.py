import pandas as pd
import matplotlib.pyplot as plt
import datetime
if __name__ == "__main__":
    # data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/PastHires.csv")
    # print(data.head(10))
    # print(data.sort_values("Years Experience"))
    #
    # value_counts_data = data['Level of Education'].value_counts()
    # print(value_counts_data)
    # # value_counts_data = value_counts_data.cumsum()
    # value_counts_data.plot(kind="bar")
    # plt.show()

    sales_data = pd.read_csv(r"D:/SomePythonPrograms/labRelations/sales.csv")
    print("sale_data head: {0}".format(sales_data.head()))
    print("sale_data types: {0}".format(sales_data.dtypes))
    print("sale_data shape: {0}".format(sales_data.shape))

    sales_data['ordered_at'] = pd.to_datetime(sales_data['ordered_at'])
    sales_data['price'] = sales_data['price'].str[1:-1]
    sales_data['line_total'] = sales_data['line_total'].str[1:-1]

    sales_data['price'] = pd.to_numeric(sales_data['price'])
    sales_data['line_total'] = pd.to_numeric(sales_data['line_total'])
    print("sale_data types: {0}".format(sales_data.dtypes))
    print("sale_data head: {0}".format(sales_data.head()))

    print("duplicated: {0}".format(sales_data[sales_data.duplicated()].shape[0]))

    sales_data = sales_data.dropna(how='any', inplace=False)
    sales_data.drop_duplicates(subset=None, inplace=False, keep='first')

    print("isnull sum: {0}".format(sales_data.isnull().sum()))

    print("is null name:{0}".format(sales_data[sales_data['name'].isnull()].head()))

    print("not =: {0}".format(sales_data[(sales_data['price'] * sales_data['quantity']) != sales_data['line_total']].shape[0]))

    cols = []
    sales_data['line_total'] = sales_data['price'] * sales_data['quantity']

    sales_data = sales_data.drop(sales_data[sales_data.line_total < 0].index)
    print("sales_data describe:")

    print(sales_data.describe())

    sales_data['category'] = sales_data['name'].str.extract('(\".*\").*')
    sales_data['name'] = sales_data['name'].str.extract('\".*\"(.*)')

    print(sales_data['name'])
    print(sales_data['category'])

    print(sales_data.head(n = 10))