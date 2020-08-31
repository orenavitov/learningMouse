package July;

public class day20200730 {


    /**
     * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
     *
     * 示例 1:
     *
     * 输入: 2
     * 输出: 1
     * 解释: 2 = 1 + 1, 1 × 1 = 1。
     * 示例 2:
     *
     * 输入: 10
     * 输出: 36
     * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
     *
     * 链接：https://leetcode-cn.com/problems/integer-break
     */
    private static int integerBreak(int n) {
        int[] result = new int[n + 1];
        if (n <= 1) {
            return 0;
        }
        result[0] = 0;
        result[1] = 0;
        result[2] = 1;

        for(int i = 2; i < n; i ++) {
            result[i] = 1;
        }
        for (int i = 2; i < n + 1; i ++) {
            for (int j = i - 1; j >= 2; j --) {
                int temp1 = (i - j) * result[j];
                int temp2 = (i - j) * j;
                int tempMax = Math.max(temp1, temp2);
                if (tempMax > result[i]) {
                    result[i] = tempMax;
                }

            }
        }
        return result[n];
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例：
     *
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * 链接：https://leetcode-cn.com/problems/add-two-numbers
     * @param args
     */
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode result = new ListNode(0);
        ListNode l1CurrentNode = l1;
        ListNode l2CurrentNode = l2;
        ListNode resultCurrentNode = result;
        boolean addOne = false;

        while (l1CurrentNode != null || l2CurrentNode != null) {
            int val1 = 0;
            int val2 = 0;
            if (l1CurrentNode != null) {
                val1 = l1CurrentNode.val;
                l1CurrentNode = l1CurrentNode.next;
            }
            if (l2CurrentNode != null) {
                val2 = l2CurrentNode.val;
                l2CurrentNode = l2CurrentNode.next;
            }
            int tempResult = 0;
            if (addOne) {
                tempResult = val1 + val2 + 1;
            } else {
                tempResult = val1 + val2;
            }
            if (tempResult >= 10) {
                addOne = true;
                resultCurrentNode.val = (tempResult - 10);
            } else {
                addOne = false;
                resultCurrentNode.val = tempResult;
            }
            if (l1CurrentNode != null || l2CurrentNode != null) {
                ListNode resultNextNode = new ListNode(0);
                resultCurrentNode.next = resultNextNode;
                resultCurrentNode = resultNextNode;
            }

        }
        if (addOne) {
            resultCurrentNode.next = new ListNode(1);
        }
        return result;
    }

    public static void main(String[] args) {
        ListNode l11 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);
        l11.next = l12;
        l12.next = l13;
        ListNode l21 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(4);
        l21.next = l22;
        l22.next = l23;
        ListNode result = addTwoNumbers(l11, l21);
    }
}
