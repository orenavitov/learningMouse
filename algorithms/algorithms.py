import collections
import array
'''
    问题： 对一个数字进行分解， 例如：
    1 = 1
    2 = 1+1 or 2
    3 = 1+1+1 or 1+2 or 3
    4 = 1+1+1+1 or 1+1+2 or 1+3 or 2+2 or 4
    ...
    '''


def number_distritube_1(number):
    '''
    distritube_map: {
        current_number: {
            # c = a + b
            # first_number 表示第一个加数， 如上式中的'a'
            # first_number 对应的链表是一个二维的链表
            first_number: []
        }
    }
    '''
    distritube_map = {}
    current_number_map = dict([(1, [[1]])])
    distritube_map[1] = current_number_map
    current_number = 2
    while (current_number <= number):
        sub_number_distritube_map = {}
        for i in range(int(current_number / 2) + 1):

            if (i >= 1):

                # 只分解第二个加数， 不分解第一个加数
                # 第一个加数
                first_number = i
                # 第二个加数
                second_number = current_number - first_number
                first_number_distritube_map = distritube_map[first_number]
                second_number_distritube_map = distritube_map[second_number]
                sub_number_distritube_lists = []
                for sub_number in second_number_distritube_map.keys():
                    if (sub_number >= first_number):
                        distritube_lists = second_number_distritube_map[sub_number]
                        for distritube_list in distritube_lists:
                            sub_number_distritube_list = []
                            sub_number_distritube_list.extend(distritube_list)
                            sub_number_distritube_list.append(first_number)
                            sub_number_distritube_lists.append(sub_number_distritube_list)
                        sub_number_distritube_map[first_number] = sub_number_distritube_lists
        sub_number_distritube_map[current_number] = [[current_number]]
        distritube_map[current_number] = sub_number_distritube_map
        current_number += 1
    return distritube_map


result = {}


def sub_distritube(number):
    result.setdefault(number, [])
    distritube_number = number
    while (distritube_number >= 1):
        if (distritube_number == 1):
            result[number].append([number])
        else:
            other_needed_distritube = number - distritube_number
            if other_needed_distritube == 0:
                temp_list = []
                for i in range(distritube_number):
                    temp_list.append(1)
                result[number].append(temp_list)
            else:
                distritube_lists = result[other_needed_distritube]
                if other_needed_distritube > distritube_number:
                    temp_lists = []
                    for distritube_list in distritube_lists:
                        if len(distritube_list) <= distritube_number:
                            temp_list = []
                            for i in range(len(distritube_list)):
                                temp_list.append(distritube_list[i] + 1)
                            for i in range(distritube_number - len(distritube_list)):
                                temp_list.append(1)
                            temp_lists.append(temp_list)
                    result[number].extend(temp_lists)
                    del temp_lists
                else:
                    temp_lists = []
                    for distritube_list in distritube_lists:
                        temp_list = []
                        for i in range(len(distritube_list)):
                            temp_list.append(distritube_list[i] + 1)
                        for i in range(distritube_number - len(distritube_list)):
                            temp_list.append(1)
                        temp_lists.append(temp_list)
                    result[number].extend(temp_lists)
                    del temp_lists
        distritube_number -= 1


def number_distritube_2(number):
    current_number = 1
    while (current_number <= number):
        sub_distritube(current_number)
        current_number += 1


'''
有1分，2分， 5分， 10分的硬币， 每种硬币无限多个， 输入n分钱， 有多少种方式组成这n分钱；
思路：假设x1分钱有m中组成方式， 则x1+1有m种组成方式， x1+2有m中组成方式， x1+3有m种组成方式，
上述只是使用1分硬币的情况下， 使用2分硬币， x1+3有m+m种组成方式
'''


def f(n):
    V = [1, 2, 5]
    C = [0 for _ in range(n + 1)]
    C[0] = 1
    for v in V:
        for i in range(v, n + 1):
            if i - v >= 0:
                C[i] += C[i - v]
    return C


'''
堆排序实现
tree表示输入的完全二叉树， 用数组表示
'''

