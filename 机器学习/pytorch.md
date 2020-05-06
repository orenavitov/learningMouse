# Pytorch

https://blog.csdn.net/mingqi1996/article/details/87889403

## Pytorch 计算图形成细节

https://www.cnblogs.com/catnip/p/8760780.html

## Tensor基本运算

1. 加法

torch.add(a, b)

2. 减法

torch.sub(a, b)

3. 乘法（对应元素相乘）

torch.mul(a, b)

4. 除法

torch.div(a, b)

5. 矩阵乘法

torch.mm(a, b)

torch.matmul(a, b)

对于高维矩阵只能用torch.matmul(a, b) 要求a, b 的前两个维度必须相同

6. 幂运算

a.pow(2)

对于开方可以使用a.sqrt()

7. 指数与对数运算

torch.exp(a)

torch.log(a)

## torch.nn.functional

* conv2d

conv2d(input, weight, bias = None, stride = 1, dilation = 1, group = 1)

input: 输入张量(minibatch_size, inChannels_size, h_size, w_size)

weight: 过滤器张量(outChannels_size, inChannels_size, h_size, w_size)

bias: out_channels

## pytorch 计算模型相关

* ListModule

* Parameter

* add_module

## 维度相关

1. a.permute(0, 1, 3, 2) 

最后两个维度调换

## torch.utils.data

https://zhuanlan.zhihu.com/p/28200166

## question

1. Variable 与 Tensor？



2. backward() 中的grad_tensors参数？

3. dataset, dataloader ？

https://zhuanlan.zhihu.com/p/30934236

4. module.parameters() module中的参数指的是什么？