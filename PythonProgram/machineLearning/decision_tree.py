# 决策树算法

import math
import numpy
import copy


class Tree_node:

    def __init__(self, context, childs, name, value):
        self.name = name
        self.context = context
        self.childs = childs
        self.value = value

    def add_name(self, name):
        self.name = name

    def add_value(self, value):
        self.value = value

    def add_child(self, child):
        if child != None:
            if self.childs == None:
                self.childs = []
            self.childs.append(child)

    def get_context(self):
        return self.context

    def get_childs(self):
        return self.childs

    def get_name(self):
        return self.name

    def get_value(self):
        return self.value


class DecisionTree:

    # D: 数据集
    def __init__(self, D, attrs, labels):
        self.D = D
        # 数据总量
        self.D_count = len(D)
        # 特征属性数量
        self.attrs_number = len(D[0])

        self.attrs = attrs
        self.labels = labels
        # 初始化信息熵
        self.ent_D = 0.0
    def get_attrAndlabel_map(self, input_D, remain_attrs):
        attrs_size = len(remain_attrs)
        # attr_detial形式： {attr_name: {attr_value: {label_1 : n_1, label_2 : n_2, ... , label_n : n_n}}}
        attr_detial = {}
        # label_detial形式： {label_value: count}
        label_detial = {}
        for i in range(len(remain_attrs)):
            attr_class = remain_attrs[i]
            attr_detial[attr_class] = {}
            index = self.attrs.index(attr_class)
            for d in input_D:
                attr_value = d[index]
                if attr_value not in attr_detial[attr_class].keys():
                    attr_detial[attr_class][attr_value] = {}
                    label = d[-1]
                    attr_detial[attr_class][attr_value][label] = 1
                else:
                    label = d[-1]
                    if label not in attr_detial[attr_class][attr_value].keys():
                        attr_detial[attr_class][attr_value][label] = 1
                    else:
                        attr_detial[attr_class][attr_value][label] += 1

        for d in input_D:
            label = d[-1]
            if label not in label_detial.keys():
                label_detial[label] = 1
            else:
                label_detial[label] += 1
        return attr_detial, label_detial

    # 计算attr_class的信息增益
    def Gain(self, attr_class, attr_detial, label_detial):
        # 信息熵
        entValue = 0.0
        # 条件信息熵
        entValue_ = 0.0
        if (self.ent_D == 0.0):
            for label in label_detial:
                entValue -= (label_detial[label] / self.D_count) *\
                            math.log(label_detial[label] / self.D_count, 2)
            self.ent_D = entValue

        attr_distritube = attr_detial[attr_class]
        for attr_value in attr_distritube.keys():
            _sum = 0.0
            each_attr_ent = 0.0
            for label in attr_distritube[attr_value].keys():
                _sum += attr_distritube[attr_value][label]
            for label in attr_distritube[attr_value].keys():
                each_attr_ent -= (attr_distritube[attr_value][label] / _sum) * math.log(
                    attr_distritube[attr_value][label] / _sum, 2
                )
            entValue_ += (_sum / self.D_count) * each_attr_ent
        return self.ent_D - entValue_

    # 计算每个属性的信息增益， 选择最好的划分属性
    def ID3_select_best_attr(self, input_D, remain_attrs):
        attr_detial, label_detial = self.get_attrAndlabel_map(input_D, remain_attrs)
        max_gain = 0
        best_attr = remain_attrs[0]
        for attr_class in remain_attrs:
            currrnt_gain = self.Gain(attr_class, attr_detial, label_detial)
            if currrnt_gain > max_gain:
                best_attr = attr_class
                max_gain = currrnt_gain
        return best_attr, attr_detial

    def C4_5_select_best_attr(self, input_D, remain_attrs):
        attr_detial, label_detial = self.get_attrAndlabel_map(input_D, remain_attrs)
        # 保存每种属性的信息增益
        attr_class_gain_map = {}
        # 每种熟悉感的信息增益率
        attr_class_gain_ratio_map = {}
        for attr_class in remain_attrs:
            gain = self.Gain(attr_class, attr_detial, label_detial)
            attr_class_gain_map[attr_class] = gain
        ## 计算平均的信息增益
        sum_gain = 0.0
        for attr_class in attr_class_gain_map.keys():
            sum_gain += attr_class_gain_map[attr_class]
        average_gain = sum_gain / len(remain_attrs)
        ## 找出高于平均信息增益的属性
        remain_attrs_ = []
        for attr_class in attr_class_gain_map.keys():
            if attr_class_gain_map[attr_class_gain_map] > average_gain:
                remain_attrs_.append(attr_class)
        ## 在高于平均信息增益的属性中找出增益率最高的属性作为划分的最优属性
        for attr_class in remain_attrs_:
            attr_values = attr_detial[attr_class].keys()
            I_V = 0.0
            for attr_value in attr_values:
                # sum_用于计算每种属性的每一个值的总数
                sum_ = 0.0
                attr_value_keys = attr_detial[attr_class][attr_value].keys()
                for attr_value_key in attr_value_keys:
                    sum_ += attr_detial[attr_class][attr_value][attr_value_key]
                I_V += -(sum_ / self.D_count) * math.log((sum_ / self.D_count), 2)
            attr_class_gain_ratio_map[attr_class] = attr_class_gain_map[attr_class] / I_V
        ## 在attr_class_gain_ratio_map中找到信息增益率最高的属性
        max_gain_ratio = 0.0
        best_attr = remain_attrs_[0]
        for attr_class in remain_attrs_:
            if attr_class_gain_ratio_map[attr_class] > best_attr:
                max_gain_ratio = attr_class_gain_ratio_map[attr_class]
                best_attr = attr_class
        return best_attr, attr_detial

    # ID3算法
    # input_D: 输入的数据集
    # remain_attrs: 待划分的属性
    # layer: 当前待划分节点是决策树的第几层
    # distritube_name: 本节点名字， 依据什么属性被划分的
    def ID3(self, input_D, remain_attrs, distritubed_name, distritube_value):

        if distritubed_name == None:
            node_name = "root"
        else:
            node_name = distritubed_name

        # 初始化一个树节点
        node = Tree_node(context=input_D, childs = None, name = node_name, value = distritube_value)

        # 对待划分属性机型保存
        remain_attrs_copy = copy.copy(remain_attrs)
        pre_label = input_D[0][-1]
        need_devision = False
        # 没有属性可以继续划分了就返回
        if len(remain_attrs_copy) == 0:
            return
        # 如果所有的训练数据属于同一个类别则停止划分
        for d in input_D:
            if d[-1] == pre_label:
                continue
            else:
                need_devision =True
                break
        if need_devision:
            best_attr, attr_detial = self.ID3_select_best_attr(input_D, remain_attrs_copy)
            best_attr_index = self.attrs.index(best_attr)
            remain_attrs_copy.remove(best_attr)
            for remain_attr_value in attr_detial[best_attr].keys():
                next_input_d = []
                for d in input_D:
                    if d[best_attr_index] == remain_attr_value:
                        next_input_d.append(d)
                node.add_child(self.ID3(next_input_d, remain_attrs_copy, best_attr, remain_attr_value))
        return node
    def C4_5(self, input_D, remain_attrs, layer):
        # 对待划分属性机型保存
        remain_attrs_copy = copy.copy(remain_attrs)
        pre_label = input_D[0][-1]
        need_devision = False
        # 没有属性可以继续划分了就返回
        if len(remain_attrs_copy) == 0:
            return
        # 如果所有的训练数据属于同一个类别则停止划分
        for d in input_D:
            if d[-1] == pre_label:
                continue
            else:
                need_devision = True
                break
        if need_devision:
            layer += 1
            best_attr, attr_detial = self.C4_5_select_best_attr(input_D, remain_attrs_copy)
            best_attr_index = self.attrs.index(best_attr)
            remain_attrs_copy.remove(best_attr)
            for remain_attr_value in attr_detial[best_attr].keys():
                next_input_d = []
                for d in input_D:
                    if d[best_attr_index] == remain_attr_value:
                        next_input_d.append(d)
                self.C4_5(next_input_d, remain_attrs_copy, layer)
        pass


