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

$$ f^{*}(l_v, l_{Co(v)}, l_{NBR(v)}, h^{(t-1)}_{NBR(v)}) = \sum_{v^{'} \in IN(v)}f(l_v,l_{(v', v)}, l_{v'}, h_{v'}^{(t-1)}) + f(l_v,l_{(v', v)}, l_{v'}, h_{v'}^{(t-1)})$$