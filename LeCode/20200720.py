"""
给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。

找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。

示例:

X X X X
X O O X
X X O X
X O X X
运行你的函数后，矩阵变为：

X X X X
X X X X
X X X X
X O X X
解释:

被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的

链接：https://leetcode-cn.com/problems/surrounded-regions
解答：
可以从边缘的"O"开始， 与之相邻（上下左右， 不要有斜方向的）的"O"肯定不会被包围， 反之其他的"O"一定会被包围
"""
class Solution1:
    def solve(self, board):
        """
        Do not return anything, modify board in-place instead.
        """
        self.row_count = len(board)
        if (self.row_count == 0):
            return board
        self.col_count = len(board[0])
        left_board = [(i, 0) for i in range(self.row_count)]
        right_board = [(i, self.col_count - 1) for i in range(self.row_count)]
        top_board = [(0, i) for i in range(self.col_count)]
        down_board = [(self.row_count - 1, i) for i in range(self.col_count)]
        edge = []
        edge.extend(left_board)
        edge.extend(right_board)
        edge.extend(top_board)
        edge.extend(down_board)

        for station in edge:
            if board[station[0]][station[1]] == "O":
                self.dfs(station, board)

        for i in range(self.row_count):
            for j in range(self.col_count):
                if (board[i][j] == "*"):
                    board[i][j] = "O"
                    continue
                if (board[i][j] == "O"):
                    board[i][j] = "X"

    def dfs(self, station, board):
        row = station[0]
        col = station[1]
        if (row < 0 or row >= self.row_count):
            return
        if (col < 0 or col >= self.col_count):
            return
        value = board[row][col]
        if (value == "X" or value == "*"):
            return
        if (value == "O"):
            board[row][col] = "*"
        self.dfs((row - 1, col), board)
        self.dfs((row + 1, col), board)
        self.dfs((row, col - 1), board)
        self.dfs((row, col + 1), board)


s1 = Solution1()
# board = [["X", "X", "X", "X"],
#          ["X", "O", "O", "X"],
#          ["X", "X", "O", "X"],
#          ["X", "O", "X", "X"]
#          ]
# board = [
#     ["O", "O"],
#     ["O", "O"]
# ]
board = [["O", "O", "O"],
         ["O", "O", "O"],
         ["O", "O", "O"],
         ]

s1.solve(board)
print(board)