# attr_1 = numpy.random.randint(1, 4, size = [1, 30])
# attr_2 = numpy.random.randint(1, 3, size = [1, 30])
# attr_3 = numpy.random.randint(1, 4, size = [1, 30])
# attr_4 = numpy.random.randint(1, 3, size = [1, 30])
# attr_5 = numpy.random.randint(1, 4, size = [1, 30])
# attr_6 = numpy.random.randint(1, 3, size = [1, 30])
# label = numpy.random.randint(1, 3, size = [1, 30])
# D = []
# D.extend(attr_1)
# D.extend(attr_2)
# D.extend(attr_3)
# D.extend(attr_4)
# D.extend(attr_5)
# D.extend(attr_6)
# D.extend(label)
# attrs = [
#     'attr_1',
#     'attr_2',
#     'attr_3',
#     'attr_4',
#     'attr_5',
#     'attr_6',
# ]
# labels = [
#     'label_1',
#     'label_2'
# ]

# 根据输入的数据产生一个一维的高斯分布
def get_gaussian_distribution(D):
    sum = 0.0
    for d in D:
        sum += d
    # 均值
    averge = sum / len(D)
    sum_ = 0.0
    for d in D:
        sum_ += (d - averge) ** 2
    # 方差
    variance = sum_ / len(D)
    return averge, variance
# 一维的高斯分布
gassian = lambda x, average, variance: (1 / ((2 * math.pi) ** 0.5 * variance ** 0.5)) * \
                                    math.e ** (-(x - average) ** 2 / 2 * variance)

