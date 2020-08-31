"""
给定字符串 s 和 t ，判断 s 是否为 t 的子序列。

你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。

示例 1:
s = "abc", t = "ahbgdc"

返回 true.

示例 2:
s = "axc", t = "ahbgdc"

返回 false.

后续挑战 :

如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？

链接：https://leetcode-cn.com/problems/is-subsequence
"""
class Solution1:
    def isSubsequence(self, s, t):
        result = True
        if (len(s) == 0):
            return True
        if (len(s) != 0 and len(t) == 0):
            return False
        self.char_index_map = {}
        t_chars = list(t)
        for i, char in enumerate(t_chars):
            if (char not in self.char_index_map.keys()):
                self.char_index_map[char] = []
            self.char_index_map[char].append(i)

        s_chars = list(s)
        temp_index = 0
        for char in s_chars:
            if(char not in self.char_index_map.keys()):
                result = False
                break
            char_indexes = self.char_index_map[char]
            exist_next = False
            for index in char_indexes:
                if index >= temp_index:
                    temp_index = index + 1
                    exist_next = True
                    break
            if (exist_next == False):
                result = False
                break
        return result

"""
给定一个整数数组 nums ，小李想将 nums 切割成若干个非空子数组，使得每个子数组最左边的数和最右边的数的最大公约数大于 1 。
为了减少他的工作量，请求出最少可以切成多少个子数组。

示例 1：

输入：nums = [2,3,3,2,3,3]

输出：2

解释：最优切割为 [2,3,3,2] 和 [3,3] 。第一个子数组头尾数字的最大公约数为 2 ，第二个子数组头尾数字的最大公约数为 3 。

示例 2：

输入：nums = [2,3,5,7]

输出：4

解释：只有一种可行的切割：[2], [3], [5], [7]

链接：https://leetcode-cn.com/problems/qie-fen-shu-zu
"""
class Solution2:
    def splitArray(self, nums):
        N = len(nums)
        if (N <= 1):
            return nums
        result = []
        for num in nums:
            result.append([num])
        common_divisors = nums
        start_index = 0
        end_index = N - 1
        while(start_index < N - 1 and end_index != start_index):

            while(end_index > start_index):
                left_common_divisors = common_divisors[start_index]
                right_common_divisors = common_divisors[end_index]
                if left_common_divisors == right_common_divisors:
                    result, common_divisors = self.merge(result, common_divisors, start_index, end_index, right_common_divisors)
                    start_index = start_index + 1
                    end_index = len(common_divisors) - 1
                    break
                if left_common_divisors > right_common_divisors:
                    common_divisor = self.findMaxCommonDivisor(left_common_divisors, right_common_divisors)
                    if common_divisor > 1:
                        result, common_divisors = self.merge(result, common_divisors, start_index, end_index, common_divisor)
                        start_index = start_index + 1
                        end_index = len(common_divisors) - 1
                        break
                    else:
                        end_index = end_index - 1
                if left_common_divisors < right_common_divisors:
                    common_divisor = self.findMaxCommonDivisor(right_common_divisors, left_common_divisors)
                    if common_divisor > 1:
                        result, common_divisors = self.merge(result, common_divisors, start_index, end_index, common_divisor)
                        start_index = start_index + 1
                        end_index = len(common_divisors) - 1
                        break
                    else:
                        end_index = end_index - 1

        return len(result)

    def merge(self, input, common_divisors, start_index, end_index, value):
        temp = []
        for i in range(start_index, end_index + 1):
            temp.extend(input[i])
        common_divisors[end_index] = value
        common_divisors = common_divisors[end_index:]
        input[end_index] = temp
        input = input[end_index:]
        return input, common_divisors


    def findMaxCommonDivisor(self, a, b):
        temp = a % b
        if (temp == 0):
            return b
        else:
            bigger = b
            smaller = temp
            return self.findMaxCommonDivisor(bigger, smaller)
nums = [2,3,3,2,3,3]
s2 = Solution2()
print(s2.splitArray(nums))
