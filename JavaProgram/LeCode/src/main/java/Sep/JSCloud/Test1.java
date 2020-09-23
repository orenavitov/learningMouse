package Sep.JSCloud;

import java.util.Scanner;

public class Test1 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < T; i ++) {
            String line = scanner.nextLine();
            String[] detials = line.split(" ");
            int[] nodes = new int[detials.length];
            for (int j = 0; j < detials.length; j ++) {
                nodes[j] = Integer.valueOf(detials[j]);
            }
            int result = check(nodes, 0);
            if (result >= 1) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

    private static int check(int[] nodes, int index) {
        int result = 0;
        int leftResult = 0;
        int rightResult = 0;
        int leftSum = 0;
        int rightSum = 0;
        int leftIndex = index * 2 + 1;
        int rightIndex = index * 2 + 2;
        if (leftIndex >= nodes.length) {
            return 0;
        }
        if (leftIndex < nodes.length) {
            leftSum = nodes[leftIndex] + sum(nodes, leftIndex);
            leftResult = check(nodes, leftIndex);
        }
        if (rightIndex < nodes.length) {
            rightSum = nodes[rightIndex] + sum(nodes, rightIndex);
            rightResult = check(nodes, rightIndex);
        }
        if (leftSum == rightSum) {
            result = 1;
        } else {
            result = 0;
        }
        return result + leftResult + rightResult;
    }

    private static int sum(int[] nodes, int index) {
        if (index >= nodes.length) {
            return 0;
        }
        int leftSum = 0;
        int rightSum = 0;
        int leftIndex = index * 2 + 1;
        int rightIndex = index * 2 + 2;
        if (leftIndex < nodes.length) {
            leftSum = nodes[leftIndex] + sum(nodes, leftIndex);
        }
        if (rightIndex < nodes.length) {
            rightSum = nodes[rightIndex] + sum(nodes, rightIndex);
        }
        return leftSum + rightSum;
    }

    public static void main(String[] args) {
        Solution();
    }
}
