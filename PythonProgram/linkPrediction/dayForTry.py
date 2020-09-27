class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    def deleteDuplication(self, head):
        if (head == None):
            return None
        pre = head
        next = pre.next
        while(pre.val == next.val):
            pre = self.findFirst(pre)
            if (pre == None):
                return None
            next = pre.next

        head = pre
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
        return head
    def findFirst(self, start):
        startVal = start.val
        next = start.next
        if (next == None):
            return start
        if (startVal != next.val):
            return start
        while(next != None and next.val == startVal):
            next = next.next
        return next



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
    node2 = ListNode(1)
    node3 = ListNode(2)
    node4 = ListNode(2)
    node5 = ListNode(3)
    node6 = ListNode(4)
    node7 = ListNode(5)
    # node8 = ListNode(3)
    # node9 = ListNode(3)
    node1.next = node2
    node2.next = node3
    node3.next = node4
    # node4.next = node5
    # node5.next = node6
    # node6.next = node7
    # node7.next = node8
    # node8.next = node9
    s = Solution()
    head = s.deleteDuplication(node1)
    start = head
    while (start != None):
        print("{0} ".format(start.val))
        start = start.next
