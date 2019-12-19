'''
@Time: 2019/12/11 11:02
@Author: mih
@Des: 
'''
import torch

x = torch.ones(2, 2, requires_grad = True)
print(x)

y = x + 2
print(y)

print(y.grad_fn)

z = y * y * 3
out = z.mean()
print(z, out)

a = torch.randn(2, 2)
a = ((a * 3) / (a - 1))
print(a.requires_grad)
a.requires_grad_(True)
print(a.requires_grad)
b = (a * a).sum()
print(b.grad_fn)
print(out.grad_fn)
# 执行out.backward()相当于指定了输出函数
print("out.backward():{0}".format(out.backward()))
print(x.grad)
# randn(n, m), 返回一个n * m的正太分布的张量
x = torch.randn(3, requires_grad = True)
y = x * 2
while y.data.norm() < 1000:
    y = y * 2
print(y)