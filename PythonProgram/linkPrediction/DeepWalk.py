'''
@Time: 2019/12/31 15:49
@Author: mih
@Des: 
'''
import random
import networkx
import matplotlib.pyplot as plt
from gensim.models import Word2Vec
import numpy
from imblearn.over_sampling import SMOTE

class Deep_Walk():
    # walk_length: 随机游走的步数
    # start_node: 随机游走开始的节点
    # A: 图的邻接矩阵
    def __init__(self, walk_length, G, A, embed_size = 4):
        self.walk_length = walk_length
        self.embed_size = embed_size
        self.G = G
        self.A = A
        self.nodes = sorted(G.nodes(), key=lambda x: int(x))
        self.edges = G.edges()

    def get_sentence(self, start_node):
        sentence = [start_node]
        current_walk_length = 0
        while (current_walk_length <= self.walk_length):
            current_walker_index = sentence[-1]
            current_walker_index_neighbors = list(self.G.neighbors(current_walker_index))
            if (len(current_walker_index_neighbors) > 0):
                sentence.append(random.choice(current_walker_index_neighbors))
            else:
                # 如果是孤立点
                pass
            current_walk_length = current_walk_length + 1
        return sentence

    def get_sentences(self, nodes):
        sentences = []
        for node in nodes:
            sentences.append(self.get_sentence(node))
        self.sentences = sentences
        return sentences

    def get_embedding(self, embed_size = 30, window_size = 7, workers = 3, iter = 10, **kwargs):
        self.get_sentences(self.nodes)
        kwargs['sentences'] = self.sentences
        kwargs["min_count"] = kwargs.get("min_count", 1)
        kwargs['size'] = embed_size
        kwargs["sg"] = 1  # skip gram
        kwargs["hs"] = 1  # deepwalk use Hierarchical Softmax
        kwargs["workers"] = workers
        kwargs["window"] = window_size
        kwargs["iter"] = iter

        print("Learning embedding vectors...")
        model = Word2Vec(**kwargs)
        print("Learning embedding vectors done!")

        self.w2v_model = model
        if self.w2v_model is None:
            print("model not train")
            return {}
        self._embeddings = {}
        for word in self.nodes:
            self._embeddings[word] = self.w2v_model.wv[word]
        return self._embeddings

    def get_data(self):
        data = []
        embeddings = self.get_embedding()
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