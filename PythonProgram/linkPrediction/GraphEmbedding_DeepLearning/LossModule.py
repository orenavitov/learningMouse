import torch
from torch import nn
import math

class MihOutputModule1(nn.Module):
    def __init__(self, d, e, layers):
        super(MihOutputModule1, self).__init__()
        self.d = d
        self.e = e
        self.layers = layers
    # input[0] : labels
    # input[1] : src_node_embeddings
    # input[2] : dst_node_embeddings
    def forward(self, *input):
        labels = input[0]
        src_node_embeddings = input[1]
        dst_node_embeddings = input[2]
        differcences_sum = (src_node_embeddings - dst_node_embeddings) ** 2
        differcences_sum = torch.sum(differcences_sum, dim=1) / (self.d * self.layers)
        predicts = self.e ** (-differcences_sum)
        loss = 0.5 * (labels - predicts) ** 2
        loss = torch.sum(loss)
        return loss


class MihOutputModule2(nn.Module):
    def __init__(self):
        super(MihOutputModule2, self).__init__()

    def forward(self, *input):
        labels = input[0]
        src_node_embeddings = input[1]
        dst_node_embeddings = input[2]
        output = torch.sum(src_node_embeddings * dst_node_embeddings, dim=1, keepdim=True) / (
                    torch.norm(src_node_embeddings, p=2, dim = 1) * torch.norm(dst_node_embeddings, p=2, dim = 1))
        loss = torch.norm(labels - output, p = 2)
        return loss