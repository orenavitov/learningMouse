
import sys
if __name__ == "__main__":
    # 读取第一行的n
    n = int(sys.stdin.readline().strip())
    ans = 0
    for i in range(n):
        # 读取每一行
        line = sys.stdin.readline().strip()
        # 把每一行的数字分隔后转化成int列表
        values = list(map(int, line.split()))
        for v in values:
            ans += v
    print(ans)

def test1():
    # Definition for singly-linked list.
    class Node():
        def __init__(self, x):
            self.val = x

        def set_next(self, next):
            self.next = next

        def get_next(self):
            return self.next


        def get_val(self):
            return self.val

    class Solution():
        def getlenght(self, head):
            current = head
            length = 0
            while(current.next != None):
                current = current.next
                length = length + 1
            return length + 1

        def getIndex(self, head, index):
            i = 0
            current = head
            while(i < index):
                current = current.next
                i = i + 1
            return current

        def reorderList(self, head):
            """
            Do not return anything, modify head in-place instead.
            """
            length = self.getlenght(head)
            step = 0
            steps = int(length / 2)
            while(step <= steps):
                current = self.getIndex(head, step)
                current_next = current.get_next()
                last = self.getIndex(head, length - 1)
                current.set_next(last)
                last.set_next(current_next)
                step = step + 2
            tail = self.getIndex(head, length - 1)
            tail.set_next(None)

    def test():
        node1 = Node(1)
        node2 = Node(2)
        node3 = Node(3)
        node4 = Node(4)
        node5 = Node(5)
        node1.set_next(node2)
        node2.set_next(node3)
        node3.set_next(node4)
        node4.set_next(node5)
        node5.set_next(None)
        s= Solution()
        s.reorderList(node1)
        current = node1
        while(current != None):
            print("{0} ".format(current.get_val()))
            current = current.get_next()
    test()

if __name__ == '__main__':
    test1()
