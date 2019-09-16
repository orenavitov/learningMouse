import os
import networkx
import matplotlib.pyplot as plt
if __name__ == '__main__':
    plt.rcParams['figure.dpi'] = 300
    G = networkx.Graph()
    with open(r"C:\Users\mihao\Desktop\bio-CE-GT.edges", "r") as graph_file:
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
    print("nodes: {0}".format(len(nodes)))
    print("edges: {0}".format(lines_count))
    networkx.draw(G, with_labels=True, node_size = 50)
    plt.show()
