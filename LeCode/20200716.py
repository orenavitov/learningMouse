
import numpy
N = 5
matrix = numpy.random.randint(low = 0, high = 100, size = [N, N])

print(matrix)
visited = []
result = []

def solution(A):
    stack = []
    first_line = matrix[0]
    for i, element in enumerate(first_line):
        stack.append((0, i))
    go(stack)

def go(stack):
    while(len(stack) != 0):
        start = stack.pop()
        if (start in visited):
            continue
        visited.append(start)
        i,j = start
        if (i == N - 1):
            values = [matrix[m][n] for m, n in visited]
            print(values)
            result.append(sum(values))
            visited.remove(start)

        else:
            left = (i, j - 1)
            down = (i + 1, j)
            if(check(left) and (left not in stack)):
                stack.append(left)
            if (check(down) and (down not in stack)):
                stack.append(down)

def check(index):
    i, j = index
    if (i >= 0 and i <= N - 1 and j >= 0 and j <= N - 1):
        return True
    else:
        return False
solution(matrix)
