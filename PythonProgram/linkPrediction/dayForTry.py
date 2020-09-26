class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    def deleteDuplication(self, p):
        pre = p
        next = self.findNext(pre)
        while(next != None):
            nextNext = next.next
            if (nextNext != None and next.val == next.next.val):
                next = self.findNext(next)
                continue
            else:
                pre.next = next
                pre = next
                next = self.findNext(pre)
        pre.next = next
        return p
    def findNext(self, start):
        pre = start
        cur = pre.next
        while(cur != None):
            if (pre.val == cur.val):
                pre = cur
                cur = cur.next
                continue
            return cur
        return cur

if __name__ == '__main__':
    node1 = ListNode(1)
    node2 = ListNode(2)
    node3 = ListNode(3)
    node4 = ListNode(3)
    node5 = ListNode(3)
    node6 = ListNode(3)
    node1.next = node2
    node2.next = node3
    node3.next = node4
    node4.next = node5
    node5.next = node6
    s = Solution()
    head = s.deleteDuplication(node1)
    start = head
    while (start != None):
        print("{0} ".format(start.val))
        start = start.next
