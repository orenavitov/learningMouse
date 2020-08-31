from matplotlib.pyplot import MultipleLocator
import matplotlib.pyplot as plt
import numpy

"""
颜色：

"""

plt.figure(figsize=(25, 16), dpi=100)

def drawBar(X_names, width, Y_values, Y_range, X_start = 10,  X_scale = 1, Y_scale = 1, labels = None, colors = None):
    X_indexes = numpy.arange(start = X_start, stop = int(X_start + len(X_names) * width), step = width)
    X_type_size = len(Y_values[0])
    # X_scale = width / X_type_size
    if X_type_size % 2 == 1:
        bias = -int(X_type_size / 2) * X_scale
    if X_type_size % 2 == 0:
        bias = -int((X_type_size - 1) / 2) * X_scale - (X_scale / 2)

    for i in range(X_type_size):
        X = X_indexes + bias + X_scale * i
        height = [Y[i] for Y in Y_values]
        plt.bar(x = X, height = height, width = X_scale, fc = colors[i], label = labels[i])

    Y_start = Y_range[0]
    Y_end = Y_range[1]
    # 设置Y轴的刻度范围【Y_start， Y_end】
    plt.ylim(Y_start, Y_end)
    # 设置Y轴的最小刻度1
    y_major_locator = MultipleLocator(Y_scale)
    ax = plt.gca()
    ax.yaxis.set_major_locator(y_major_locator)
    plt.legend()
    plt.xticks(X_indexes, X_names)
    plt.show()

# X_names = ["A", "B", "C", "D", "E"]
# width = 5
# Y_values = [
#     [3, 1, 3, 7],
#     [3, 4, 1, 9],
#     [5, 1, 2, 5],
#     [6, 2, 4, 7],
#     [4, 1, 3, 6],
# ]
# Y_range = [0, 10]
# X_start = 10
# X_scale = 1
# Y_scale = 1
# labels = ["r", "g", "b", "grey"]
# colors = ["r", "g", "b", "grey"]

X_names = ["USAir", "Bio-CE-GT", "hamster", "PB", "Yeast"]
width = 15
Y_values_heuristicAlgorithm = [
    [0.52, 0.860, 0.579, 0.662, 0.710, 0.710, 0.712],
    [0.518, 0.770, 0.453, 0.624, 0.722, 0.727, 0.730],
    [0.503, 0.762, 0.421, 0.592, 0.714, 0.719, 0.725],
    [0.500, 0.801, 0.582, 0.664, 0.700, 0.700, 0.710],
    [0.513, 0.809, 0.529, 0.649, 0.729, 0.734, 0.737]
]
#["DeepWalk", "Node2Vec", "EmbeddingWithAttention", "GCN", "Variant1", "Variant1_", "Variant2", "Variant2_", "SEAL"]
#"Variant1": MihGNNEmbedding8
#"Variant1_":MihGNNEmbedding10
#"Variant2":MihGNNEmbedding13
#"Variant2_":MihGNNEmbedding12
Y_AP_valus_embeddngs = [
    [0.791, 0.810, 0.904, 0.904, 0.829, 0.899, 0.899, 0.913, 0.899],
    [0.869, 0.873, 0.861, 0.880, 0.835, 0.910, 0.898, 0.906, 0.922],
    [0.712, 0.939, 0.850, 0.864, 0.813, 0.909, 0.877, 0.904, 0.870],
    [0.877, 0.881, 0.859, 0.875, 0.817, 0.872, 0.854, 0.879, 0.875],
    [0.978, 0.984, 0.861, 0.906, 0.844, 0.913, 0.902, 0.931, 0.929]
]
Y_AC_valus_embeddngs = [
    [0.826, 0.817, 0.917, 0.894, 0.752, 0.929, 0.905, 0.944, 0.862],
    [0.894, 0.935, 0.863, 0.887, 0.769, 0.918, 0.951, 0.917, 0.927],
    [0.721, 0.927, 0.879, 0.858, 0.756, 0.918, 0.908, 0.923, 0.823],
    [0.877, 0.877, 0.854, 0.862, 0.765, 0.868, 0.855, 0.865, 0.876],
    [0.972, 0.982, 0.906, 0.920, 0.797, 0.942, 0.988, 0.952, 0.958]
]
Y_AUC_valus_embeddngs = [
    [0.791, 0.810, 0.904, 0.904, 0.925, 0.899, 0.951, 0.913, 0.954],
    [0.869, 0.873, 0.861, 0.880, 0.936, 0.910, 0.974, 0.906, 0.973],
    [0.712, 0.939, 0.850, 0.864, 0.916, 0.909, 0.950, 0.904, 0.937],
    [0.877, 0.881, 0.859, 0.875, 0.909, 0.872, 0.933, 0.879, 0.943],
    [0.978, 0.984, 0.861, 0.906, 0.948, 0.913, 0.982, 0.931, 0.967]
]
Y_range = [0.4, 1.2]
X_start = 20
X_scale = 1
Y_scale = 0.1
labels_heuristic = ["AA", "ACT", "CN", "Jaccard", "Katz", "RW", "RWR"]
labels_embedding = ["DeepWalk", "Node2Vec", "EmbeddingWithAttention", "GCN", "Variant1", "Variant1_", "Variant2", "Variant2_", "SEAL"]
colors_heuristic = ["r", "g", "b", "k", "c", "m", "purple"]
colors_embedding = ["r", "g", "b", "k", "c", "m", "purple", "pink", "orange"]
if __name__ == '__main__':
    drawBar(X_names = X_names, width = width, Y_values = Y_AUC_valus_embeddngs,
            Y_range = Y_range, X_start = X_start,  X_scale = X_scale,
            Y_scale = Y_scale, labels = labels_embedding, colors = colors_embedding)