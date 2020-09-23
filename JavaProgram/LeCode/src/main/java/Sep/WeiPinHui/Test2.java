package Sep.WeiPinHui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int length = input.length();
        int start = 0;
        List<Character> appeared = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        int maxLenght = 0;
        while (start < length) {
            char c = input.charAt(start);
            if (appeared.contains(c)) {
                if (appeared.size() > maxLenght) {
                    maxLenght = appeared.size();
                }
                int repeatIndex = appeared.lastIndexOf(c);
                indexes = indexes.subList(repeatIndex + 1, indexes.size());
                appeared = appeared.subList(repeatIndex + 1, appeared.size());
            }
            appeared.add(c);
            indexes.add(start);
            start ++;
        }

        System.out.println(maxLenght);
    }

    public static void main(String[] args) {
        Solution();
    }


//    private static void Solution() {
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
////        List<Character> result = null;
//        int length = input.length();
//        int start = 0;
//        List<Character> appeared = new ArrayList<>();
//        List<Integer> indexes = new ArrayList<>();
//        int maxLenght = 0;
//        while (start < length) {
//            char c = input.charAt(start);
//            if (!appeared.contains(c)) {
//                appeared.add(c);
//                indexes.add(start);
//                start ++;
//            } else {
//                if (appeared.size() > maxLenght) {
////                    result = new ArrayList<>(appeared);
//                    maxLenght = appeared.size();
//                }
//                int repeatIndex = appeared.lastIndexOf(c);
//                indexes = indexes.subList(repeatIndex + 1, indexes.size());
//                appeared = appeared.subList(repeatIndex + 1, appeared.size());
//                indexes.add(start);
//                appeared.add(c);
////                start = appeared.get(repeatIndex + 1);
//                start ++;
//            }
//        }
////        StringBuilder output = new StringBuilder();
////        for(Character c : result) {
////            output.append(c);
////        }
//        System.out.println(maxLenght);
//    }
//
//    public static void main(String[] args) {
//        Solution();
//    }
}
