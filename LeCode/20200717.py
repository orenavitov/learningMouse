
class Solution1:
    def searchInsert(self, nums, target):
        if target in nums:
            return nums.index(target)
        else:
            return self.insert(nums, target, 0, len(nums))
    def insert(self, nums, target, start, end):
        if (start >= end):
            return start
        middle = start + int((end - start) / 2)

        if target < nums[middle]:
            temp_start = start
            temp_end = middle
            return self.insert(nums, target, temp_start, temp_end)
        else:
            temp_start = middle + 1
            temp_end = end
            return self.insert(nums, target, temp_start, temp_end)

# nums = [1,3,5,6, 20, 30, 40]
# target = 25
# s = Solution1()
# index =s.searchInsert(nums, target)
# print(index)

# class path:
#     def __init__(self, start, end, length):
#         self.start = start
#         self.end = end
#         self.length = length

class Solution2:

    def __init__(self, N):
        self.stack = []
        self.N = N
        self.paths = [0] * N

    def minJump(self, jump):

        # current_station = 0
        # jump_length = jump[current_station]
        # while(current_station < self.N):
        #     next_station = current_station + jump_length
        #     if (next_station >= N):
        #         return self.paths[current_station] + 1
        #     if (self.paths[next_station] == 0 or self.paths[next_station] <= self.paths[current_station] + 1):
        #         self.paths[next_station] = self.paths[current_station] + 1
        #     if (jump_length > 1):
        #         for temp_station in range(current_station + 1, next_station):
        #             if self.paths[temp_station] == 0 or self.paths[temp_station] < 1 + self.paths[next_station]:
        #                 self.paths[temp_station] = 1 + self.paths[next_station]
        #
        #     for temp_station in range(current_station + 1, next_station):
        #         temp_jump_length = jump[temp_station]
        #         temp_next_station = temp_station + temp_jump_length
        #         if (temp_next_station >= N):
        #             return self.paths[temp_station] + 1
        #         if self.paths[temp_station + temp_jump_length] == 0 or self.paths[temp_station + temp_jump_length] < self.paths[temp_station] + 1:
        #             self.paths[temp_station + temp_jump_length] = self.paths[temp_station] + 1
        #     current_station = next_station
        #     jump_length = jump[current_station]

        length = len(jump)
        i = length - 1
        while(i >= 0):
            dis = jump[i]
            if (i + jump[i] >= length):
                jump[i]  = 1
            else:
                jump[i] = jump[i + jump[i]] + 1
            j = i + 1
            while(j < length and j < i + dis and jump[j] > jump[i]):
                jump[j] = jump[i] + 1
                j = j + 1
            i = i - 1
        return jump[0];




jump = [2,5,1,1,1,1]
N = len(jump)
s2 = Solution2(N)
result = s2.minJump(jump)
print(result)