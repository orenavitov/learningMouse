import torch
import torch.nn as nn
import numpy
from torch.nn import Parameter
from NN import LineNetwork
import torch.utils.data as Data

def getDataLoader(A, radio):
    data = []
    label = []
    N = len(A)
    for i in range(N):
        for j in range(N):
            if i != j:
                data.append([i, j])
                label.append(A[i][j])
    train_data = torch.tensor(data[: int(N * (N - 1)  * radio)])
    train_label = torch.tensor(label[: int(N * (N - 1) * radio)])

    test_data = torch.tensor(data[int(N * (N - 1) * radio):])
    test_label = torch.tensor(label[int(N * (N - 1) * radio):])
    train_dataset = Data.TensorDataset(train_data, train_label)
    test_dataset = Data.TensorDataset(test_data, test_label)

    train_loader = Data.DataLoader(
        dataset=train_dataset,
        batch_size=16,
        shuffle=True,
    )

    test_loader = Data.DataLoader(
        dataset=test_dataset,
        batch_size=16 ,
        shuffle=True,
    )
    return train_loader, test_loader


class GNN(nn.Module):

    def __init__(self, G, A, neighbors, embedding_size):
        super(GNN, self).__init__()
        self.G = G
        self.A = torch.tensor(A, dtype=torch.float64)
        self.neighbors = neighbors
        self.embedding_size = embedding_size
        self.N = self.A.shape[0]
        self.node_embeddings = self.node_init_embeddings(self.N, self.embedding_size)
        self.node_embeddings = Parameter(self.node_embeddings)
        self.line = LineNetwork(self.embedding_size * 2, self.embedding_size * 4 + 1, 2)

    def node_init_embeddings(self, N, d):
        # [N, d]
        embeddings = numpy.random.normal(size=[N, d])
        embeddings = torch.tensor(embeddings)
        return embeddings


    # input: [Batch_size, 2]
    def forward(self, input):
        self.aggretion_matrix = torch.tensor(numpy.eye(self.N)) + self.A
        input = input.reshape([-1, ])
        node_neighbor_indexes = torch.index_select(self.aggretion_matrix, index = input, dim = 0).reshape([-1, 2, self.N])  # [batch_size, 2, N]
        node_aggretion = torch.matmul(node_neighbor_indexes, self.node_embeddings) # [batch_size, 2, d]
        output = node_aggretion.view([-1, self.embedding_size * 2]).float()
        output = self.line(output)
        return output