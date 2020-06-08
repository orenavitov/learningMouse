import torch
from torch import nn
import numpy
from torch.nn import Parameter
from Tools import Matrix_pre_handle
import math
from Tools import get_steps_neighbor
import GraphEmbedding_DeepLearning.BaseModules as base_modules
from GraphEmbedding_DeepLearning.NN import LineNetwork


"""
GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
改进负样本的嵌入状态更新
"""
class MihGNNEmbedding1(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding1, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.layers = layers
        self.steps = steps
        self.GPU = GPU
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)

        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
            # self.layer_lines.add_module(name='drop_out{0}'.format(layer + 1), module = nn.Dropout(0.5))
        self.relu = nn.ReLU()
        self.e = torch.tensor(math.e)
        if GPU:
            self.A_s = self.A_s.cuda()
            self.delay = self.delay.cuda()
            self.embedding_state = self.embedding_state.cuda()
            self.layer_lines = self.layer_lines.cuda()
            self.relu = self.relu.cuda()
            self.e = torch.tensor(math.e).cuda()
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)

    def forward(self, *input):
        # TODO
        edges = input[0]
        edges = edges.permute([1, 0])

        src = edges[0]
        dst = edges[1]
        labels = input[1]
        labels_scalar = labels.numpy()


        neighbors_src = self.A_s.index_select(dim = 0, index = src)
        neighbors_dst = self.A_s.index_select(dim = 0, index = dst)

        embedding_states_src_list = []
        embedding_states_dst_list = []

        for index, label in enumerate(labels_scalar):
            src_index = src[index]
            dst_index = dst[index]
            if label == 1:
                src_embedding_state = torch.matmul(neighbors_src[index], self.embedding_state)
                embedding_states_src_list.append(src_embedding_state)
                dst_embedding_state = torch.matmul(neighbors_dst[index], self.embedding_state)
                embedding_states_dst_list.append(dst_embedding_state)

            if label == 0:
                embedding_states_src_list.append(self.embedding_state[src_index])
                embedding_states_dst_list.append(self.embedding_state[dst_index])

        embedding_states_src_list = torch.stack(embedding_states_src_list, dim = 0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim = 0)

        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(embedding_states_src_list)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(embedding_states_dst_list)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss

    def test(self, edges):
        # TODO
        edges = edges.permute([1, 0])
        src = edges[0]
        dst = edges[1]
        src_neighbors = self.A_s.index_select(dim = 0, index = src)
        dst_neighbors = self.A_s.index_select(dim = 0, index = dst)
        src_embeddings = torch.matmul(src_neighbors, self.embedding_state)
        dst_embeddings = torch.matmul(dst_neighbors, self.embedding_state)
        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(src_embeddings)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(dst_embeddings)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        return predicts


"""
GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
"""
class MihGNNEmbedding2(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding2, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)

        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
        self.relu = nn.ReLU()
        self.e = torch.tensor(math.e)
        if (GPU):
            self.A_s = self.A_s.cuda()
            self.embedding_state = self.embedding_state.cuda()
            self.layer_lines = self.layer_lines.cuda()
            self.relu = self.relu.cuda()
            self.e = self.e.cuda()
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)

    def forward(self, *input):
        edges = input[0]
        edges = edges.permute([1, 0])
        src = edges[0]
        dst = edges[1]
        labels = input[1]
        neighbors_src = self.A_s.index_select(dim=0, index=src)
        neighbors_dst = self.A_s.index_select(dim=0, index=dst)
        embedding_states_src = torch.matmul(neighbors_src, self.embedding_state)
        embedding_states_dst = torch.matmul(neighbors_dst, self.embedding_state)
        embedding_states_src_list = []
        embedding_states_dst_list = []
        current_embedding_states_src = embedding_states_src
        current_embedding_states_dst = embedding_states_dst
        for line in self.layer_lines:
            current_embedding_states_src = line(current_embedding_states_src)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list.append(current_embedding_states_src)
            current_embedding_states_dst = line(current_embedding_states_dst)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list.append(current_embedding_states_dst)
        embedding_states_src_list = torch.stack(embedding_states_src_list, dim=0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss

    def test(self, edges):
        edges = edges.permute([1, 0])
        src = edges[0]
        dst = edges[1]
        src_neighbors = self.A_s.index_select(dim = 0, index = src)
        dst_neighbors = self.A_s.index_select(dim = 0, index = dst)
        src_embeddings = torch.matmul(src_neighbors, self.embedding_state)
        dst_embeddings = torch.matmul(dst_neighbors, self.embedding_state)
        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(src_embeddings)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(dst_embeddings)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = self.e ** (-differcences_sum)
        return predicts

# GNN 使用余弦相似度作为目标函数
class MihGNNEmbedding3(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding3, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)

        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)
        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))

        self.relu = nn.ReLU()

    def forward(self, edges):
        batch_size = len(edges)
        src = [edge[0] for edge in edges]  # [batch_size, ]
        dst = [edge[1] for edge in edges]  # [batch_size, ]
        src = torch.tensor(src, dtype=torch.long)
        dst = torch.tensor(dst, dtype=torch.long)
        neighbors_src = self.A_s.index_select(dim=0, index=src)
        neighbors_dst = self.A_s.index_select(dim=0, index=dst)
        embedding_states_src = torch.matmul(neighbors_src, self.embedding_state)
        embedding_states_src = self.layer_lines(embedding_states_src)
        # embedding_states_src = self.relu(embedding_states_src)
        embedding_states_dst = torch.matmul(neighbors_dst, self.embedding_state)
        embedding_states_dst = self.layer_lines(embedding_states_dst)
        # embedding_states_dst = self.relu(embedding_states_dst)
        # cal A_star
        cos_similary = embedding_states_src * embedding_states_dst
        cos_similary = torch.sum(cos_similary, dim=1).reshape([-1, 1])
        cos_similary = torch.norm(cos_similary, dim=1).reshape([-1, ])
        x_1 = torch.norm(embedding_states_src, dim=1)
        x_2 = torch.norm(embedding_states_dst, dim=1)
        cos_similary = cos_similary / (x_1 * x_2)
        similary = self.relu(cos_similary)
        return similary

