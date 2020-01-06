'''
@Time: 2020/1/3 15:29
@Author: mih
@Des: 
'''
import torch
from torch import nn
from torchviz import make_dot
from torch.autograd import Variable
from GGNN import GGNN
from NN import LineNetwork
import numpy

def Test():
    model = nn.Sequential()
    model.add_module('W0', nn.Linear(8, 16))
    model.add_module('tanh', nn.Tanh())
    model.add_module('W1', nn.Linear(16, 1))

    x = Variable(torch.randn(1, 8))

    vis_graph = make_dot(model(x), params=dict(model.named_parameters()))
    vis_graph.view()


def show():
    state_dim = 5
    n_edge_type = 1
    n_node = 20
    n_steps = 5
    ggnn = GGNN(state_dim=state_dim, n_edge_type=n_edge_type, n_node=n_node, n_steps=n_steps)
    A = numpy.random.random(size=(n_node, n_node))
    states = torch.randn(n_node, state_dim)



    train_loader, _ = ggnn(states, A)

    data = train_loader.dataset
    inputs, labels = data.tensors
    ln = LineNetwork(state_dim * 2, state_dim, 2)
    # output = ln(inputs)
    vis_graph = make_dot(ln(inputs), params=dict(ln.named_parameters()))
    vis_graph.view()

if __name__ == '__main__':
    show()