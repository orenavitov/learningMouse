class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

"""
输入一个列表， 对每k个节点翻转一次， 如果最后不够k个则保持原顺序
例如： 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
每3个节点翻转一次：
结果为： 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8   
"""
class Solution:
    def __init__(self):
        pass

    def getlength(self, head):
        i = 0
        current = head
        while(current != None):
            current = current.next
            i = i + 1
        return i


    def findNext(self, startNode, currentNode, current_index, k):
        if current_index == k:
            startNode = currentNode.next
            return currentNode, startNode, currentNode
        else:
            nextNode, startNode, head = self.findNext(startNode, currentNode.next, current_index + 1, k)
            nextNode.next = currentNode
            return currentNode, startNode, head

    def nextBatch(self, startNode, k, batch, batchs):
        if batch == batchs + 1:
            return startNode
        lastNode, startNode, head = self.findNext(startNode, startNode, 1, k)
        lastNode.next = self.nextBatch(startNode, k, batch + 1, batchs)
        return head

    def reverseKGroup(self, head, k):
        l = self.getlength(head)
        batch = 1
        batchs = int(l / k)
        startNode = head
        head = self.nextBatch(startNode, k, batch, batchs)
        return head


def generateNodeList(length):
    nodes = []
    for i in range(length):
        node = ListNode(i)
        nodes.append(node)
    for i, node in enumerate(nodes):
        if(i < length - 1):
            node.next = nodes[i + 1]
    nodes[-1].next = None
    head = nodes[0]
    current = head
    while(current != None):
        print(current.val)
        current = current.next
    return head

def printlist(head):
    current = head
    while(current != None):
        print(current.val)
        current = current.next

if __name__ == '__main__':
    nodeList = generateNodeList(31)
    solution = Solution()
    head = solution.reverseKGroup(nodeList, 4)
    print("result:")
    printlist(head)