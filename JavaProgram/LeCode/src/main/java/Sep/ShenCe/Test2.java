package Sep.ShenCe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    public static List<String> Solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<String> results = new ArrayList<>();
        LinkedList<Character> lefts = new LinkedList<>();
        for (int i = 0; i < input.length(); i ++) {
            lefts.addLast(input.charAt(i));
        }
        dfs(0, input.length(), lefts, "", results);
        return results;
    }

    private static void dfs(int curIndex, int length, List<Character> lefts,
                            String temp, List<String> results) {
        if (curIndex == length) {
            results.add(temp);
            return;
        }
        for (int i = 0; i < lefts.size(); i ++) {
            Character c = lefts.remove(i);
            dfs(curIndex + 1, length, lefts, temp + c, results);
            lefts.add(i, c);
        }
    }

    public static void main(String[] args) {
        List<String> results = Solution();
        System.out.print("[");
        for (int i = 0; i < results.size(); i ++) {
            if (i == results.size() - 1) {
                System.out.print("'" + results.get(i) + "'");
            } else {
                System.out.print("'" + results.get(i) + "'" + ", ");
            }

        }
        System.out.print("]");
//        results.forEach(result -> {
//            System.out.println(result);
//        });
//        System.out.println(results.toString());
    }
}
