package October;

public class day20201009 {
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode hasCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slowPoint = head;
        ListNode fastPoint = head;
        while (true) {
            slowPoint = slowPoint.next;
            if (slowPoint == null) {
                return null;
            }
            if (fastPoint.next == null) {
                return null;
            } else {
                if (fastPoint.next.next == null) {
                    return null;
                } else {
                    fastPoint = fastPoint.next.next;
                }
            }
            if (slowPoint == fastPoint) {
                ListNode enterNode = findTheCircleListEnter(slowPoint, fastPoint, head);
                return enterNode;
            }
        }
    }

    private static ListNode findTheCircleListEnter(ListNode slowPoint, ListNode fastNode, ListNode head) {
        fastNode = head;
        while (true) {
            if (fastNode == slowPoint) {
                return fastNode;
            } else {
                fastNode = fastNode.next;
                slowPoint = slowPoint.next;
            }
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        System.out.println(hasCycle(node1));

    }
}
