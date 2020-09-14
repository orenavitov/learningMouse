package Sep.quShi;

import java.util.Scanner;

public class Test1 {
    public static void Solution() {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] detials = line.split(" ");
        String middleOrder = detials[0];
        String backOrder = detials[1];
        char start = backOrder.charAt(backOrder.length() - 1);
        result.append(start);
        int rootIndex = middleOrder.indexOf(start);
        String leftChildren = middleOrder.substring(0, rootIndex);
        String rightChildren = middleOrder.substring(rootIndex + 1, middleOrder.length());
        handleLeft(leftChildren, 0, backOrder, result);
        handleRight(rightChildren, rootIndex, backOrder, result);
        System.out.println(result.toString());
    }

    private static void handleLeft(String leftChildren, int startIndex,
                                   String tempBackOrderString, StringBuilder result) {
        if (leftChildren.length() == 1) {
            result.append(leftChildren);
            return;
        }
        if (leftChildren.length() > 1) {
            String subBackOrderString = tempBackOrderString.substring(startIndex, startIndex + leftChildren.length());
            char start = subBackOrderString.charAt(subBackOrderString.length() - 1);
            result.append(start);
            int rootIndex = leftChildren.indexOf(start);
            String newLeftChildren = leftChildren.substring(startIndex, rootIndex);
            String newRightChildren = leftChildren.substring(rootIndex + 1, leftChildren.length());

            handleLeft(newLeftChildren, startIndex, subBackOrderString, result);
            handleRight(newRightChildren, rootIndex + 1, subBackOrderString, result);

        }
    }

    private static void handleRight(String rightChildren, int startIndex,
                                    String tempBackOrderString, StringBuilder result) {
        if (rightChildren.length() == 1) {
            result.append(rightChildren);
            return;
        }
        if (rightChildren.length() > 1) {
            String subBackOrderString = tempBackOrderString.substring(startIndex,
                    startIndex + rightChildren.length());
            char start = subBackOrderString.charAt(subBackOrderString.length() - 1);
            result.append(start);
            int rootIndex = rightChildren.indexOf(start);

            String newLeftChildren = rightChildren.substring(0, rootIndex);
            String newRightChildren = rightChildren.substring(rootIndex + 1, rightChildren.length());

            handleLeft(newLeftChildren, startIndex, subBackOrderString, result);
            handleRight(newRightChildren, rootIndex + 1, subBackOrderString, result);
        }
    }

//    private static int getIndex(String s, char c) {
//        for (int i = 0; i < )
//    }

    public static void main(String[] args) {
        Solution();
    }
}
