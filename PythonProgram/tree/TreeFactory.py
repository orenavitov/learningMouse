import math
import numpy
import mih.slort.RandomNumFactory as factory
from mih.tree.Tree import treeNode
def treeCreater(list):
    depth = calculationDepth(list)
    size = len(list)
    tree = []
    def nodeCreater(tree, squence, value):
        node = treeNode()
        print("id:", id(node))
        node.squence = squence
        node.value = value
        tree.append(node)
    for i in range(0, size):
        nodeCreater(tree, i + 1, list[i])
    for node in tree:
        sequence = node.squence
        if not (sequence * 2 > size + 1):
            node.leftChild = sequence * 2
            node.rightChild = sequence * 2 + 1
        if int(sequence / 2) != 0:
            node.parentNode = int(sequence / 2)
    print("ok")

def calculationDepth(list):
    depth = 0
    length = len(list)
    while(int(length / 2) != 0):
        length = int(length / 2)
        depth = depth + 1
    return depth + 1

def forwardLookThroughTheTree()

nums = factory.randomNumFactory(5)
print('depth:', calculationDepth(nums))
treeCreater(nums)