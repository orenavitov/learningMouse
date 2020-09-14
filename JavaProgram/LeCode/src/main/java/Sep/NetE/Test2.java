package Sep.NetE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    public static void Solution() {
        int maxLenght = 0;
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int length = str.length();
        HashMap<Character, Integer> charachterAndCount = new HashMap<>();
        List<Character> characterList = Arrays.asList('a', 'b', 'c', 'x', 'y', 'z');
        for (Character c : characterList) {
            charachterAndCount.put(c, 0);
        }
        for (int i = 0; i < length; i ++) {
            resetMap(charachterAndCount);
            for (int j = i; j < length; j ++) {
                char c = str.charAt(j);
                if (characterList.contains(c)) {
                    int count = charachterAndCount.get(c);
                    charachterAndCount.put(c, count + 1);
                    if (check(charachterAndCount)) {
                        int tempLength = j - i + 1;
                        if (tempLength > maxLenght) {
                            maxLenght = tempLength;
                        }
                    }
                }
            }
        }
        System.out.println(maxLenght);
    }

    private static boolean check(HashMap<Character, Integer> charachterAndCount) {
        boolean result = true;
        for (Character c : charachterAndCount.keySet()) {
            int count = charachterAndCount.get(c);
            if (count % 2 == 1) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static void resetMap(HashMap<Character, Integer> charachterAndCount) {
        for (Character c : charachterAndCount.keySet()) {
            charachterAndCount.put(c, 0);
        }
    }

    public static void main(String[] args) {
        Solution();
    }
}