# GNN 优化的目标函数是：labaels - math.e ** (-sum((x_i - x_j) ** 2))
# 改进负样本的嵌入状态更新
# 引入注意力机制节点j对节点i的注意力为f(h_i, h_j), h_i, h_j为嵌入状态， f可以是一个全连接层， 输入维度是2 * D， 输出维度是1
class MihGNNEmbedding4(nn.Module):
    def __init__(self, A, N, d, layers, steps, delays, GPU = False):
        super(MihGNNEmbedding4, self).__init__()
        self.N = N
        self.d = d
        self.layers = layers
        self.steps = steps
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)
        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
        self.relu = nn.ReLU()

        self.attention_layer = Attention(A, steps = 2, d = d, delays = delays, embeddings = self.embedding_state)
    def forward(self, *input):
        edges = input[0].numpy()
        src = [edge[0] for edge in edges]
        dst = [edge[1] for edge in edges]
        labels = input[1]
        labels_scalar = labels.numpy()

        embedding_states_src_list = []
        embedding_states_dst_list = []
        for index, label in enumerate(labels_scalar):
            src_index = src[index]
            dst_index = dst[index]
            if label == 1:
                embedding_states_src_list.append(self.attention_layer(src_index))
                embedding_states_dst_list.append(self.attention_layer(dst_index))

            if label == 0:
                embedding_states_src_list.append(self.embedding_state[src_index])
                embedding_states_dst_list.append(self.embedding_state[dst_index])

        embedding_states_src_list = torch.stack(embedding_states_src_list, dim = 0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim = 0)

        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(embedding_states_src_list)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(embedding_states_dst_list)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = torch.tensor(math.e) ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss

    def test(self, test_data):
        edges = test_data.numpy()
        src_embeddings = []
        dst_embeddings = []
        for edge in edges:
            src = edge[0]
            dst = edge[1]
            src_embeddings.append(self.attention_layer(src))
            dst_embeddings.append(self.attention_layer(dst))
        src_embeddings = torch.stack(src_embeddings, dim = 0)
        dst_embeddings = torch.stack(dst_embeddings, dim = 0)

        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(src_embeddings)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(dst_embeddings)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)

        differcences_sum = (embedding_states_src - embedding_states_dst) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / self.d
        predicts = torch.tensor(math.e) ** (-differcences_sum)
        return predicts

