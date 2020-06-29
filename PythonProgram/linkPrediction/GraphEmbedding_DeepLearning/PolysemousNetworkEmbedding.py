import torch
from torch import nn
import numpy
from torch.nn import Parameter

class PriorDistribution(nn.Module):
    def __init__(self, A, K, alpha, GPU = False):
        super(PriorDistribution, self).__init__()
        self.A = torch.tensor(A, dtype = torch.float)
        self.K = K
        self.alpha = alpha
        N = self.A.shape[0]
        self.prior_distribution_matrix = Parameter(data = torch.randn(size = [N, self.K], dtype = torch.float), requires_grad = True)
        if GPU:
            self.A = self.A.cuda()
            self.prior_distribution_matrix = self.prior_distribution_matrix.cuda()

    def forward(self):
        difference = self.A - torch.matmul(self.prior_distribution_matrix, self.prior_distribution_matrix.t())
        difference = torch.norm(difference, p = 2)
        regular = torch.norm(self.prior_distribution_matrix, p = 2)
        loss = difference + self.alpha * regular
        return loss

class PolysemousGcnLayer(nn.Module):
    def __init__(self, layers, K, embedding_size):
        super(PolysemousGcnLayer, self).__init__()
        self.layers = layers
        self.K = K
        self.weights = []
        for layer in range(self.layers):
            layer_weight = []
            for k in range(self.K):
                weight = torch.randn(size = [embedding_size, embedding_size], dtype = torch.float)
                layer_weight.append(weight)
            layer_weight = torch.stack(layer_weight, dim = 0)
            self.weights.append(layer_weight)
        self.weights = torch.stack(self.weights, dim = 0)
        self.weights = Parameter(self.weights, requires_grad = True)

        self.biases = []
        for layer in range(self.layers):
            layer_bias = []
            for k in range(self.K):
                bias = torch.randn(size=[embedding_size, ], dtype=torch.float)
                layer_bias.append(bias)
            layer_bias = torch.stack(layer_bias, dim = 0)
            self.biases.append(layer_bias)
        self.biases = torch.stack(self.biases, dim = 0)
        self.biases = Parameter(self.biases, requires_grad = True)
        self.relu = nn.ReLU()
    # input : [K, batch_size, d]
    def forward(self, input):
        output = []
        for layer in range(self.layers):
            layer_output = []
            layer_weight = self.weights[layer]
            layer_bias = self.biases[layer]
            for k in range(self.K):
                layer_k_output = torch.matmul(input[k], layer_weight[k]) # [batch_size, K, d]
                layer_k_output = layer_k_output + layer_bias[k] # [batch_size, K, d]
                layer_k_output = self.relu(layer_k_output)
                layer_output.append(layer_k_output)
            layer_output = torch.stack(layer_output, dim = 0) # [K, batch_size, d]
            input = layer_output
            layer_output = layer_output.permute([1, 0, 2]) #[batch_size, K, d]
            output.append(layer_output)
        output = torch.stack(output, dim = 0)  # [layer, batch_size, K, d]
        output = torch.mean(output, dim = 0)

        return output

