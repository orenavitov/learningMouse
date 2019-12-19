'''
@Time: 2019/12/18 15:49
@Author: mih
@Des:
假设传播过程使用一个W矩阵实现， 即一个Liner；
输出过程使用一个G矩阵实现， 即一个Liner；
假设输入数据是一个（N*N, N）的矩阵， 其中第一个维度表示一共可能有N*N条边， 边的标号按邻接矩阵的行展开；
第二个维度表示第k条边的隐藏状态， 使用源宿节点的状态相加表示， 源宿节点的状态为节点与其他各点形成边的概率，
已经存在边的用1表示；
假设第一个Liner层的神经元个数为整个图中节点的最大度值；
第二个Liner层的神经元个数为2， 表示存在链路的可能性， 不存在链路的可能性
'''
import torch
import torch.nn as nn
import numpy
import torch.nn.functional as F
import torch.optim as optim
import torch.utils.data as Data
import Main

class AutoCode(nn.Module):
    def __init__(self, node_number, max_d):
        super(AutoCode, self).__init__()
        self.node_number = int(node_number)
        self.max_d = int(max_d)
        self.line1 = nn.Linear(self.node_number, self.max_d, bias=False)
        self.line2 = nn.Linear(max_d, 2, bias=True)

    def forward(self, input):
        input.requires_grad_(True)
        # shape = input.shape
        x = self.line1(input)
        out = self.line2(x)
        # out = F.softmax(out, dim=1)
        # result = []
        # for o in out:
        #     if 0[0] >= o[1]:
        #         result.append(1)
        #     else:
        #         result.append(0)

        return out

if __name__ == '__main__':
    # 准备训练数据， 测试数据
    train_data, train_label, test_data, test_label, d_info, node_number = Main.proper_data_(r"C:\Users\mih\Desktop\文件\bio-CE-GT.edges")
    max_d = numpy.max(d_info)
    train_data = torch.tensor(train_data, dtype = torch.float)
    train_label = torch.tensor(train_label, dtype = torch.long)
    train_dataSet = Data.TensorDataset(train_data, train_label)
    train_loader = Data.DataLoader(
        dataset = train_dataSet,
        batch_size=64,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    test_data = torch.tensor(test_data, dtype = torch.float)
    test_label = torch.tensor(test_label, dtype = torch.long)
    test_dataSet = Data.TensorDataset(test_data, test_label)
    test_loader = Data.DataLoader(
        dataset=test_dataSet,
        batch_size=16,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    ac = AutoCode(node_number, max_d)
    use_gpu = torch.cuda.is_available()
    if (use_gpu):
        ac = ac.cuda()


    # 定义损失函数
    loss_function = nn.CrossEntropyLoss()
    optimizer = optim.SGD(ac.parameters(), lr=0.001, momentum=0.9)
    # 多批次循环
    for epoch in range(100):
        running_loss = 0.0
        for i, data in enumerate(train_loader, 0):
            # 获取输入
            inputs, labels = data
            if (use_gpu):
                inputs = inputs.cuda()
                labels = labels.cuda()

            # 梯度置0
            optimizer.zero_grad()
            # 正向传播，反向传播，优化
            outputs = ac(inputs)
            loss = loss_function(outputs, labels)
            if (use_gpu):
                loss.cuda()

            loss.backward()
            optimizer.step()
            # 打印状态信息
            running_loss += loss.item()
            if i % 4 == 0:  # 每2000批次打印一次
                print('[%d, %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss / 4))
                running_loss = 0.0
    # 模型进行评估
    ac.eval()
    correct = 0
    total = 0
    for test_data, test_label in test_loader:
        outputs = ac(test_data)
        # torch.max 返回ouputs中第dim个维度的最大值即索引
        _, predictions = torch.max(outputs.data, 1)
        total += test_label.size(0)
        correct += (predictions == test_label).sum()
    print('准确率: %.4f %%' % (100 * correct / total))