class Attention(nn.Module):
    def __init__(self, A, steps, d, delays, embeddings, GPU = False):
        super(Attention, self).__init__()
        self.steps = steps
        self.neighbors = get_steps_neighbor(A, steps)
        self.fc = nn.Linear(in_features = 2 * d, out_features = 1)
        self.leakyRelu = nn.LeakyReLU(negative_slope=0.2)
        self.soft_max = nn.Softmax(dim = 0)
        self.embeddings = embeddings
    # input: node
    # output: embedding
    def forward(self, node):
        nodes_embeding_step = []
        for step in range(self.steps):
            neighbors_step = self.neighbors[step]

            node_embedding = self.embeddings[node]
            node_neighbors = neighbors_step[node]
            node_neighbors_number = len(node_neighbors)
            if (node_neighbors_number > 0):
                node_neighbors = torch.tensor(node_neighbors, dtype = torch.long)
                node_neighbors_embeddings = torch.index_select(self.embeddings, index = node_neighbors, dim = 0)
                node_embedding_repeat = node_embedding.repeat([node_neighbors_number, 1])
                node_neighbors_embeddings_2 = torch.cat([node_neighbors_embeddings, node_embedding_repeat], dim = 1)
                attentions = self.fc(node_neighbors_embeddings_2).squeeze(-1)
                attentions = self.leakyRelu(attentions)
                attentions = self.soft_max(attentions)
                attentions = attentions.view(-1, 1)
                nodes_embeding_step.append(torch.sum(node_neighbors_embeddings * attentions + node_embedding, dim=0))
            else:
                nodes_embeding_step.append(node_embedding)
        nodes_embeding_step = torch.stack(nodes_embeding_step, dim = 0)
        return torch.sum(nodes_embeding_step, dim = 0)

class MihGNNEmbedding5(nn.Module):
    def __init__(self, A, N, d, layers, steps, delay, GPU = False):
        super(MihGNNEmbedding5, self).__init__()
        self.N = N
        self.d = d
        self.A_s = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.layers = layers
        self.steps = steps
        self.delay = torch.tensor(delay, dtype=torch.float)
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)

        self.layer_lines = nn.Sequential()
        for layer in range(self.layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer + 1),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))
        self.relu = nn.ReLU()
        self.output_layers = LineNetwork(feature_dim = self.d * 2, hidden_layer_dim = self.d, output_dim = 2)
        self.cross_entropy = nn.CrossEntropyLoss()
    def forward(self, *input):
        edges = input[0]
        edges = edges.permute([1, 0])
        src = edges[0]
        dst = edges[1]
        labels = input[1]
        neighbors_src = self.A_s.index_select(dim=0, index=src)
        neighbors_dst = self.A_s.index_select(dim=0, index=dst)
        embedding_states_src = torch.matmul(neighbors_src, self.embedding_state)
        embedding_states_dst = torch.matmul(neighbors_dst, self.embedding_state)
        embedding_states_src_list = []
        embedding_states_dst_list = []
        current_embedding_states_src = embedding_states_src
        current_embedding_states_dst = embedding_states_dst
        for line in self.layer_lines:
            current_embedding_states_src = line(current_embedding_states_src)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list.append(current_embedding_states_src)
            current_embedding_states_dst = line(current_embedding_states_dst)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list.append(current_embedding_states_dst)
        embedding_states_src_list = torch.stack(embedding_states_src_list, dim=0)
        embedding_states_dst_list = torch.stack(embedding_states_dst_list, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list, dim=0)
        output = torch.cat([embedding_states_src, embedding_states_dst], dim = 1)
        output = self.output_layers(output)
        loss = self.cross_entropy(output, labels)
        return loss
    def test(self, edges):
        # TODO
        edges = edges.permute([1, 0])
        src = edges[0]
        dst = edges[1]
        src_neighbors = self.A_s.index_select(dim = 0, index = src)
        dst_neighbors = self.A_s.index_select(dim = 0, index = dst)
        src_embeddings = torch.matmul(src_neighbors, self.embedding_state)
        dst_embeddings = torch.matmul(dst_neighbors, self.embedding_state)
        embedding_states_src_list_ = []
        embedding_states_dst_list_ = []
        for line in self.layer_lines:
            current_embedding_states_src = line(src_embeddings)
            current_embedding_states_src = self.relu(current_embedding_states_src)
            embedding_states_src_list_.append(current_embedding_states_src)
            current_embedding_states_dst = line(dst_embeddings)
            current_embedding_states_dst = self.relu(current_embedding_states_dst)
            embedding_states_dst_list_.append(current_embedding_states_dst)
        embedding_states_src_list_ = torch.stack(embedding_states_src_list_, dim=0)
        embedding_states_dst_list_ = torch.stack(embedding_states_dst_list_, dim=0)
        embedding_states_src = torch.sum(embedding_states_src_list_, dim=0)
        embedding_states_dst = torch.sum(embedding_states_dst_list_, dim=0)
        output = torch.cat([embedding_states_src, embedding_states_dst], dim = 1)
        predictions = self.output_layers(output)
        return predictions