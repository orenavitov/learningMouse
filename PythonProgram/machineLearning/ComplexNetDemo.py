import os
import networkx
import matplotlib.pyplot as plt
'''
本Demo主要演示使用networkx绘制复杂网络图
'''
if __name__ == '__main__':
    # 相当于绘制4K图片
    plt.rcParams['figure.dpi'] = 600
    G = networkx.Graph()

    with open(r"C:\Users\mih\Desktop\文件\bio-CE-GT.edges", "r") as graph_file:
        nodes = []
        lines_count = 1
        for line in graph_file.readlines():
            src, dst, weight = line.split(" ")
            if src not in nodes:
                nodes.append(src)
                G.add_node(src)
            if dst not in nodes:
                nodes.append(dst)
                G.add_node(dst)
            G.add_edge(src, dst)
            lines_count += 1
    pos = networkx.spring_layout(G)
    print("nodes: {0}".format(len(nodes)))
    print("edges: {0}".format(lines_count))
    networkx.draw(G, pos=pos, with_labels=False, node_size = 0.5, linewidths = 0.0001, width = 0.1)
    plt.show()
