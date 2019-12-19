'''
@Time: 2019/12/17 19:39
@Author: mih
@Des: 
'''
import torch
import torch.nn as nn
import numpy
import torch.nn.functional as F
import torch.optim as optim

import torch.utils.data as Data

#Spectrum Convolution
class Spectrum_Convolution_NN(nn.Module):

    def __init__(self):
        pass

    def forward(self, input):
        D = [numpy.sum(row) ** (-0.5) for row in input]
        D = numpy.diag(D)
        D = torch.tensor(D)
        input_shape = input.shape
        i_ones = torch.ones(input_shape)
        A = input + i_ones
        A = torch.tensor(A)
        A = torch.mul(D, A)
        A = torch.mul(A, D)

        weight0 = torch.randn(input_shape)

        pass

#
class AutoCode(nn.Module):
    def __init__(self, num_features):
        super(AutoCode, self).__init__()
        self.num_features = num_features
        self.line1 = nn.Linear(10, self.num_features, bias=True)
        self.line2 = nn.Linear(self.num_features, 10, bias=True)
        pass
    def forward(self, input):
        input.requires_grad_(True)
        shape = input.shape
        x = self.line1(input)
        out = self.line2(x)
        return out

if __name__ == '__main__':
    ac = AutoCode(2)
    print(ac)
    print("{0}".format(list(ac.parameters())))
    # 训练数据
    input = torch.randn(100, 10)
    # 训练数据的标签
    target = torch.ones(100, 10)
    out = ac(input)
    # 指定dataset的输入数据与标签数据
    torch_dataset = Data.TensorDataset(input, target)
    loader = Data.DataLoader(
        dataset=torch_dataset,
        batch_size=10,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    cirterion = nn.MSELoss()
    optimizer = optim.SGD(ac.parameters(), lr=0.01)
    for epoch in range(10):
        running_loss = 0.0
        for step, data in enumerate(loader, 1):
            # 获取输入， 标签
            input, target = data
            # 梯度置0
            optimizer.zero_grad()

            out = ac(input)
            loss = cirterion(out, target)
            loss.backward()
            optimizer.step()
            # 打印状态信息
            running_loss += loss.item()
            if step % 5 == 0:
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, step, running_loss / 5))
                running_loss = 0.0



