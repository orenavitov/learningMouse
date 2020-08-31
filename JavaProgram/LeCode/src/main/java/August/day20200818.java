package August;

import java.util.ArrayList;
import java.util.List;

public class day20200818 {
    private static class ListNode {
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

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private static TreeNode sortedListToBST(ListNode head) {
        ListNode currentNode = head;
        ArrayList<ListNode> nodes = new ArrayList<>();
        while (currentNode != null) {
            nodes.add(currentNode);
            currentNode = currentNode.next;
        }
        int size = nodes.size();
        return generateSubTree(nodes, 0, size - 1);
    }

    private static TreeNode generateSubTree(List<ListNode> nodeList, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            return null;
        }
        int middleIndex = (startIndex + endIndex + 1) / 2;
        ListNode middleNode = nodeList.get(middleIndex);
        TreeNode node = new TreeNode(middleNode.val);
        node.left = generateSubTree(nodeList, startIndex, middleIndex - 1);
        node.right = generateSubTree(nodeList, middleIndex + 1, endIndex);
        return node;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(-10);
        ListNode node2 = new ListNode(-3);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        TreeNode root = sortedListToBST(node1);
        System.out.println("over!");

    }
}