"""
把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  

示例 1：

输入：[3,4,5,1,2]
输出：1
示例 2：

输入：[2,2,2,0,1]
输出：0

链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
"""
class Solution1:
    def minArray(self, numbers):
        numbers_size = len(numbers)
        if (numbers <= 0):
            return None
        start = numbers[0]

        for i in range(numbers_size):
            if (numbers[i] < start):
                    return numbers[i]
        return start

"""
给你两个数组 nums1 和 nums2 。

请你返回 nums1 和 nums2 中两个长度相同的 非空 子序列的最大点积。

数组的非空子序列是通过删除原数组中某些元素（可能一个也不删除）后剩余数字组成的序列，但不能改变数字间相对顺序。比方说，[2,3,5] 是 [1,2,3,4,5] 的一个子序列而 [1,5,3] 不是。

 

示例 1：

输入：nums1 = [2,1,-2,5], nums2 = [3,0,-6]
输出：18
解释：从 nums1 中得到子序列 [2,-2] ，从 nums2 中得到子序列 [3,-6] 。
它们的点积为 (2*3 + (-2)*(-6)) = 18 。
示例 2：

输入：nums1 = [3,-2], nums2 = [2,-6,7]
输出：21
解释：从 nums1 中得到子序列 [3] ，从 nums2 中得到子序列 [7] 。
它们的点积为 (3*7) = 21 。
示例 3：

输入：nums1 = [-1,-1], nums2 = [1,1]
输出：-1
解释：从 nums1 中得到子序列 [-1] ，从 nums2 中得到子序列 [1] 。
它们的点积为 -1 。

思路：
算是一个二维的动态规划问题， 每个数组的每个元素都有两种状态， 取或者不取；
首先初始化一个二维矩阵， 行数为nums1的长度加一， 列数为nums2的长度加一， 为什么加一：因为每个元素两种状态，所以两个元素组合可以有4种状态；
二维矩阵的第i行，第j列表示，nums1前i个元素，nums2前j个元素所形成点积的最大值

链接：https://leetcode-cn.com/problems/max-dot-product-of-two-subsequences
"""
import copy
class Solution2:
    def maxDotProduct(self, nums1, nums2):
        max_length = min(len(nums1), len(nums2))
        if (max_length == 0):
            return 0
        result = []
        for i in range(1, max_length + 1):
            temp_result1 = []
            temp_result2 = []
            self.get_sub_nums([], nums1, i, temp_result1)
            self.get_sub_nums([], nums2, i, temp_result2)
            print("length : {0}, includes : {1}".format(i, temp_result1))
            print("length : {0}, includes : {1}".format(i, temp_result2))
            for vector1 in temp_result1:
                for vector2 in temp_result2:
                    result.append(self.dot(vector1, vector2))
        return max(result)
    def get_sub_nums(self, nums, left_nums, left_length, temp_result):

        if (len(left_nums) < left_length):
            return

        if (left_length == 0):
            temp_result.append(nums)
            return
        for i in range(len(left_nums)):
            copy_nums = copy.deepcopy(nums)
            copy_nums.append(left_nums[i])
            self.get_sub_nums(copy_nums, left_nums[i + 1: len(left_nums)], left_length - 1, temp_result)

    def dot(self, v1, v2):
        result = [v * v2[i] for i, v in enumerate(v1)]
        return sum(result)
# s2 = Solution2()
nums1 = [3,-2]

nums2 = [2,-6,7]
# result = s2.maxDotProduct(nums1, nums2)
# print(result)
import numpy
import sys

class Other_Solution2:
    def maxDotProduct(self, nums1, nums2):
        row_count = len(nums1)
        col_count = len(nums2)
        result = []
        for i in range(row_count + 1):
            row_result = []
            for j in range(col_count + 1):
                row_result.append(-sys.maxsize)
            result.append(row_result)

        for i in range(1, row_count + 1):
            for j in range(1, col_count + 1):
                result[i][j] = max(result[i -1][j], result[i][j - 1])
                result[i][j] = max(result[i][j], result[i - 1][j - 1])
                result[i][j] = max(result[i][j], max(0, result[i - 1][j - 1]) + nums1[i - 1] * nums2[j - 1])
        return result[row_count][col_count]

o_s2 = Other_Solution2()
result = o_s2.maxDotProduct(nums1, nums2)
print(result)