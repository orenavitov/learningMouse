"""
给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。设计一个算法使得这 m 个子数组各自和的最大值最小。

注意:
数组长度 n 满足以下条件:

1 ≤ n ≤ 1000
1 ≤ m ≤ min(50, n)
示例:

输入:
nums = [7,2,5,10,8]
m = 2

输出:
18

解释:
一共有四种方法将nums分割为2个子数组。
其中最好的方式是将其分为[7,2,5] 和 [10,8]，
因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。

链接：https://leetcode-cn.com/problems/split-array-largest-sum

思路：
可与这么想： 我们的目的是寻找一个分割数， 使得nums可以分成m个子数组， 如果m = 1， 那么结果时32， 如果m = 5, 那么结果是10
那么如果  1<m<5, 应该存在一个分割数使得分成的m个子数组和尽量接近这个分割数

"""
class Solution1:
    def splitArray(self, nums, m):
        M = len(nums)
        if (m == M):
            return max(nums)
        new_nums = nums
        while(M > m):
            new_nums = self.generateNewNums(new_nums)
            M = M - 1
        return max(new_nums)


    def generateNewNums(self, nums):
        M = len(nums)
        if (M == 1):
            return nums
        tempMin = nums[0] + nums[1]
        tempMinIndex = 0
        isleft = False
        for i in range(1, M):
            left = i - 1
            right = i + 1
            if (left >= 0):
                leftSum = nums[left] + nums[i]
                if (leftSum < tempMin):
                    tempMin = leftSum
                    tempMinIndex = i
                    isleft = True
            else:
                continue
            if (right <= M - 1):
                rightSum = nums[right] + nums[i]
                if (rightSum < tempMin):
                    tempMin = rightSum
                    tempMinIndex = i
                    isleft = False
        new_nums = []
        i = 0
        while(i < M):
            if(i == tempMinIndex):
                if (isleft == True):
                    new_nums[-1] = new_nums[-1] + nums[i]

                else:
                    new_nums.append(nums[i] + nums[i + 1])
                    i = i + 1
            else:
                new_nums.append(nums[i])
            i = i + 1
        return new_nums
nums = [7,2,5,10,8]
s1 = Solution1()
result = s1.splitArray(nums, 3)
print(result)
