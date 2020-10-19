package October;

import java.util.List;

public class day20201013 {
    private static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        ListNode next = cur.next;
        cur.next = pre;
        pre.next = swapPairs(next);
        return cur;

    }

    private static int sortTest(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i ++) {
            int cur = nums[i];
            if (cur < nums[0]) {
                int temp = nums[0];
                nums[0] = nums[i];
                nums[i] = temp;
                result ++;
                continue;
            }
            if (cur > nums[nums.length - 1]) {
                int temp = nums[nums.length - 1];
                nums[nums.length - 1] = nums[i];
                nums[i] = temp;
                result ++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
////        ListNode node4 = new ListNode(4);
////        ListNode node5 = new ListNode(5);
////        ListNode node6 = new ListNode(6);
//        node1.next = node2;
//        node2.next = node3;
////        node3.next = node4;
////        node4.next = node5;
////        node5.next = node6;
//        ListNode head = swapPairs(node1);
//        ListNode cur = head;
//        while (cur != null) {
//            System.out.print(cur.val + " ");
//            cur = cur.next;
//        }
        int[] nums = {3, 2, 1, 4, 6, 5};
        int result = sortTest(nums);
        System.out.println("result : " + result);
        for(int num : nums) {
            System.out.print(num + " ");
        }
    }
}