class PolysemousNetwork(nn.Module):
    def __init__(self, A, embedding_size, layers, K, GPU = False):
        super(PolysemousNetwork, self).__init__()
        N = A.shape[0]
        self.A = torch.tensor(A, dtype = torch.float)
        self.embedding_size = embedding_size
        self.K = K
        self.layers = layers
        self.GPU = GPU
        self.prior_distribution_matrix = torch.randn([N, K])
        self.prior_distribution_matrix = self.prior_distribution_matrix / torch.sum(self.prior_distribution_matrix, dim = 1, keepdim = True)
        self.prior_distribution_matrix = Parameter(self.prior_distribution_matrix, requires_grad = True)
        self.embedding_state = torch.randn(size = [K, N, embedding_size], dtype = torch.float)
        self.GCN_layer = PolysemousGcnLayer(layers = self.layers, K = self.K, embedding_size = self.embedding_size)
        self.add_module(name = "GCN_layer", module = self.GCN_layer)
        self.relu = nn.ReLU()

    def forward(self, *input):
        pairs = input[0]
        labels = input[1]
        pairs = pairs.permute([1, 0])
        src_nodes = pairs[0] #[batch_size, ]
        dst_nodes = pairs[1] #[batch_size, ]
        src_nodes_prior_distribution = self.prior_distribution_matrix.index_select(dim = 0, index = src_nodes) #[batch_size, K]
        dst_nodes_prior_distribution = self.prior_distribution_matrix.index_select(dim = 0, index = dst_nodes) #[batch_size, K]
        src_nodes_prior_distribution = src_nodes_prior_distribution.unsqueeze(dim = -1) #[batch_size, K, 1]
        src_nodes_prior_distribution = src_nodes_prior_distribution.repeat([1, 1, self.K]).reshape([-1, self.K, self.K]) #[batch_size, K, K]
        dst_nodes_prior_distribution = dst_nodes_prior_distribution.repeat([1, self.K]).reshape([-1, self.K, self.K]) #[batch_size, K, K]
        src_nodes_neighbors = self.A.index_select(dim = 0, index = src_nodes)
        dst_nodes_neighbors = self.A.index_select(dim = 0, index = dst_nodes)

        src_nodes_embeddings = []
        dst_nodes_embeddings = []
        for k in range(self.K):
            src_nodes_k_embeddings = torch.matmul(src_nodes_neighbors, self.embedding_state[k])
            src_nodes_embeddings.append(src_nodes_k_embeddings) #[K, batch_size, d]
            dst_nodes_k_embeddings = torch.matmul(dst_nodes_neighbors, self.embedding_state[k])
            dst_nodes_embeddings.append(dst_nodes_k_embeddings) #[K, batch_size, d]
        src_nodes_embeddings = self.GCN_layer(src_nodes_embeddings)
        dst_nodes_embeddings = self.GCN_layer(dst_nodes_embeddings)
        src_nodes_embeddings = src_nodes_embeddings.unsqueeze(dim = 2)
        src_nodes_embeddings = src_nodes_embeddings.repeat([1, 1, self.K, 1]).reshape(-1, self.K, self.K, self.embedding_size) #[batch_size, K, K, d]
        dst_nodes_embeddings = dst_nodes_embeddings.repeat([1, self.K, 1]).reshape(-1, self.K, self.K, self.embedding_size) #[batch_size, K, K, d]
        similarity = torch.mul(src_nodes_embeddings, dst_nodes_embeddings) #[batch_size, K, K, d]
        similarity = torch.sum(similarity, dim = -1, keepdim = False) #[batch_size, K, K]
        prior_distribution = torch.mul(src_nodes_prior_distribution, dst_nodes_prior_distribution) #[batch_size, K, K]
        similarity = torch.mul(similarity, prior_distribution) #[batch_size, K, K]
        similarity = torch.sum(similarity, dim = -1, keepdim = False) #[batch_size, K]
        similarity = torch.sum(similarity, dim=-1, keepdim=False) #[batch_size, ]
        difference = labels - similarity
        loss = torch.norm(difference, p = 2)
        return loss

    def test(self, pairs):
        pairs = pairs.permute([1, 0])
        src_nodes = pairs[0]  # [batch_size, ]
        dst_nodes = pairs[1]  # [batch_size, ]
        src_nodes_prior_distribution = self.prior_distribution_matrix.index_select(dim=0,
                                                                                   index=src_nodes)  # [batch_size, K]
        dst_nodes_prior_distribution = self.prior_distribution_matrix.index_select(dim=0,
                                                                                   index=dst_nodes)  # [batch_size, K]
        src_nodes_prior_distribution = src_nodes_prior_distribution.unsqueeze(dim=-1)  # [batch_size, K, 1]
        src_nodes_prior_distribution = src_nodes_prior_distribution.repeat([1, 1, self.K]).reshape(
            [-1, self.K, self.K])  # [batch_size, K, K]
        dst_nodes_prior_distribution = dst_nodes_prior_distribution.repeat([1, self.K]).reshape(
            [-1, self.K, self.K])  # [batch_size, K, K]
        src_nodes_neighbors = self.A.index_select(dim=0, index=src_nodes)
        dst_nodes_neighbors = self.A.index_select(dim=0, index=dst_nodes)

        src_nodes_embeddings = []
        dst_nodes_embeddings = []
        for k in range(self.K):
            src_nodes_k_embeddings = torch.matmul(src_nodes_neighbors, self.embedding_state[k])
            src_nodes_embeddings.append(src_nodes_k_embeddings)
            dst_nodes_k_embeddings = torch.matmul(dst_nodes_neighbors, self.embedding_state[k])
            dst_nodes_embeddings.append(dst_nodes_k_embeddings)
        src_nodes_embeddings = self.GCN_layer(src_nodes_embeddings)  # [batch_size, K, d]
        dst_nodes_embeddings = self.GCN_layer(dst_nodes_embeddings)  # [batch_size, K, d]
        src_nodes_embeddings = src_nodes_embeddings.unsqueeze(dim=2)
        src_nodes_embeddings = src_nodes_embeddings.repeat([1, 1, self.K, 1]).reshape(-1, self.K, self.K,
                                                                                      self.embedding_size)  # [batch_size, K, K, d]
        dst_nodes_embeddings = dst_nodes_embeddings.repeat([1, self.K, 1]).reshape(-1, self.K, self.K,
                                                                                   self.embedding_size)  # [batch_size, K, K, d]
        similarity = torch.mul(src_nodes_embeddings, dst_nodes_embeddings)  # [batch_size, K, K, d]
        similarity = torch.sum(similarity, dim=-1, keepdim=False)  # [batch_size, K, K]
        prior_distribution = torch.mul(src_nodes_prior_distribution, dst_nodes_prior_distribution)  # [batch_size, K, K]
        similarity = torch.mul(similarity, prior_distribution)  # [batch_size, K, K]
        similarity = torch.sum(similarity, dim=-1, keepdim=False)  # [batch_size, K]
        similarity = torch.sum(similarity, dim=-1, keepdim=False)  # [batch_size, ]
        return similarity