package October;

public class day20201018 {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slowPoint = head;
        ListNode next = slowPoint;
        for (int i = 0; i < n + 1; i ++) {
            next = next.next;
            if (next == null) {
                if (n - i == 1) {
                    return head.next;
                } if(n - i > 1) {
                    return null;
                }
            }
        }
        while (next != null) {
            next = next.next;
            slowPoint = slowPoint.next;
        }
        slowPoint.next = slowPoint.next.next;
        return head;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
//        node4.next = node5;
        ListNode result = removeNthFromEnd(node1, 4);
        ListNode temp = result;
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }
}
