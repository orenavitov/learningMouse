'''
@Time: 2019/12/31 15:51
@Author: mih
@Des: 原文中使用节点的Annotaion, 区别于节点的Label, 我暂时不用Annotation
原文使用有向图， 我暂时使用无向图
'''
import torch
import torch.nn as nn
import numpy
import torch.nn.functional as F
import torch.optim as optim
import torch.utils.data as Data
from NN import LineNetwork

class AttrProxy():
    def __init__(self, module, prefix):
        self.module = module
        self.prefix = prefix

    def __getitem__(self, i):
        return getattr(self.module, self.prefix + str(i))

class Propogator(nn.Module):
    """
    Gated Propogator for GGNN
    Using LSTM gating mechanism
    """
    def __init__(self, state_dim, n_node, n_edge_types):
        super(Propogator, self).__init__()

        self.n_node = n_node
        self.n_edge_types = n_edge_types

        self.reset_gate = nn.Sequential(
            nn.Linear(state_dim * 2, state_dim),
            nn.Sigmoid()
        )
        self.update_gate = nn.Sequential(
            nn.Linear(state_dim * 2, state_dim),
            nn.Sigmoid()
        )
        self.tansform = nn.Sequential(
            nn.Linear(state_dim * 3, state_dim),
            nn.Tanh()
        )

    def forward(self, next_states, state_cur, A):
        A = A.astype(numpy.float32)
        A_ = torch.from_numpy(A[:, :self.n_node*self.n_edge_types])
        # next_states = next_states.type_as(A_)
        # state_cur = state_cur.type_as(A_)
        a = torch.mm(A_, next_states)
        a = torch.cat((a, state_cur), 1)
        #
        r = self.reset_gate(a)
        z = self.update_gate(a)
        joined_input = torch.cat((a, r * state_cur), 1)
        h_hat = self.tansform(joined_input)

        output = (1 - z) * state_cur + z * h_hat

        return output

class GGNN(nn.Module):
    def __init__(self, state_dim, n_edge_type, n_node, n_steps):
        super(GGNN, self).__init__()

        self.state_dim = state_dim
        self.n_edge_types = n_edge_type
        self.n_node = n_node
        self.n_steps = n_steps

        for i in range(self.n_edge_types):
            fc = nn.Linear(self.state_dim, self.state_dim)
            self.add_module("fc_{}".format(i), fc)

        self.fcs = AttrProxy(self, "fc_")


        # Propogation Model
        self.propogator = Propogator(self.state_dim, self.n_node, self.n_edge_types)
        # # Output Model
        # self.out = nn.Sequential(
        #     nn.Linear(self.state_dim, self.state_dim),
        #     nn.Tanh(),
        #     self.line()
        # )

        self._initialization()

    def _initialization(self):
        for m in self.modules():
            if isinstance(m, nn.Linear):
                m.weight.data.normal_(0.0, 0.02)
                m.bias.data.fill_(0)

    def forward(self, prop_state, A):
        for i_step in range(self.n_steps):

            next_states = []
            for i in range(self.n_edge_types):
                next_states.append(self.fcs[i](prop_state))

            next_states = torch.stack(next_states).contiguous()
            next_states = next_states.view(-1, self.n_edge_types * self.state_dim)
            prop_state = self.propogator(next_states, prop_state, A)

        # output = self.out(prop_state)
        train_loader, test_loader = self.get_data(prop_state, A)
        return train_loader, test_loader

    def get_data(self, embedding_states, A):
        join_state = []
        for i in range(self.n_node):
            for j in range(self.n_node):
                join_state.append(torch.cat((embedding_states[i], embedding_states[j]), 0))
        data = torch.cat(join_state, 0).view(self.n_node * self.n_node, -1)
        labels = A.reshape(A.shape[0] * A.shape[1], )
        train_data = data[:int(0.7*data.shape[0]), :]
        train_labels = torch.tensor(labels[:int(0.7*len(labels))].astype(numpy.float32))
        test_data = data[int(0.7*data.shape[0]):, :]
        test_labels = torch.tensor(labels[int(0.7*len(labels)):].astype(numpy.float32))
        train_dataSet = Data.TensorDataset(train_data, train_labels)
        train_loader = Data.DataLoader(
            dataset=train_dataSet,
            batch_size=64,  # 批大小
            # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
            shuffle=True,  # 是否随机打乱顺序
            # num_workers=2,  # 多线程读取数据的线程数
        )
        test_dataSet = Data.TensorDataset(test_data, test_labels)
        test_loader = Data.DataLoader(
            dataset=test_dataSet,
            batch_size=64,  # 批大小
            # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
            shuffle=True,  # 是否随机打乱顺序
            # num_workers=2,  # 多线程读取数据的线程数
        )

        return train_loader, test_loader