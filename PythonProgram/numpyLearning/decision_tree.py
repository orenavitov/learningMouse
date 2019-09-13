# 决策树算法

import math
import numpy
import copy
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

    # 计算第index个属性的信息增益
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




    # ID3算法
    # input_D: 输入的数据集
    # remain_attrs: 待划分的属性
    # distritube_attr:
    # layer: 当前待划分节点是决策树的第几层
    def ID3(self, input_D, remain_attrs, distritube_attr, layer):
        # if distritube_attr == None:
        #     print("start!")
        #     print("layer{0}:{1}".format(layer, input_D))
        # else:
        #     print()
        #     print("layer{0}:{1}:{2}".format(layer, distritube_attr, input_D))

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
            layer += 1
            best_attr, attr_detial = self.ID3_select_best_attr(input_D, remain_attrs_copy)
            best_attr_index = self.attrs.index(best_attr)
            remain_attrs_copy.remove(best_attr)
            for remain_attr_value in attr_detial[best_attr].keys():
                next_input_d = []
                for d in input_D:
                    if d[best_attr_index] == remain_attr_value:
                        next_input_d.append(d)
                self.ID3(next_input_d, remain_attrs_copy, best_attr, layer)

    def C4_5(self, input_D, remain_attrs, layer):

        pass


attr_1 = numpy.random.randint(1, 4, size = [1, 30])
attr_2 = numpy.random.randint(1, 3, size = [1, 30])
attr_3 = numpy.random.randint(1, 4, size = [1, 30])
attr_4 = numpy.random.randint(1, 3, size = [1, 30])
attr_5 = numpy.random.randint(1, 4, size = [1, 30])
attr_6 = numpy.random.randint(1, 3, size = [1, 30])
label = numpy.random.randint(1, 3, size = [1, 30])
D = []
D.extend(attr_1)
D.extend(attr_2)
D.extend(attr_3)
D.extend(attr_4)
D.extend(attr_5)
D.extend(attr_6)
D.extend(label)
attrs = [
    'attr_1',
    'attr_2',
    'attr_3',
    'attr_4',
    'attr_5',
    'attr_6',
]
labels = [
    'label_1',
    'label_2'
]
if __name__ == '__main__':
    D = numpy.array(D).T
    print(D)
    decision_tree = DecisionTree(D, attrs, labels)
    decision_tree.ID3(D, attrs, None, 0)
