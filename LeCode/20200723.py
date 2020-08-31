"""
给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

说明：每次只能向下或者向右移动一步。

示例:

输入:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 7
解释: 因为路径 1→3→1→1→1 的总和最小。

链接：https://leetcode-cn.com/problems/minimum-path-sum
"""
class Solution1:
    def minPathSum(self, grid):

        self.row_count = len(grid)
        if (self.row_count == 0):
            return None
        self.col_count = len(grid[0])
        self.grid = grid
        self.result = []
        # start_stations = [(0, i) for i in range(self.col_count)]

        # for station in start_stations:
        #     self.go(station, 0)
        self.go((0, 0), 0)
        return min(self.result)

    def go(self, station, temp_result):
        row = station[0]
        col = station[1]

        next_result = temp_result + self.grid[row][col]

        if (row == self.row_count - 1 and col == self.col_count - 1):
            self.result.append(next_result)

        else:
            if (col == self.col_count - 1 and row < self.row_count - 1):
                self.go((row + 1, col), next_result)
            if (col < self.col_count - 1 and row == self.row_count - 1):
                self.go((row, col + 1), next_result)
            if (col < self.col_count - 1 and row < self.row_count - 1):
                # 向右
                self.go((row, col + 1), next_result)
                # 向下
                self.go((row + 1, col), next_result)

# grid = [[7,1,3,5,8,9,9,2,1,9,0,8,3,1,6,6,9,5],[9,5,9,4,0,4,8,8,9,5,7,3,6,6,6,9,1,6],[8,2,9,1,3,1,9,7,2,5,3,1,2,4,8,2,8,8],[6,7,9,8,4,8,3,0,4,0,9,6,6,0,0,5,1,4],[7,1,3,1,8,8,3,1,2,1,5,0,2,1,9,1,1,4],[9,5,4,3,5,6,1,3,6,4,9,7,0,8,0,3,9,9],[1,4,2,5,8,7,7,0,0,7,1,2,1,2,7,7,7,4],[3,9,7,9,5,8,9,5,6,9,8,8,0,1,4,2,8,2],[1,5,2,2,2,5,6,3,9,3,1,7,9,6,8,6,8,3],[5,7,8,3,8,8,3,9,9,8,1,9,2,5,4,7,7,7],[2,3,2,4,8,5,1,7,2,9,5,2,4,2,9,2,8,7],[0,1,6,1,1,0,0,6,5,4,3,4,3,7,9,6,1,9]]
grid = [
    [1,3,1],
    [1,5,1],
    [4,2,1]
]
class Other_Solution1:
    def minPathSum(self, grid):
        self.row_count = len(grid)
        if (self.row_count == 0):
            return None
        self.col_count = len(grid[0])
        # max_number = sum(grid)
        result = []
        for i in range(self.row_count):
            row_result = []
            for j in range(self.col_count):
                row_result.append(1000000)
            result.append(row_result)
        for i in range(self.row_count):
            for j in range(self.col_count):

                if (self.check(i - 1, j)):
                    result[i][j] = min(result[i][j], result[i - 1][j] + grid[i][j])
                if (self.check(i, j - 1)):
                    result[i][j] = min(result[i][j], result[i][j - 1] + grid[i][j])
                if ((not self.check(i - 1, j)) and (not self.check(i, j - 1))):
                    result[i][j] = grid[i][j]
        return result[self.row_count - 1][self.col_count - 1]

    def check(self, row, col):
        if (row >=0 and row <= self.row_count - 1 and col >= 0 and col <= self.col_count - 1):
            return True
        else:
            return False


s1 = Other_Solution1()
result = s1.minPathSum(grid)
print(result)
# print(min(result))