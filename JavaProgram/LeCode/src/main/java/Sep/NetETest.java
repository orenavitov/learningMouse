package Sep;

import java.util.LinkedList;
import java.util.Scanner;

public class NetETest {

    private static void Solution1() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < secondLineDetials.length; i ++) {
            temp.addLast(Integer.valueOf(secondLineDetials[i]));
        }


    }

    private static void dfs(LinkedList<Integer> temp) {
        int minIndex = findMinIndex(temp);
        int minNum = temp.get(minIndex);

    }

    private static int findMinIndex(LinkedList<Integer> temp) {
        int i = -1;
        int min = temp.get(i);
        for (int index = 0; index < temp.size(); index ++) {
            int cur = temp.get(index);
            if (cur < min) {
                min = cur;
                i = index;
            }
        }
        return i;
    }
    // 是否存在奇数
    private static boolean has1(LinkedList<Integer> temp) {
        boolean result = false;
        for (int i = 0; i < temp.size(); i ++) {
            int cur = temp.get(i);
            if (cur % 2 == 1) {
                return true;
            }

        }
        return result;
    }

    // 是否存在偶数
    private static boolean has2(LinkedList<Integer> temp) {
        boolean result = false;
        for (int i = 0; i < temp.size(); i ++) {
            int cur = temp.get(i);
            if (cur % 2 == 0) {
                return true;
            }

        }
        return result;
    }
}
