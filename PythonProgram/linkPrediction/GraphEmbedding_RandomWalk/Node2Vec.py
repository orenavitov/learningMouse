'''
@Time: 2020/1/9 15:02
@Author: mih
@Des: 
'''
import numpy
import torch
from gensim.models import Word2Vec

from torch import nn
from GraphEmbedding_RandomWalk.NN import LineNetwork

class Node2vec(nn.Module):
    def __init__(self, G, A, walk_length, p, q, embed_size, iter, window_size, workers):
        super(Node2vec, self).__init__()
        self.G = G
        self.A = A
        self.walk_length = walk_length
        self.p = p
        self.q = q
        self.embed_size = embed_size
        self.iter = iter
        self.window_size = window_size
        self.workers = workers
        self.nodes = sorted(G.nodes(), key=lambda x: int(x))
        self.edges = list(G.edges())
        self.preprocess_transition_probs()
        self.get_embeddings(embed_size = embed_size, window_size = window_size, workers = workers, iter = iter)

        self.line = LineNetwork(feature_dim=self.embed_size * 2, hidden_layer_dim=self.embed_size, output_dim=2)
        self.loss = nn.CrossEntropyLoss()

    def forward(self, *input):
        edges = input[0]
        labels = input[1]
        edges = edges.view([2, -1])
        src_nodes = edges[0]
        dst_nodes = edges[1]
        src_embeddings = self.word_embeddings.index_select(index=src_nodes, dim=0)
        dst_embeddings = self.word_embeddings.index_select(index=dst_nodes, dim=0)
        edge_embeddings = torch.cat([src_embeddings, dst_embeddings], dim=1)
        output = self.line(edge_embeddings)
        loss = self.loss(output, labels)
        return loss

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
        return [accept, alias]

    def preprocess_transition_probs(self):
        G = self.G
        self.alias_nodes = {}
        for node in G.nodes():
            unnormalized_probs = [G[node][nbr].get('weight', 1.0)
                                  for nbr in G.neighbors(node)]
            norm_const = sum(unnormalized_probs)
            normalized_probs = [float(u_prob) / norm_const for u_prob in unnormalized_probs]
            self.alias_nodes[node] = self.create_alias_table(normalized_probs)
        self.alias_edges = {}

        for edge in self.edges:
            self.alias_edges[edge] = self.get_alias_edge(edge[0], edge[1])

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

    def get_sentences(self):
        self.sentences = []
        for v in self.nodes:
            self.sentences.append(self.node2vec_walk(walk_length=self.walk_length, start_node=v))

    def node2vec_walk(self, walk_length, start_node):
        G = self.G

        walk = [start_node]
        while len(walk) < walk_length:
            cur = walk[-1]
            cur_nbrs = list(G.neighbors(cur))
            if len(cur_nbrs) > 0:
                if len(walk) == 1:
                    walk.append(
                        cur_nbrs[self.alias_sample(self.alias_nodes[cur][0], self.alias_nodes[cur][1])])
                else:
                    prev = walk[-2]
                    edge = (prev, cur)
                    if edge not in self.edges:
                        edge = (cur, prev)
                    # try:
                    alias_edge =  self.alias_edges[edge]
                    src = alias_edge[0]
                    dst = alias_edge[1]
                    next_node = cur_nbrs[self.alias_sample(src, dst)]
                    # except:
                    #     print("edge: {0}".format(edge))
                    #     print("alias_edges: {0}".format(self.alias_edges[edge]))
                    walk.append(next_node)
            else:
                break
        return walk

    def alias_sample(self, accept, alias):
        try:
            N = len(accept)
            i = int(numpy.random.random() * N)
            r = numpy.random.random()
            if r < accept[i]:
                return i
            else:
                return alias[i]
        except:
            print("error in alias sample!")


    def get_embeddings(self, embed_size=30, window_size=7, workers=3, iter=10):
        self.get_sentences()
        print("Learning embedding vectors...")
        self.word2Vec = Word2Vec(sentences=self.sentences, size=self.embed_size, min_count=1, hs=0, sg=1,
                                 workers=self.workers,
                                 window=self.window_size, iter=self.iter)
        print("Learning embedding vectors done!")
        self.word_embeddings = []
        for node in self.G.nodes():
            self.word_embeddings.append(self.w2v_model.wv[node])