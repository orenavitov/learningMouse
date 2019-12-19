'''
@Time: 2019/12/11 10:50
@Author: mih
@Des: 
'''
import torch

x = torch.empty(5, 3)
print(x)

x = torch.rand(5, 3)
print(x)

x = torch.zeros(5, 3, dtype=torch.long)
print(x)