# 数据集为鸢尾花数据
# 特征值包括sepal length（萼片长度）， sepal width（萼片宽度）， petal length（花瓣长度）， petal width（花瓣宽度）
# 标签为鸢尾花的种类：1：Iris Setosa， 2：Iris Versicolour， 3：Iris Virginica
# def data_handle(D, attrs, labels):
#     # 训练数据集
#     train_data = {}
#     for label in labels:
#         train_data[label] = []
#     # 测试数据集
#     test_data = []
#     with open(r"E:\train_data\Iris\bezdekIris.data", "r") as file:
#         for line in file.readlines(""):
#             # 去掉行尾的换行符
#             line_ = line[:-1]
#
#             label = line_.split(",")[-1]
#             d = [line_.split(",")[:-1]]
#             if numpy.random.randint(0, 9) > 3:
#                 train_data[label].append(d)
#             else:
#                 test_data.append(d)
#     train_data = numpy.array(train_data)
#     test_data = numpy.array(test_data)
#     # 每个属性高斯分布的（均值， 方差）
#     gassians = []
#     for label in train_data.keys():
#         label_gassian = []
#         label_data = train_data[label]
#         for label_attr_data in label_data.T:
#             average, variance = get_gaussian_distribution(label_attr_data)
#             label_gassian.append(tuple(average, variance))
#     gassians.append(label_gassian)
#
#     D_ = []
#     for d in D:
#         d_ = []
#         for attr_d in d:
# 使用二分法， 将离散数据进行转化
def transform_attr_data(attr_data):
    list_attr_data = list(attr_data)
    list_attr_data.sort()
    split_number = list_attr_data[int(len(attr_data) / 2)]
    for i in range(len(attr_data)):
        # 如果不大于中位数则设为1，否者设为2
        if attr_data[i] <= split_number:
            attr_data[i] = 1
        else:
            attr_data[i] = 2
    return attr_data

# 数据集为鸢尾花数据
# 特征值包括sepal length（萼片长度）， sepal width（萼片宽度）， petal length（花瓣长度）， petal width（花瓣宽度）
# 标签为鸢尾花的种类：1：Iris Setosa， 2：Iris Versicolour， 3：Iris Virginica
def data_handle():
    data = []
    train_data = []
    test_data = []
    with open(r"E:\train_data\Iris\bezdekIris.data", "r") as file:
        for line in file.readlines():
            # 去掉行尾的换行符
            line_ = line[:-1]
            d = line_.split(",")
            if len(d) > 1:
                data.append(d)
    lines = len(data)
    attr_with_label_count = len(data[0])
    for i in range(attr_with_label_count):
        if i == attr_with_label_count - 1:
            for j in range(lines):
                if data[j][i] == "Iris-setosa":
                    data[j][i] = 1
                    continue
                if data[j][i] == "Iris-versicolor":
                    data[j][i] = 2
                    continue
                if data[j][i] == "Iris-virginica":
                    data[j][i] = 3
                    continue
        else:
            temp_list = []
            for j in range(lines):
                temp_list.append(data[j][i])
            temp_list.sort()
            mid = temp_list[int(lines / 2)]
            for j in range(lines):
                value = data[j][i]
                if value <= mid:
                    data[j][i] = 1
                else:
                    data[j][i] = 2
            temp_list.clear()
    for i in range(lines):
        flag = numpy.random.randint(0, 10)
        if flag <= 6:
            train_data.append(data[i])
        else:
            test_data.append(data[i])

    return train_data, test_data

def predict(root, test_data, attrs):
    if root.get_childs() != None:
        childs = root.get_childs()
        for child in childs:
            distritube_attr = child.get_name()
            distritube_value = child.get_value()
            data_value = test_data[attrs.index(distritube_attr)]
            if data_value == distritube_value:
                return predict(child, test_data, attrs)
    else:
        context = root.get_context()
        leaf = {}
        for item in context:
            if item[-1] not in leaf.keys():
                leaf[item[-1]] = 1
            else:
                leaf[item[-1]] += 1
        result = 1
        result_count = 0
        for key in leaf.keys():
            if leaf[key] >= result_count:
                result = key
                result_count = leaf[key]
        return result

if __name__ == '__main__':
    train_data, test_data = data_handle()
    attrs = [
        "sepal length",
        "sepal width",
        "petal length",
        "petal width"
    ]
    labels = [
        "Iris Setosa",
        "Iris Versicolour",
        "Iris Virginica"
    ]
    decision_tree = DecisionTree(train_data, attrs, labels)
    root = decision_tree.ID3(train_data, attrs, None, None)
    right = 0
    wrong = 0
    for data in train_data:
        result = predict(root, data, attrs)
        print("the real result is {0}, the predict result is {1}".format(data[-1], result))
        if data[-1] == result:
            right += 1
        else:
            wrong += 1
    print("the right rate is {0}".format(right / (right + wrong)))