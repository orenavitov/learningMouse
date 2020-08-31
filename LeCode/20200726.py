"""
给定一个整数矩阵，找出最长递增路径的长度。

对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。

示例 1:

输入: nums =
[
  [9,9,4],
  [6,6,8],
  [2,1,1]
]
输出: 4
解释: 最长递增路径为 [1, 2, 6, 9]。
示例 2:

输入: nums =
[
  [3,4,5],
  [3,2,6],
  [2,2,1]
]
输出: 4
解释: 最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。

链接：https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix
"""
class Solution1:
    def longestIncreasingPath(self, matrix):
        self.row_count = len(matrix)
        self.result = 0
        if (self.row_count == 0):
            return 0
        self.col_count = len(matrix[0])
        all_depanences = []
        unclear = []
        result = []
        for row in range(self.row_count):
            row_result = []
            row_depanences = []
            for col in range(self.col_count):
                row_result.append(None)
                unclear.append((row, col))
                depanences = []
                # top
                if (self.check_station(row - 1, col) and matrix[row - 1][col] < matrix[row][col]):
                    depanences.append((row - 1, col))
                # down
                if (self.check_station(row + 1, col) and matrix[row + 1][col] < matrix[row][col]):
                    depanences.append((row + 1, col))
                # left
                if (self.check_station(row, col - 1) and matrix[row][col - 1] < matrix[row][col]):
                    depanences.append((row, col - 1))
                # right
                if (self.check_station(row, col + 1) and matrix[row][col + 1] < matrix[row][col]):
                    depanences.append((row, col + 1))
                row_depanences.append(depanences)
            all_depanences.append(row_depanences)
            result.append(row_result)

        while(len(unclear) != 0):
            for row, row_depanences in enumerate(all_depanences):
                for col, depanences in enumerate(row_depanences):
                    if ((row, col) not in unclear):
                        continue
                    clear = True
                    for station in depanences:
                        if (result[station[0]][station[1]] == None):
                            clear = False
                    if (clear):
                        neighbors_value = []
                        for station in depanences:
                            neighbors_value.append(result[station[0]][station[1]] + 1)
                        if(len(neighbors_value) > 0):
                            result[row][col] = max(neighbors_value)
                        else:
                            result[row][col] = 1
                        unclear.remove((row, col))
        tempResult = 0
        for row_result in result:
            tempMax = max(row_result)
            if (tempMax > tempResult):
                tempResult = tempMax
        return tempResult
    def check_station(self, row, col):
        if (row >=0 and row <= self.row_count - 1 and col >= 0 and col <= self.col_count - 1):
            return True
        else:
            return False


class Solution2:
    def longestIncreasingPath(self, matrix):
        self.matrix = matrix
        self.row_count = len(matrix)
        self.result = 0
        if (self.row_count == 0):
            return 0
        self.col_count = len(matrix[0])
        self.maxValue = 0
        self.maxValuePathLength = 0
        for row in range(self.row_count):
            for col in range(self.col_count):
                self.go(row, col, 0)
        return self.maxValuePathLength

    def go(self, row, col, currentPathLength):
        value = self.matrix[row][col]
        currentPathLength = currentPathLength + 1
        if ((self.check_station(row - 1, col) and self.matrix[row - 1][col] > value) or
            (self.check_station(row + 1, col) and self.matrix[row + 1][col] > value) or
            (self.check_station(row, col - 1) and self.matrix[row][col - 1] > value) or
            (self.check_station(row, col + 1) and self.matrix[row][col + 1] > value)):

            if(self.check_station(row - 1, col) and self.matrix[row - 1][col] > value):
                self.go(row - 1, col, currentPathLength)
            if (self.check_station(row + 1, col) and self.matrix[row + 1][col] > value):
                self.go(row + 1, col, currentPathLength)
            if (self.check_station(row, col - 1) and self.matrix[row][col - 1] > value):
                self.go(row, col - 1, currentPathLength)
            if (self.check_station(row, col + 1) and self.matrix[row][col + 1] > value):
                self.go(row, col + 1, currentPathLength)
        else:
            if (currentPathLength > self.maxValuePathLength):
                self.maxValuePathLength = currentPathLength

    def check_station(self, row, col):
        if (row >=0 and row <= self.row_count - 1 and col >= 0 and col <= self.col_count - 1):
            return True
        else:
            return False
Matrix =[[7,7,5],[2,4,6],[8,2,0]]
s1 = Solution1()
result = s1.longestIncreasingPath(Matrix)
print(result)