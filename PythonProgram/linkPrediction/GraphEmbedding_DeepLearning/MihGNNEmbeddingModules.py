import torch
from torch import nn
import numpy
from torch.nn import Parameter
from Tools import Matrix_pre_handle
import math
from Tools import get_steps_neighbor
from GraphEmbedding_DeepLearning.NN import LineNetwork
from GraphEmbedding_DeepLearning.AggregationModule import GNNAggregationModule
from GraphEmbedding_DeepLearning.AggregationModule import GNNAggregationForNegativeModule
from GraphEmbedding_DeepLearning.AggregationModule import GNNAggregationWithAttention
from GraphEmbedding_DeepLearning.LossModule import MihOutputModule1
from GraphEmbedding_DeepLearning.LossModule import MihOutputModule2
"""
GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
改进负样本的嵌入状态更新
"""
class MihGNNEmbedding1(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding1, self).__init__()
        self.d = d
        self.e = torch.tensor(math.e, dtype=torch.float)
        A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        embedding_state = numpy.random.randn(N, d)
        embedding_state = torch.tensor(data=embedding_state, dtype=torch.float)
        self.aggregationModule = GNNAggregationForNegativeModule(A=A, convolution_layers=layers, d=d,
                                                                 embedding_states=embedding_state)
        self.lossFunction = MihOutputModule1(d = self.d, e = self.e)


    def forward(self, *input):
        pairs = input[0]
        labels = input[1]

        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        ordered_labels, src_node_embeddings = self.aggregationModule(labels, src_node_indexes)
        ordered_labels, dst_node_embeddings = self.aggregationModule(labels, dst_node_indexes)
        loss = self.lossFunction(ordered_labels, src_node_embeddings, dst_node_embeddings)
        return loss

    def test(self, edges, preference="negative"):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]
        if (preference == "positive"):
            preference_labels = torch.ones_like(src_node_indexes, dtype=torch.long)
        if (preference == "negative"):
            preference_labels = torch.zeros_like(src_node_indexes, dtype=torch.long)
        _, src_node_embeddings = self.aggregationModule(preference_labels, src_node_indexes)
        _, dst_node_embeddings = self.aggregationModule(preference_labels, dst_node_indexes)
        differcences_sum = (src_node_embeddings - dst_node_embeddings) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        return predicts


"""
GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
"""
class MihGNNEmbedding2(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding2, self).__init__()
        self.d = d
        self.e = torch.tensor(math.e, dtype=torch.float)
        A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        embedding_state = numpy.random.randn(N, d)
        embedding_state = torch.tensor(data=embedding_state, dtype=torch.float)
        self.aggregationModule = GNNAggregationModule(A=A, convolution_layers=layers, d=d,
                                                                 embedding_states=embedding_state)
        self.lossFunction = MihOutputModule1(d = self.d, e = self.e)

    def forward(self, *input):
        pairs = input[0]
        labels = input[1]

        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        loss = self.lossFunction(labels, src_node_embeddings, dst_node_embeddings)
        return loss

    def test(self, edges):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]

        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        differcences_sum = (src_node_embeddings - dst_node_embeddings) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        return predicts

# GNN 使用余弦相似度作为目标函数
class MihGNNEmbedding3(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding3, self).__init__()
        A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        embedding_state = numpy.random.randn(N, d)
        embedding_state = torch.tensor(data=embedding_state, dtype=torch.float)
        self.aggregationModule = GNNAggregationModule(A=A, convolution_layers=layers, d=d,
                                                      embedding_states=embedding_state)
        self.lossFunction = MihOutputModule2()
        self.relu = nn.ReLU()

    def forward(self, *input):
        pairs = input[0]
        labels = input[1]
        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        loss = self.lossFunction(labels, src_node_embeddings, dst_node_embeddings)
        return loss

    def test(self, edges):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]
        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        predicts = torch.sum(src_node_embeddings * dst_node_embeddings, dim=0, keepdim=False) / (
                torch.norm(src_node_embeddings, p=2) * torch.norm(dst_node_embeddings, p=2))
        return predicts

# 引入注意力机制节点j对节点i的注意力为f(h_i, h_j), h_i, h_j为嵌入状态， f可以是一个全连接层， 输入维度是2 * D， 输出维度是1
class MihGNNEmbedding4(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding4, self).__init__()
        A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        embedding_state = numpy.random.randn(N, d)
        embedding_state = torch.tensor(data=embedding_state, dtype=torch.float)
        self.aggregationModule = GNNAggregationWithAttention(A=A, convolution_layers=layers, d=d,
                                                      embedding_states=embedding_state)
        self.liner = LineNetwork(input_features=d * 2, output_features=2, hidden_features=d)
        self.cross_entropy = nn.CrossEntropyLoss()
        self.add_module(name="AggregationModule", module=self.aggregationModule)
        self.add_module(name="FullConnectionLiner", module=self.liner)
    def forward(self, *input):
        pairs = input[0]
        labels = input[1]
        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        liner_input = torch.cat([src_node_embeddings, dst_node_embeddings], dim=1)
        output = self.liner(liner_input)
        loss = self.cross_entropy(output, labels)
        return loss

    def test(self, edges):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]
        src_node_embeddings = self.aggregationModule(src_node_indexes)
        dst_node_embeddings = self.aggregationModule(dst_node_indexes)
        liner_input = torch.cat([src_node_embeddings, dst_node_embeddings], dim=1)
        predictions = self.liner(liner_input)
        return predictions

# 使用cross_entry损失函数， 前提保证训练数据的正负样本数量相同
class MihGNNEmbedding5(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding5, self).__init__()
        A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        embedding_state = numpy.ones(shape = [N, d])
        embedding_state = torch.tensor(data = embedding_state, dtype=torch.float)
        self.aggregationModule = GNNAggregationForNegativeModule(A = A, convolution_layers = layers, d = d, embedding_states = embedding_state)
        self.liner = LineNetwork(input_features = d * layers * 2, output_features = 2, hidden_features = d)
        self.cross_entropy = nn.CrossEntropyLoss()
        self.add_module(name = "AggregationModule", module = self.aggregationModule)
        self.add_module(name = "FullConnectionLiner", module = self.liner)


    def forward(self, *input):
        pairs = input[0]
        labels = input[1]
        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        ordered_labels, src_node_embeddings = self.aggregationModule(labels, src_node_indexes)
        ordered_labels, dst_node_embeddings = self.aggregationModule(labels, dst_node_indexes)
        liner_input = torch.cat([src_node_embeddings, dst_node_embeddings], dim = 1)
        output = self.liner(liner_input)
        loss = self.cross_entropy(output, labels)
        return loss


    def test(self, edges, preference = "negative"):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]
        if (preference == "positive"):
            preference_labels = torch.ones_like(src_node_indexes, dtype = torch.long)
        if (preference == "negative"):
            preference_labels = torch.zeros_like(src_node_indexes, dtype = torch.long)
        _, src_node_embeddings = self.aggregationModule(preference_labels, src_node_indexes)
        _, dst_node_embeddings = self.aggregationModule(preference_labels, dst_node_indexes)
        liner_input = torch.cat([src_node_embeddings, dst_node_embeddings], dim = 1)
        predictions = self.liner(liner_input)
        return predictions
