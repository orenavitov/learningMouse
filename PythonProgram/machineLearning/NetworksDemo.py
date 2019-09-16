import networkx
import matplotlib.pyplot as plt
if __name__ == '__main__':
    # 绘制无向图
    G = networkx.Graph()
    # 在图中增加两个节点2， 3
    G.add_nodes_from([2, 3])
    # 在图中增加一条2， 3节点边
    G.add_edge(2, 3)
    # 在涂总增加1、 2之间， 3、 4之间的边
    G.add_edges_from([(1, 2), (3, 4)])
    #
    G.add_node(node_for_adding="7", attr={'name': 1, 'wight': 2})

    networkx.draw(G, with_labels=True)
    plt.show()