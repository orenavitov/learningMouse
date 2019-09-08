# 决策树算法

import math
import numpy

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

    def get_attrAndlabel_map(self, input_D, remain_attrs):
        attrs_size = len(remain_attrs)
        # attr_detial形式： {attr_name: {attr_value: {label_1 : n_1, label_2 : n_2, ... , label_n : n_n}}}
        attr_detial = {}
        # label_detial形式： {label_value: count}
        label_detial = {}
        for i in range(remain_attrs):
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

    # 计算第index个属性的
    def ent(self, attr_class, attr_detial, label_detial):
        entValue = 0.0
        if (attr_class == None):
            for label in label_detial:
                entValue += -(label_detial[label] / self.attrs_number) *\
                            math.log(label_detial[label] / self.attrs_number, 2)

        else:
            attr_distritube = attr_detial[attr_class]
            for attr_value in attr_distritube.keys():
                _sum = 0
                for label in attr_distritube[attr_value].keys():
                    _sum += attr_distritube[attr_value][label]
                for label in attr_distritube[attr_value].keys():
                    entValue += -(attr_distritube[attr_value][label] / self.attrs_number) * math.log(
                        attr_distritube[attr_value][label] / self.attrs_number, 2
                    )
        return entValue


    def ID3_select_best_attr(self, input_D, remain_attrs):
        attr_detial, label_detial = self.get_attrAndlabel_map(input_D, remain_attrs)
        max_ent = 0
        best_attr = remain_attrs[0]
        for attr_class in remain_attrs:
            current_ent = self.ent(attr_class, attr_detial, label_detial)
            if current_ent > max_ent:
                best_attr = attr_class
                max_ent = current_ent
        return best_attr

    # ID3算法
    def ID3(self, input_D, remain_attrs, distritube_attr):
        print("distritube attr is {0}".format(distritube_attr))
        remain_attrs = remain_attrs
        pre_label = input_D[0][-1]
        need_devision = False
        # 没有属性可以继续划分了就返回
        if len(remain_attrs) == 0:
            return

        for d in input_D:
            if d[-1] == pre_label:
                continue
            else:
                need_devision =True
                break
        if need_devision:
            best_attr = self.ID3_select_best_attr(input_D, remain_attrs)
            best_attr_index = self.attrs.index(best_attr)
            print("the best attr is ", best_attr)
            remain_attrs = remain_attrs.remove(best_attr)
            for remain_attr_value in remain_attrs[best_attr].keys():
                next_input_d = []
                for d in input_D:
                    if d[best_attr_index] == remain_attr_value:
                        next_input_d.append(d)
                self.ID3(next_input_d, remain_attrs, best_attr_index)




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
