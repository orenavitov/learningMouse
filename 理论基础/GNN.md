# GNN

## The Graph Neural Network Model

> $$ x_n = f_w(l_n, l_{co[n]}, x_{ne[n]}, l_{ne[n]}) \\[2ex]
 o_n = g_w(x_n, l_n) \tag{1} $$  

 > $$
x = F_w(x, l) 
\\[2ex]
o = G_w(x, l_n) \tag{2} $$

> $$  x_n = \sum_{u \in ne[n]}h_w(l_n, l_{(n, u), x_u, l_u}) \tag{3}$$

> $$ x(t+1) = F_w(x(t), l) \tag{4}$$

> $$ x_n(t+1)=f_w(l_n, l_{co[n]}, x_{ne[n]}(t), l_{ne[n]}) 
\\[2ex]
o_n(t)=g_w(x_n(t),l_n) \ \ n\in N \tag{5}$$ 

>$$ \ $$

## Gated Graph Sequence Neural Network

节点向量：$h_v\in \mathbb{R}^D$

节点标签：$l_v \in \{1, ... , L_V\}$

边标签：$l_v \in \{1, ... ,L_{\varepsilon} \}$

子图S中的节点向量集合：$h_S=\{ h_v | v \in S\}$

子图S中的边标签集合：$l_S=\{l_e | e\in S\}$

边的入节点表示：$IN(v) = \{v^{'} | \{v^{'}, v\}\in \varepsilon \}$ 

边的出节点表示：$OUT(v) = \{v^{'} | \{ v, v^{'}\}\in \varepsilon \}$


### 传播模型

节点的迭代表示：

>$$ h_{v}^{t} = f^{*}(l_v, l_{Co(v)}, l_{NBR(v)}, h^{(t-1)}_{NBR(v)}) $$

最终会达到收敛状态。

$$ f^{*}(l_v, l_{Co(v)}, l_{NBR(v)}, h^{(t-1)}_{NBR(v)}) = \sum_{v^{'} \in IN(v)}f(l_v,l_{(v', v)}, l_{v'}, h_{v'}^{(t-1)}) + \sum_{v' \in OUT(v)}f(l_v,l_{(v', v)}, l_{v'}, h_{v'}^{(t-1)})$$

上式中$f(.)$或者是一个线性函数或者是一个神经网络， $f(.)$可表示如下：
>$$ f(l_v, l_{(v, v')}, l_{v'}, h^{'}_{v'}) = A^{(  l_v,l_{(v, v')}, l_{v'})}h_{v'}^{t-1}  + b^{(l_v,l_{(v, v')}, l_{v'})}$$
其中A， b是训练参数

学习过程通过$Almeida-Pineda algorithm$实现， 好处是不用存储计算过程中产生的变量， 缺点是要求传播过程必须是一个压缩映射。

GG-NN中使用$Gated Recurrent Units$， 相对于$Almeida-Pineda algorithm$需要更多的存储， 但传播的过程不必限制为压缩映射。

GNN中不必关注节点的初始化表示， 因为压缩映射保证存在一个固定点， 然而在GG-NN中不存在这种情况， 需要将节点的标签进行汇聚， 使用向量$x$表示。

>$$ h_G = tanh(\sum_{v \in V}\sigma(i(h_v^{(T), x_v}))\bigodot tanh(j(h_v^{(T)}, x_v))) $$

$i, j$是将$h_v^{(T)}, x_v$作为输入的神经网络。

GG-NN操作一个序列并且产生序列的输出$o^{(1)}...o^{(K)}$, 对于第$k^{th}$步的输出， 标记节点的声明矩阵为$X^{(k)} = [x_1^{(k)};...;x_{|V|}^{(k)}]^T \in \mathbb{R}^{|V| \times L_{V}}$