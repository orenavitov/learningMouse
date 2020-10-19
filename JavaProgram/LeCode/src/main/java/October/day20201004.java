package October;

public class day20201004 {
    public static int minimumOperations(String leaves) {
        int result = 0;
        int start = 0;
        int end = leaves.length() - 1;
        boolean forwardChangeTag = false;
        boolean backwardChangeTag = false;
        char sc = leaves.charAt(start);
        if (sc == 'y') {
            result++;
            forwardChangeTag = true;
            start++;
        }
        char ec = leaves.charAt(end);
        if (ec == 'y') {
            result++;
            backwardChangeTag = true;
            end--;
        }
        while (start <= end) {
            sc = leaves.charAt(start);
            ec = leaves.charAt(end);
            if (start == end) {
                if (sc == 'r') {
                    if (forwardChangeTag || backwardChangeTag) {
                        result++;
                    }
                }
                break;
            }
            if (sc == 'y') {
                forwardChangeTag = true;
                start++;
            }
            if (sc == 'r') {
                if (forwardChangeTag) {
                    result++;
                    forwardChangeTag = false;
                }
                start++;
            }
            if (ec == 'y') {
                backwardChangeTag = true;
                end--;
            }
            if (ec == 'r') {
                if (backwardChangeTag) {
                    result++;
                    backwardChangeTag = false;
                }
                end--;
            }
        }
        if (start != end && !forwardChangeTag && !backwardChangeTag) {
            result++;
        }

        return result;
    }

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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode start1 = l1;
        ListNode start2 = l2;
        boolean add = false;
        while (start1 != null && start2 != null) {
            int val1 = start1.val;
            int val2 = start2.val;
            int sum = add ? val1 + val2 + 1 : val1 + val2;
            if (sum >= 10) {
                start1.val = sum - 10;
                add = true;
            } else {
                start1.val = sum;
                add = false;
            }
            start1 = start1.next;
            start2 = start2.next;
        }
        if (start1 == null) {
            start1 = start2;
            while (add && start2 != null) {
                int sum = start2.val + 1;
                if (sum >= 10) {
                    start2.val = sum - 10;
                    add = true;
                } else {
                    start2.val = sum;
                    add = false;
                }
                if (start2.next == null) {
                    if (add) {
                        start2.next = new ListNode(1);
                        add = false;
                    }
                } else {
                    start2 = start2.next;
                }
            }

        } else {
            while (add) {
                int sum = start1.val + 1;
                if (sum >= 10) {
                    start1.val = sum - 10;
                    add = true;
                } else {
                    start1.val = sum;
                    add = false;
                }
                if (start1.next == null) {
                    if (add) {
                        start1.next = new ListNode(1);
                        add = false;
                    }
                } else {
                    start1 = start1.next;
                }
            }
        }
        return l1;
    }

    public static void main(String[] args) {
        ListNode n11 = new ListNode(2);
        ListNode n12 = new ListNode(4);
        ListNode n13 = new ListNode(9);
        n11.next = n12;
        n12.next = n13;
        ListNode n21 = new ListNode(5);
        ListNode n22 = new ListNode(6);
        ListNode n23 = new ListNode(4);
        ListNode n24 = new ListNode(9);
        n21.next = n22;
        n22.next = n23;
        n23.next = n24;
        ListNode result = addTwoNumbers(n11, n21);
        ListNode start = result;
        while (start != null) {
            System.out.print(start.val + " ");
            start = start.next;
        }
    }
}
