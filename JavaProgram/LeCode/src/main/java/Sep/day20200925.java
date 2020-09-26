package Sep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class day20200925 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        List<Integer> inorderList = new ArrayList<>();
        List<Integer> postorderList = new ArrayList<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderList.add(inorder[i]);
        }
        for (int i = 0; i < postorder.length; i++) {
            postorderList.add(postorder[i]);
        }

        return findRootNode(inorderList, postorderList);
    }

    public static TreeNode findRootNode(List<Integer> inorder, List<Integer> postorder) {
        if (postorder.size() == 0) {
            return null;
        }
        int rootVal = postorder.get(postorder.size() - 1);
        TreeNode root = new TreeNode(rootVal);
        List<Integer> leftChildren = findLeftChildren(inorder, rootVal);
        List<Integer> rightChildren = findRightChild(inorder, rootVal);
        TreeNode leftChild = findRootNode(leftChildren, postorder.subList(0, leftChildren.size()));
        TreeNode rightChild = findRootNode(rightChildren, postorder.subList(leftChildren.size(), postorder.size() - 1));
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }

    public static List<Integer> findLeftChildren(List<Integer> inorder, int root) {
        int rootIndex = inorder.indexOf(root);
        return inorder.subList(0, rootIndex);
    }

    public static List<Integer> findRightChild(List<Integer> inorder, int root) {
        int rootIndex = inorder.indexOf(root);
        return inorder.subList(rootIndex + 1, inorder.size());
    }

    // 模拟1~N的入栈出栈
    public static void stackAction(int N) {

    }

    public static List<String> prodecer(Stack<Integer> produces, int cur, int N, List<String> results) {
        for (int i = cur; i < N; i++) {
            for (int j = 1; j <= N - i; j++) {
                pushIn(produces, i, j);
                List<String> tempResults = customer(produces, i, N, new ArrayList<>());
//                results.addAll()
            }
        }
        return null;
    }

    public static List<String> crossJoinList(List<String> l1, List<String> l2) {
        List<String> result = new ArrayList<>();
        for (String l : l1) {
            for (String L : l2) {
                result.add(l + L);
            }
        }
        return result;
    }

    public static List<String> customer(Stack<Integer> produces, int cur, int N, List<String> results) {
        for (int i = 1; i <= produces.size(); i++) {
            String temp = popOut(produces, 1);
            results.add(temp);
        }
        return results;

    }

    public static void pushIn(Stack<Integer> produces, int start, int n) {
        for (int i = 0; i < n; i++) {
            produces.push(start + i);
        }
    }

    public static String popOut(Stack<Integer> produces, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(produces.pop());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        TreeNode root = buildTree(inorder, postorder);
        System.out.println("end!");
    }
}
