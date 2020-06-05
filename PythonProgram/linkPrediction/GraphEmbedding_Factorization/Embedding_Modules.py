import torch
import numpy
from torch import nn
from torch.nn import Parameter

# GF
class MihGNNEmbeddingTest4(nn.Module):
    def __init__(self, N, d, lambda_1):
        super(MihGNNEmbeddingTest4, self).__init__()
        self.N = N
        self.d = d
        self.lambda_1 = lambda_1
        self.embedding_state = numpy.random.randn(N, d)
        self.embedding_state = torch.tensor(data=self.embedding_state, dtype=torch.float)
        self.embedding_state = Parameter(self.embedding_state, requires_grad=True)

    def forward(self, *input):
        edges = input[0]
        labels = input[1]
        src = [edge[0] for edge in edges]  # [batch_size, ]
        src = torch.tensor(src, dtype=torch.long)
        dst = [edge[1] for edge in edges]  # [batch_size, ]
        dst = torch.tensor(dst, dtype=torch.long)
        embedding_states_src = self.embedding_state.index_select(dim=0, index=src)  # [batch_size, d]
        embedding_states_dst = self.embedding_state.index_select(dim=0, index=dst)  # [batch_size, d]
        Y = torch.mul(embedding_states_src, embedding_states_dst)  # [batch_size, d]
        Y = torch.sum(Y, dim=1)
        L = 0.5 * torch.sum((labels - Y) ** 2) + (self.lambda_1 / 2) * torch.norm(embedding_states_src, dim=1)
        L = torch.sum(L, dim=0)
        return L, Y