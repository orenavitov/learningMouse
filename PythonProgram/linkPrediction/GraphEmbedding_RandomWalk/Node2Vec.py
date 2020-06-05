'''
@Time: 2020/1/9 15:02
@Author: mih
@Des: 
'''
import numpy
import torch
from gensim.models import Word2Vec
from imblearn.over_sampling import SMOTE
import torch.utils.data as Data

def getDataLoader(train_data, train_label, test_data, test_label, batch_size):
    train_data = torch.tensor(train_data, dtype=torch.float)
    train_label = torch.tensor(train_label, dtype=torch.long)
    train_dataSet = Data.TensorDataset(train_data, train_label)
    train_loader = Data.DataLoader(
        dataset=train_dataSet,
        batch_size=batch_size,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    test_data = torch.tensor(test_data, dtype=torch.float)
    test_label = torch.tensor(test_label, dtype=torch.long)
    test_dataSet = Data.TensorDataset(test_data, test_label)
    test_loader = Data.DataLoader(
        dataset=test_dataSet,
        batch_size=batch_size,  # 批大小
        # 若dataset中的样本数不能被batch_size整除的话，最后剩余多少就使用多少
        shuffle=True,  # 是否随机打乱顺序
        # num_workers=2,  # 多线程读取数据的线程数
    )
    return train_loader, test_loader

class Node2vec():

    def __init__(self, G, A, walk_length, num_walks, walker, p, q, embed_size, iter, window_size, workers):

        self.G = G
        self.A = A
        self.edges = []
        for edge in G.edges:
            src = edge[0]
            dst = edge[1]
            self.edges.append((src, dst))
            self.edges.append((dst, src))

        self.walk_length = walk_length
        self.num_walks = num_walks
        self.walker = walker
        self.p = p
        self.q = q
        self.embed_size = embed_size
        self.iter = iter
        self.window_size = window_size
        self.workers = workers

        self.preprocess_transition_probs()
        self.sentences = self.walks(nodes=self.G.nodes, num_walks=self.num_walks,
                                              walk_length=self.walk_length)

    def create_alias_table(self, area_ratio):
        l = len(area_ratio)
        accept, alias = [0] * l, [0] * l
        small, large = [], []
        area_ratio_ = numpy.array(area_ratio) * l
        for i, prob in enumerate(area_ratio_):
            if prob < 1.0:
                small.append(i)
            else:
                large.append(i)
        while small and large:
            small_idx, large_idx = small.pop(), large.pop()
            accept[small_idx] = area_ratio_[small_idx]
            alias[small_idx] = large_idx
            area_ratio_[large_idx] = area_ratio_[large_idx] - \
                                     (1 - area_ratio_[small_idx])
            if area_ratio_[large_idx] < 1.0:
                small.append(large_idx)
            else:
                large.append(large_idx)
        while large:
            large_idx = large.pop()
            accept[large_idx] = 1
        while small:
            small_idx = small.pop()
            accept[small_idx] = 1
        return accept, alias

    def preprocess_transition_probs(self):
        G = self.G
        alias_nodes = {}
        for node in G.nodes():
            unnormalized_probs = [G[node][nbr].get('weight', 1.0)
                                  for nbr in G.neighbors(node)]
            norm_const = sum(unnormalized_probs)
            normalized_probs = [float(u_prob) / norm_const for u_prob in unnormalized_probs]
            alias_nodes[node] = self.create_alias_table(normalized_probs)
        alias_edges = {}

        for edge in self.edges:
            alias_edges[edge] = self.get_alias_edge(edge[0], edge[1])
        self.alias_nodes = alias_nodes
        self.alias_edges = alias_edges
        return

    def get_alias_edge(self, t, v):
        G = self.G
        p = self.p
        q = self.q
        unnormalized_probs = []
        for x in G.neighbors(v):
            weight = G[v][x].get('weight', 1.0)
            if x == t:
                unnormalized_probs.append(weight / p)
            elif G.has_edge(x, t):
                unnormalized_probs.append(weight)
            else:
                unnormalized_probs.append(weight / q)
        norm_const = sum(unnormalized_probs)
        normalized_probs = [
            float(u_prob) / norm_const for u_prob in unnormalized_probs]

        return self.create_alias_table(normalized_probs)

    def walks(self, nodes, num_walks, walk_length):
        sentences = []
        for _ in range(num_walks):
            numpy.random.shuffle(list(nodes))
            for v in nodes:
                sentences.append(self.node2vec_walk(
                    walk_length=walk_length, start_node=v))
        return sentences

    def node2vec_walk(self, walk_length, start_node):
        G = self.G
        alias_nodes = self.alias_nodes
        alias_edges = self.alias_edges
        walk = [start_node]
        while len(walk) < walk_length:
            cur = walk[-1]
            cur_nbrs = list(G.neighbors(cur))
            if len(cur_nbrs) > 0:
                if len(walk) == 1:
                    walk.append(
                        cur_nbrs[self.alias_sample(alias_nodes[cur][0], alias_nodes[cur][1])])
                else:
                    prev = walk[-2]
                    edge = (prev, cur)
                    next_node = cur_nbrs[self.alias_sample(alias_edges[edge][0],
                                                           alias_edges[edge][1])]
                    walk.append(next_node)
            else:
                break
        return walk

    def alias_sample(self, accept, alias):
        N = len(accept)
        i = int(numpy.random.random() * N)
        r = numpy.random.random()
        if r < accept[i]:
            return i
        else:
            return alias[i]

    def train(self, **kwargs):
        kwargs["sentences"] = self.sentences
        kwargs["min_count"] = kwargs.get("min_count", 0)
        kwargs["size"] = self.embed_size
        kwargs["sg"] = 1
        kwargs["hs"] = 0  # node2vec not use Hierarchical Softmax
        kwargs["workers"] = self.workers
        kwargs["window"] = self.window_size
        kwargs["iter"] = self.iter
        print("Learning embedding vectors...")
        model = Word2Vec(**kwargs)
        print("Learning embedding vectors done!")
        self.w2v_model = model


    def get_embeddings(self):
        if self.w2v_model is None:
            print("model not train")
            return {}

        self._embeddings = {}
        for node in self.G.nodes():
            self._embeddings[node] = self.w2v_model.wv[node]

        return self._embeddings

    def get_data(self):
        data = []
        embeddings = self.get_embeddings()
        node_number = len(self.G.nodes)
        for i in range(node_number):
            hidden_state_i = embeddings[str(i + 1)]
            for j in range(node_number):
                hidden_state_j = embeddings[str(j + 1)]
                # 将节点隐藏状态进行拼接
                if i == j:
                    # _hidden_state_j = [item * 100 for item in hidden_state_j]
                    # data.append((numpy.concatenate((hidden_state_j, hidden_state_j)), 1))
                    data.append((hidden_state_j - hidden_state_j, 1))
                else:
                    label = self.A[i][j]
                    # _hidden_state_j = [item * 100 for item in hidden_state_j]
                    # _hidden_state_i = [item * 100 for item in hidden_state_i]
                    data.append((hidden_state_i - hidden_state_j, label))

        data_size = len(data)
        s = int(data_size * 0.5)
        test_data = [item[0] for item in data][s:]
        test_label = [item[1] for item in data][s:]

        train_data = [item[0] for item in data][:s]
        train_label = [item[1] for item in data][:s]
        # X = [d[0] for d in data]
        # Y = [d[1] for d in data]
        smote = SMOTE(k_neighbors=10)
        X_res, Y_res = smote.fit_resample(X=train_data, y=train_label)
        # 打乱顺序
        train_data_size = len(X_res)
        i = 0
        shuffle_train_data = []
        while(i < train_data_size):
            shuffle_train_data.append((X_res[i], Y_res[i]))
            i = i + 1
        numpy.random.shuffle(shuffle_train_data)
        # # 取70%的数据作为训练集， 30%的数据作为测试集， 暂时不错检验集
        # s = int(data_size * 0.5)
        train_data = [item[0] for item in shuffle_train_data]
        train_label = [item[1] for item in shuffle_train_data]
        # test_data = [item[0] for item in data][s:]
        # test_label = [item[1] for item in data][s:]
        return train_data, train_label, test_data, test_label