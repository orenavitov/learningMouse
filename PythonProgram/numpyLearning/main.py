import numpy

input = numpy.random.random(size=[30, 2])
if __name__ == "__main__":
    graph = numpy.mat([
        [1, 1, 0, 0],
        [1, 1, 0, 1],
        [0, 1, 0, 1],
        [0, 1, 0, 1]
    ])
    # 求矩阵的n次幂
    def matrix_power(matrix, n):
        shape = matrix.shape
        result = numpy.eye(N = shape[0], k = 0)
        for i in range(n):
            result = numpy.matmul(result, matrix)
        return result
    result = numpy.zeros(shape=(4, 4))
    for i in range(4):
        result += matrix_power(graph, i + 1)
    print(result)