def local_adjust(tree, r, L, R):
    if L > len(tree) - 1:
        return
    temp = tree[r]
    if R <= len(tree) - 1:
        if tree[L] > tree[r] and tree[L] > tree[R]:
            tree[r] = tree[L]
            tree[L] = temp
            r = L
            L = L * 2 + 1
            R = L * 2 + 2
            local_adjust(tree, r, L, R)
        elif tree[R] > tree[r] and tree[R] > tree[L]:
            tree[r] = tree[R]
            tree[R] = temp
            r = R
            L = R * 2 + 1
            R = R * 2 + 2
            local_adjust(tree, r, L, R)
    else:
        if tree[L] > tree[r]:
            tree[r] = tree[L]
            tree[L] = temp

def adjust(tree):
    # 最后一个叶子节点的索引
    last_leaf_index = len(tree) - 1
    # 最后一个非叶子节点索引
    last_no_leaf_index = int(last_leaf_index / 2) if last_leaf_index % 2 == 1 else int(last_leaf_index / 2 - 1)
    while last_no_leaf_index >= 0:
        # temp = tree[last_no_leaf_index]
        left_child_index = last_no_leaf_index * 2 + 1
        right_child_index = last_no_leaf_index * 2 + 2
        local_adjust(tree, last_no_leaf_index, left_child_index, right_child_index)
        last_no_leaf_index -= 1
    return tree

def Solution(m, n):

    class state():
        def __init__(self, s, step):
            self.s = s
            self.step = step

        def set_s(self, s):
            self.s = s
            return self
        def get_s(self):
            return self.s

        def set_step(self, step):
            self.step = step

        def get_step(self):
            return self.step

    count = 0
    step = 0
    """
    状态1： 接下来有3种走法
    状态2： 接下来有5种走法
    状态3： 接下来有8种走法
    """
    current = state(s = 1, step = 1)

    def next(current, stop, pre_count):
        global  count
        s = current.get_s()
        step = current.get_step()
        if step < stop:
            if s == 1:
                current_count = pre_count * 3
                current.set_step(step + 1)
                next(current = current.set_s(2), stop = stop, pre_count = current_count)
                next(current = current.set_s(3), stop=stop, pre_count = current_count)

            if s == 2:
                current_count = pre_count * 5
                current.set_step(step + 1)
                next(current=current.set_s(1), stop=stop, pre_count = current_count)
                next(current=current.set_s(3), stop=stop, pre_count = current_count)
            if s == 3:
                current_count = pre_count * 8
                current.set_step(step + 1)
                next(current=current.set_s(1), stop=stop, pre_count = current_count)
                next(current=current.set_s(2), stop=stop, pre_count = current_count)
        else:
            count = count + pre_count

    def get_ways(s):
        global count
        if (s == 1):
            count = 9
        else:
            state_1 = state(s=1, step=1)
            state_2 = state(s=2, step=1)
            state_3 = state(s=3, step=1)
            next(state_1, s, pre_count=4)
            next(state_2, s, pre_count=4)
            next(state_3, s, pre_count=1)

    for i in range(m, n + 1):
        get_ways(i)

    return count

def test1(n):
    i = 1
    sum_days = 0
    sum_products = 0
    while(sum_days <= n):

        if (sum_days + i > n):
            sum_products = sum_products + (n - sum_days) * i
            break
        else:
            sum_days = sum_days + i
            sum_products = sum_products + i * i
            i = i + 1
    return sum_products

def test2(pre_state, step, stop, count):
    if step < stop:
        if pre_state == 1:
            step = step + 1
            return test2(2, step, stop, count * 2) + test2(3, step, stop, count * 1)


        if pre_state == 2:
            step = step + 1
            return test2(1, step, stop, count * 2) + test2(3, step, stop, count * 1)


        if pre_state == 3:
            step = step + 1
            return test2(1, step, stop, count * 4) + test2(2, step, stop, count * 4)

    else:
        return count

def test_(stop):
    count = 0
    if stop == 1:
        count = 9
    else:
        count = test2(1, 1, stop, 4) + test2(2, 1, stop, 4) + test2(3, 1, stop, 1)
    return count

if __name__ == '__main__':
    m = 1
    n = 2
    sum = 0
    for stop in range(m, n + 1):
        sum = sum + test_(stop)
    print("sum is {0}".format(sum))