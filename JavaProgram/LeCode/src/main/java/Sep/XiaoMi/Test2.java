package Sep.XiaoMi;

import java.util.HashSet;
import java.util.Scanner;

public class Test2 {

    private static void Solution() {
        HashSet<Character> set = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i ++) {
            char cur = input.charAt(i);
            if (!set.contains(cur)) {
                set.add(cur);
                result.append(cur);
            }
        }
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        Solution();
    }
}
