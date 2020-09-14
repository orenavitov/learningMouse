package Sep.goodFuture;

public class Test2 {

    private static class ListNode {
        public int data;
        public ListNode next;
        public ListNode(int data) {
            this.data = data;
        }
    }

    public static ListNode reverseList (ListNode head) {
        // write code here
//        ListNode firstNode = head.next;
        if (head == null) {
            return null;
        }
        ListNode firstNode = head;
//        ListNode last = firstNode;
        ListNode nextNode = firstNode.next;
        ListNode temp = null;
        firstNode.next = null;
        while (nextNode != null) {
            temp = nextNode.next;
            nextNode.next = firstNode;
            firstNode = nextNode;
            nextNode = temp;
        }
//        head.next = firstNode;
//        last.next = null;
        return firstNode;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        ListNode newFirst = reverseList(node1);
        ListNode first = newFirst;
        while (first != null) {
            System.out.print(first.data + " ");
            first = first.next;
        }
    }
}
