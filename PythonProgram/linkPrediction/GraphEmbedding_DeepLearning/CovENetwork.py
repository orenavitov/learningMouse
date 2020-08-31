import torch
from torch import nn
from GraphEmbedding_DeepLearning.NN import LineNetwork
from Tools import Matrix_pre_handle
class CovE(nn.Module):
    def __init__(self,A, N, embeding_size, layers, steps, delay, width, height, kernel_size, out_channel, GPU = False):
        super(CovE, self).__init__()
        self.A = torch.tensor(Matrix_pre_handle(A, steps=steps, delay=delay), dtype=torch.float)
        self.width = width
        self.height = height
        self.transformLiners = nn.Sequential()
        self.embeddings = torch.randn(size = [N, embeding_size], dtype = torch.float)
        for layer in range(layers):
            self.transformLiners.add_module(name = "transformLiners{0}".format(layer), module = nn.Linear(in_features = embeding_size, out_features = embeding_size))

        self.cov2d = nn.Conv2d(in_channels = 1, out_channels = out_channel, kernel_size = kernel_size , stride = 1)
        self.pool = nn.AvgPool2d(kernel_size = kernel_size)
        self.relu = nn.ReLU()
        output_width = int((width - kernel_size + 1) / kernel_size)
        output_height = int((height - kernel_size + 1) / kernel_size)
        self.output_features = output_height * output_width * out_channel
        self.ouputLiners = LineNetwork(input_features = int(self.output_features * 2), hidden_features = embeding_size, output_features = 2)
        self.softMax = nn.Softmax(dim = -1)
        self.crossEntropy = nn.CrossEntropyLoss()

    def forward(self, *input):
        pairs = input[0]
        labels = input[1]

        node_indexes = pairs.permute([1, 0])
        src_node_indexes = node_indexes[0]
        dst_node_indexes = node_indexes[1]
        src_node_neighbor_indexes = self.A.index_select(dim = 0, index = src_node_indexes)
        dst_node_neighbor_indexes = self.A.index_select(dim = 0, index = dst_node_indexes)

        src_node_embedings = torch.matmul(src_node_neighbor_indexes, self.embeddings)
        dst_node_embedings = torch.matmul(dst_node_neighbor_indexes, self.embeddings)
        for transformLayer in self.transformLiners:
            src_node_embedings = transformLayer(src_node_embedings)
            src_node_embedings = self.relu(src_node_embedings)
            dst_node_embedings = transformLayer(dst_node_embedings)
            dst_node_embedings = self.relu(dst_node_embedings)
        src_node_embedings = src_node_embedings.reshape(shape = [-1, self.width, self.height])
        src_node_embedings = src_node_embedings.unsqueeze(dim = 1)
        dst_node_embedings = dst_node_embedings.reshape(shape = [-1, self.width, self.height])
        dst_node_embedings = dst_node_embedings.unsqueeze(dim = 1)

        src_node_embedings = self.cov2d(src_node_embedings) #[batch_size, output_channel, width - kernel_size + 1, height-kernel_size + 1]
        src_node_embedings = self.pool(src_node_embedings)
        src_node_embedings = src_node_embedings.reshape(shape=[-1, self.output_features])
        dst_node_embedings = self.cov2d(dst_node_embedings)  # [batch_size, output_channel, width - kernel_size + 1, height-kernel_size + 1]
        dst_node_embedings = self.pool(dst_node_embedings)
        dst_node_embedings = dst_node_embedings.reshape(shape=[-1, self.output_features])
        edge_embeddings = torch.cat([src_node_embedings, dst_node_embedings], dim = -1)

        edge_embeddings = self.ouputLiners(edge_embeddings)
        output = self.softMax(edge_embeddings)
        loss = self.crossEntropy(output, labels)
        return loss

    def test(self, edges):
        edges = edges.permute([1, 0])
        src_node_indexes = edges[0]
        dst_node_indexes = edges[1]

        src_node_neighbor_indexes = self.A.index_select(dim=0, index=src_node_indexes)
        dst_node_neighbor_indexes = self.A.index_select(dim=0, index=dst_node_indexes)

        src_node_embedings = torch.matmul(src_node_neighbor_indexes, self.embeddings)
        dst_node_embedings = torch.matmul(dst_node_neighbor_indexes, self.embeddings)
        for transformLayer in self.transformLiners:
            src_node_embedings = transformLayer(src_node_embedings)
            src_node_embedings = self.relu(src_node_embedings)
            dst_node_embedings = transformLayer(dst_node_embedings)
            dst_node_embedings = self.relu(dst_node_embedings)
        src_node_embedings = src_node_embedings.reshape(shape=[-1, self.width, self.height])
        src_node_embedings = src_node_embedings.unsqueeze(dim=1)
        dst_node_embedings = dst_node_embedings.reshape(shape=[-1, self.width, self.height])
        dst_node_embedings = dst_node_embedings.unsqueeze(dim=1)

        src_node_embedings = self.cov2d(
            src_node_embedings)  # [batch_size, output_channel, width - kernel_size + 1, height-kernel_size + 1]
        src_node_embedings = self.pool(src_node_embedings)
        src_node_embedings = src_node_embedings.reshape(shape = [-1, self.output_features])
        dst_node_embedings = self.cov2d(
            dst_node_embedings)  # [batch_size, output_channel, width - kernel_size + 1, height-kernel_size + 1]
        dst_node_embedings = self.pool(dst_node_embedings)
        dst_node_embedings = dst_node_embedings.reshape(shape = [-1, self.output_features])
        edge_embeddings = torch.cat([src_node_embedings, dst_node_embedings], dim=-1)

        edge_embeddings = self.ouputLiners(edge_embeddings)
        predictions = self.softMax(edge_embeddings)
        return predictions