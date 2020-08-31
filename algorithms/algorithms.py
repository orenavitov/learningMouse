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


def dijkstra(A, node):
    N = len(A)
    # result 存储当前从node节点到其他节点的最短距离
    result = {}
    # find 存储已经找到的最短路径的点
    find = []
    find.append(node)
    result[node] = 0
    next_start = [node]
    while(len(find) != N and len(next_start) != 0):
        min_distance = float('inf')
        min_node = []
        for i in range(len(next_start)):
            start = next_start[0]
            next_start.remove(start)
            neighbors_dstance = A[start]

            for neighbor, distance in enumerate(neighbors_dstance):
                if distance != 0 and neighbor not in find:

                    if neighbor not in result.keys():
                        result[neighbor] = distance + result[start]
                        if (distance + result[start] < min_distance):
                            min_node.clear()
                            min_node.append(neighbor)
                            min_distance = distance + result[start]
                        else:
                            if (distance + result[start] == min_distance):
                                min_node.append(neighbor)
                    else:
                        if (result[start] + distance < result[neighbor]):
                            result[neighbor] = result[start] + distance
                            if (distance + result[start] < min_distance):
                                min_node.clear()
                                min_node.append(neighbor)
                                min_distance = distance + result[start]
                            else:
                                if (distance + result[start] == min_distance):
                                    min_node.append(neighbor)
                        else:
                            if (result[neighbor] < min_distance):
                                min_node.clear()
                                min_node.append(neighbor)
                                min_distance = result[neighbor]
                            else:
                                if (distance + result[start] == min_distance):
                                    min_node.append(neighbor)
        next_start = min_node
        find.extend(min_node)
    return result
# A = [[0, 0, 1, 0, 0, 1, 0, 1, 0, 0],
#  [0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
#  [1, 0, 0, 1, 0, 0, 0, 1, 0, 0],
#  [0, 1, 1, 0, 0, 0, 1, 0, 1, 0],
#  [0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
#  [1, 0, 0, 0, 1, 0, 0, 0, 0, 0],
#  [0, 0, 0, 1, 1, 0, 0, 0, 0, 0],
#  [1, 0, 1, 0, 1, 0, 0, 0, 1, 1],
#  [0, 0, 0, 1, 1, 0, 0, 1, 0, 0],
#  [0, 0, 0, 0, 0, 0, 0, 1, 0, 0]]
A = [
    [0, 1, 0, 0, 0],
    [1, 0, 4, 0, 2],
    [0, 4, 0, 2, 5],
    [0, 0, 2, 0, 0],
    [0, 2, 5, 0, 0]
]

# 快排， 从小到大
def fastSort(nums, start, end):

    if (start >= end):
        return
    forward_index = start
    backward_index = end
    target_num = nums[start]
    # temp_num = target_num
    while(forward_index < backward_index):
        if (nums[backward_index] < target_num):
            nums[forward_index] = nums[backward_index]
            forward_index = forward_index + 1
        else:
            backward_index = backward_index - 1
            continue
        while(forward_index < backward_index):
            if (nums[forward_index] > target_num):
                nums[backward_index] = nums[forward_index]
                backward_index = backward_index - 1
            else:
                forward_index = forward_index + 1
    nums[backward_index] = target_num
    fastSort(nums, start, backward_index)
    fastSort(nums, backward_index + 1, end)



nums = [2, 4, 9, 7, 100, 200, 150]


if __name__ == '__main__':
    fastSort(nums, 0, len(nums) - 1)
    print(nums)