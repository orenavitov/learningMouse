"""
给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，但是其方向必须向下(只能从父节点指向子节点方向)。

链接：https://leetcode-cn.com/problems/paths-with-sum-lcci
"""
# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution1:

    def pathSum(self, root, sum):
        self.result = []
        result = 0
        que = []
        if (root == None):
            return 0
        else:
            que.append(root)
        while(len(que) != 0):
            for i in range(len(que)):
                start = que[0]
                que.remove(start)
                path = []
                path = []
                self.go(start, sum, )
                if start.left != None:
                    que.append(start.left)
                if start.right != None:
                    que.append(start.right)
        return result
    def go(self, root, sum):
        if root == None:
            return 0
        sum = sum - root.val
        if sum == 0:
            return 1
        else:
            return self.go(root.left, sum) + self.go(root.right, sum)
root = TreeNode(5)
node1 = TreeNode(4)
node2 = TreeNode(8)
node3 = TreeNode(11)
node4 = TreeNode(13)
node5 = TreeNode(4)
node6 = TreeNode(7)
node7 = TreeNode(2)
node8 = TreeNode(5)
node9 = TreeNode(1)

root.left = node1
root.right = node2

node1.left = node3

node2.left = node4
node2.right = node5

node3.left = node6
node3.right = node7

node5.left = node8
node5.right = node9

# root = TreeNode(1)
# node1 = TreeNode(2)
# node2 = TreeNode(3)
# node3 = TreeNode(4)
# node4 = TreeNode(5)
# root.right = node1
# node1.right = node2
# node2.right = node3
# node3.right = node4
# 
# s =Solution()
# result = s.pathSum(root, 3)
# print(result)

"""
给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组
输入：[1,2,3,4,5,null,7,8]
        1
       /  \ 
      2    3
     / \    \ 
    4   5    7
   /
  8
输出：[[1],[2,3],[4,5,7],[8]]
"""
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None
class Solution2:
    def __init__(self):
        self.stack = []
        self.result = []
    def listOfDepth(self, tree):
        if (tree == None):
            return None
        self.stack.append(tree)
        while(len(self.stack) != 0):
            head = ListNode(0)
            temp_head = head
            for i in range(len(self.stack)):
                first_node = self.stack[0]
                self.stack.remove(first_node)
                node = ListNode(first_node.val)

                temp_head.next = node
                temp_head = node
                if first_node.right != None:
                    self.stack.append(first_node.right)
                if first_node.left != None:
                    self.stack.append(first_node.left)
            head = head.next
            self.result.append(head)
        return self.result

# s2 = Solution2()
# print(s2.listOfDepth(root))

"""
在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示.

一次移动定义为选择 0 与一个相邻的数字（上下左右）进行交换.

最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。

给出一个谜板的初始状态，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 

链接：https://leetcode-cn.com/problems/sliding-puzzle
输入：board = [[1,2,3],[4,0,5]]
输出：1
解释：交换 0 和 5 ，1 步完成
输入：board = [[1,2,3],[5,4,0]]
输出：-1
解释：没有办法完成谜板
输入：board = [[4,1,2],[5,0,3]]
输出：5
解释：
最少完成谜板的最少移动次数是 5 ，
一种移动路径:
尚未移动: [[4,1,2],[5,0,3]]
移动 1 次: [[4,1,2],[0,5,3]]
移动 2 次: [[0,1,2],[4,5,3]]
移动 3 次: [[1,0,2],[4,5,3]]
移动 4 次: [[1,2,0],[4,5,3]]
移动 5 次: [[1,2,3],[4,5,0]]
输入：board = [[3,2,4],[1,5,0]]
输出：14
"""


"""
一个厨师收集了他 n 道菜的满意程度 satisfaction ，这个厨师做出每道菜的时间都是 1 单位时间。

一道菜的 「喜爱时间」系数定义为烹饪这道菜以及之前每道菜所花费的时间乘以这道菜的满意程度，也就是 time[i]*satisfaction[i] 。

请你返回做完所有菜 「喜爱时间」总和的最大值为多少。

你可以按 任意 顺序安排做菜的顺序，你也可以选择放弃做某些菜来获得更大的总和。

示例 1：

输入：satisfaction = [-1,-8,0,5,-9]
输出：14
解释：去掉第二道和最后一道菜，最大的喜爱时间系数和为 (-1*1 + 0*2 + 5*3 = 14) 。每道菜都需要花费 1 单位时间完成。
示例 2：

输入：satisfaction = [4,3,2]
输出：20
解释：按照原来顺序相反的时间做菜 (2*1 + 3*2 + 4*3 = 20)
示例 3：

输入：satisfaction = [-1,-4,-5]
输出：0
解释：大家都不喜欢这些菜，所以不做任何菜可以获得最大的喜爱时间系数。
示例 4：

输入：satisfaction = [-2,5,-1,0,3,-3]
输出：35
链接：https://leetcode-cn.com/problems/reducing-dishes
"""

class Solution3:
    def maxSatisfaction(self, satisfaction):
        satisfaction = sorted(satisfaction)
        index = 0
        count = len(satisfaction)
        result = 0
        while(index < count):
            if (satisfaction[index] >= 0):
                break
            else:
                index = index + 1
        if index == count:
            return 0
        else:
            positive = satisfaction[index : count]
            negative = satisfaction[0 : index]
            count_positive = len(positive)
            count_negative = len(negative)
            for i, positive_value in enumerate(positive):
                result = result + (i + 1) * positive_value
            back_step = 0
            pre_sum = result
            pre_nums = positive
            while(back_step < count_negative):
                negative_value = negative[-1 - back_step]

                if (negative_value + pre_sum + sum(pre_nums) >= result):
                    result = negative_value + pre_sum + sum(pre_nums)
                pre_sum = negative_value + pre_sum + sum(pre_nums)
                pre_nums.append(negative_value)
                back_step = back_step + 1
        return result
s3 = Solution3()
print(s3.maxSatisfaction([-2,5,-1,0,3,-3]))
