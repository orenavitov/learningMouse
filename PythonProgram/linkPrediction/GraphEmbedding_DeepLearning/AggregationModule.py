import torch
from torch import nn

class GNNAggregationBaseModule(nn.Module):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(GNNAggregationBaseModule, self).__init__()
        self.A = A
        self.As = As
        self.all_nodes_neighbors = all_nodes_neighbors
        self.convolution_layers = convolution_layers
        self.embedding_states = embedding_states
        self.d = d
        self.layer_lines = nn.Sequential()
        for layer in range(self.convolution_layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))

    def forward(self, *input):
        raise NotImplementedError


    # def test(self, *input):
    #     raise NotImplementedError


# name: GNNAggregationModule
class GNNAggregationModule(GNNAggregationBaseModule):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(GNNAggregationModule, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.tanh = nn.Tanh()
        self.relu = nn.ReLU()

    def forward(self, *input):
        node_indexes = input[0]
        input_nodes_embeddings = []
        for node in node_indexes:
            sub_A = self.As[node]
            sub_A = torch.tensor(sub_A, dtype = torch.float)
            node_neighbors = self.all_nodes_neighbors[node]
            node_neighbors = torch.tensor(node_neighbors, dtype = torch.long)
            node_neighbors_embeddings = self.embedding_states.index_select(dim = 0, index = node_neighbors)
            current_aggregation_embedding_states = node_neighbors_embeddings
            for layer in range(self.convolution_layers):
                line = self.layer_lines[layer]
                current_aggregation_embedding_states = line(current_aggregation_embedding_states)
                current_aggregation_embedding_states = torch.matmul(sub_A, current_aggregation_embedding_states)
                current_aggregation_embedding_states = self.tanh(current_aggregation_embedding_states)

            node_embedding = torch.sum(current_aggregation_embedding_states, dim = 0)
            input_nodes_embeddings.append(node_embedding)
        return torch.stack(input_nodes_embeddings, dim = 0)

# name: GNNAggregationModule
class MihGNNAggregationModule(GNNAggregationBaseModule):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(MihGNNAggregationModule, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.tanh = nn.Tanh()
        self.relu = nn.ReLU()

    def forward(self, *input):
        node_indexes = input[0]
        nodes_neighbors = self.A.index_select(dim = 0, index = node_indexes)

        current_aggregation_embedding_states = torch.matmul(nodes_neighbors, self.embedding_states)
        for layer in range(self.convolution_layers):
            line = self.layer_lines[layer]
            current_aggregation_embedding_states = line(current_aggregation_embedding_states)
            current_aggregation_embedding_states = self.tanh(current_aggregation_embedding_states)

        return current_aggregation_embedding_states

# name: GNNAggregationModule
class MihGNNAggregationModule2(GNNAggregationBaseModule):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(MihGNNAggregationModule2, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.tanh = nn.Tanh()
        self.relu = nn.ReLU()

    def forward(self, *input):
        node_indexes = input[0]
        nodes_neighbors = self.A.index_select(dim = 0, index = node_indexes)

        current_aggregation_embedding_states = torch.matmul(nodes_neighbors, self.embedding_states)
        node_embeddings = []
        for layer in range(self.convolution_layers):
            line = self.layer_lines[layer]
            layer_node_embeddings = line(current_aggregation_embedding_states)
            layer_node_embeddings = self.tanh(layer_node_embeddings)
            node_embeddings.append(layer_node_embeddings)
        node_embeddings = torch.stack(node_embeddings, dim = 0)
        node_embeddings = torch.sum(node_embeddings, dim = 0)
        return node_embeddings

# name: GNNAggregationModule
class MihGNNAggregationModuleWithConv1d(GNNAggregationBaseModule):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(MihGNNAggregationModuleWithConv1d, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.d = d
        self.cov1d1 = nn.Conv1d(in_channels = 1, out_channels = 4, kernel_size = 2, stride = 2) # [batch_size, 4, d / 2]
        self.pool1d1 = nn.MaxPool1d(kernel_size = 2, stride = 2)                                # [batch_size, 4, d / 4]
        self.cov1d2 = nn.Conv1d(in_channels = 4, out_channels = 8, kernel_size = 2, stride = 2) # [batch_size, 8, d / 8]
        self.pool1d2 = nn.MaxPool1d(kernel_size=2, stride=2)                                    # [batch_size, 8, d / 16]
        self.tanh = nn.Tanh()
        self.relu = nn.ReLU()

    def forward(self, *input):
        node_indexes = input[0]
        nodes_neighbors = self.A.index_select(dim = 0, index = node_indexes)

        current_aggregation_embedding_states = torch.matmul(nodes_neighbors, self.embedding_states)
        for layer in range(self.convolution_layers):
            line = self.layer_lines[layer]
            current_aggregation_embedding_states = line(current_aggregation_embedding_states)
            current_aggregation_embedding_states = self.tanh(current_aggregation_embedding_states)
        current_aggregation_embedding_states = current_aggregation_embedding_states.unsqueeze(dim = 1)
        current_aggregation_embedding_states = self.cov1d1(current_aggregation_embedding_states)
        current_aggregation_embedding_states = self.pool1d1(current_aggregation_embedding_states)
        current_aggregation_embedding_states = self.cov1d2(current_aggregation_embedding_states)
        current_aggregation_embedding_states = self.pool1d2(current_aggregation_embedding_states)
        current_aggregation_embedding_states = current_aggregation_embedding_states.view([-1, int(self.d / 2)])
        return current_aggregation_embedding_states


class GNNAggregationForNegativeModule(GNNAggregationBaseModule):
    def __init__(self, A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(GNNAggregationForNegativeModule, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.A = self.A / torch.sum(self.A, dim = -1, keepdim = True)
        # self.A = self.A + torch.eye(N)
        self.relu = nn.ReLU()
    # input[0] : node_indexes
    # input[1] : labels
    def forward(self, *input):
        node_indexes = input[1]
        labels = input[0]

        positive_index = torch.where(labels == 1)[0]
        negative_index = torch.where(labels == 0)[0]

        labels = torch.cat([torch.ones_like(positive_index), torch.zeros_like(negative_index)], dim=0)

        positive_node_indexes = node_indexes.index_select(dim=0, index=positive_index)
        negative_node_indexes = node_indexes.index_select(dim=0, index=negative_index)

        positive_positive_node_indexes_neighbors = self.A.index_select(dim=0, index=positive_node_indexes)

        positive_node_indexes_embeddings = torch.matmul(positive_positive_node_indexes_neighbors, self.embedding_states)

        negative_node_indexes_embeddings = self.embedding_states.index_select(dim=0, index=negative_node_indexes)

        node_indexes_embeddings = torch.cat([positive_node_indexes_embeddings, negative_node_indexes_embeddings], dim=0)

        # node_indexes_embeddings_ = []
        current_node_indexes_embeddings = node_indexes_embeddings
        for line in self.layer_lines:

            current_node_indexes_embeddings = line(current_node_indexes_embeddings)
            current_node_indexes_embeddings = self.relu(current_node_indexes_embeddings)

            # node_indexes_embeddings_.append(current_node_indexes_embeddings)

        # node_indexes_embeddings_ = torch.stack(node_indexes_embeddings_, dim=0)
        # node_indexes_embeddings = torch.cat(node_indexes_embeddings_, dim=-1)
        return labels, current_node_indexes_embeddings

class GNNAggregationWithAttentionModule(GNNAggregationBaseModule):
    def __init__(self,A, As, all_nodes_neighbors, convolution_layers, d, embedding_states):
        super(GNNAggregationWithAttentionModule, self).__init__(A, As, all_nodes_neighbors, convolution_layers, d, embedding_states)
        self.fc = nn.Linear(in_features=2 * d, out_features=1)
        self.leakyRelu = nn.LeakyReLU(negative_slope=0.2)
        self.soft_max = nn.Softmax(dim=0)
        # self.neighbors_H = neighbors_H
        # self.H = H

    # input: node
    # output: embedding
    def forward(self, *input):

        node_indexes = input[0]
        input_nodes_result_embeddings = []
        neighbors_information = self.A.index_select(index = node_indexes, dim = 0)
        for i, each_node_neighbors_information in enumerate(neighbors_information):
            center_node_index = node_indexes[i]
            center_node_embedding = self.embedding_states.index_select(index = center_node_index, dim = 0).squeeze()
            neighbors_indexes = torch.where(each_node_neighbors_information > 0)[0]
            neighbors_size = neighbors_indexes.shape[0]
            if (neighbors_size == 0):
                input_nodes_result_embeddings.append(center_node_embedding)
            else:
                neighbors_embedding = self.embedding_states.index_select(index=neighbors_indexes, dim=0)
                repeated_center_node_embedding = center_node_embedding.unsqueeze(dim = 0).repeat([neighbors_size, 1])
                embedding = torch.cat([repeated_center_node_embedding, neighbors_embedding], dim = -1)
                attention_score = self.fc(embedding)
                attention_score = self.leakyRelu(attention_score)
                neighbors_embedding = attention_score * neighbors_embedding
                node_result_embeddings = torch.sum(neighbors_embedding, dim=0)

                node_result_embeddings = node_result_embeddings + center_node_embedding
                input_nodes_result_embeddings.append(node_result_embeddings)
        input_nodes_result_embeddings = torch.stack(input_nodes_result_embeddings, dim = 0)
        return input_nodes_result_embeddings

class GCNAggregationModule(nn.Module):
    def __init__(self, A, convolution_layers, d, embedding_states):
        super(GCNAggregationModule, self).__init__()

        self.A = self.pre_handle(A)

        N = self.A.shape[0]
        self.A = torch.tensor(self.A, dtype = torch.float)
        self.convolution_layers = convolution_layers
        self.d = d
        self.embedding_states = torch.randn(size = [N, d])
        self.relu = nn.ReLU()
        self.tanh = nn.Tanh()
        self.layer_lines = nn.Sequential()
        for layer in range(self.convolution_layers):
            self.layer_lines.add_module(name='layer_line{0}'.format(layer),
                                        module=nn.Linear(in_features=self.d, out_features=self.d))

    def pre_handle(self, A):
        D = torch.sum(A, dim = -1, keepdim = False)
        D = D ** (-1 / 2)
        D = torch.diag(D)

        L = torch.matmul(D, A)
        L = torch.matmul(L, D)
        return L
    def forward(self, *input):
        node_indexes = input[0]
        node_neighbors = self.A.index_select(dim = 0, index = node_indexes)
        aggregation_embedding_states = torch.matmul(node_neighbors, self.embedding_states)
        current_aggregation_embedding_states = aggregation_embedding_states
        for line in self.layer_lines:
            current_aggregation_embedding_states = line(current_aggregation_embedding_states)
            current_aggregation_embedding_states = self.relu(current_aggregation_embedding_states)

        return current_aggregation_embedding_states