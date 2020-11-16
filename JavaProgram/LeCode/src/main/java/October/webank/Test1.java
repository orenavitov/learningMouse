package October.webank;

import java.util.*;

public class Test1 {
    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int q = Integer.valueOf(firstLineDetials[1]);
        int[] numbers = new int[n];
//        Set<Integer> numbers = new HashSet<>();
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        for (int i = 0; i < n; i ++) {
           numbers[i] = Integer.valueOf(secondLineDetials[i]);
        }
        Arrays.sort(numbers);
        List<Integer> inputs = new ArrayList<>();
        for(int i = 0; i < q; i ++) {
            inputs.add(Integer.valueOf(scanner.nextLine()));
        }

        for (Integer input : inputs) {
            int result = find(numbers, input);
            System.out.println(result);
        }
    }

    private static int find(int[] numbers, int input) {
//        int result = 0;
//        int different = Integer.MAX_VALUE;
        if (input <= numbers[0]) {
            return numbers[0];
        }
        if (input >= numbers[numbers.length - 1]) {
            return numbers[numbers.length - 1];
        }
        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
            int middle = (start + end) / 2;
            int middleNum = numbers[middle];
            if (middleNum == input) {
                return middleNum;
            } else {
                if (input > middleNum) {
                    start = middle + 1;
                }
                if (input < middleNum) {
                    end = middle - 1;
                }
            }
        }

        int num1 = numbers[start];
        int num2 = 0;
        if (input > num1) {
            if (start + 1 > numbers.length - 1) {
                return numbers[numbers.length - 1];
            }
            num2 = numbers[start + 1];
        }
        if (input < num1) {
            if (start - 1 < 0) {
                return numbers[0];
            }
            num2 = numbers[start - 1];
        }

        if (Math.abs(num1 - input) < Math.abs(num2 - input)) {
            return num1;
        }
        if (Math.abs(num1 - input) > Math.abs(num2 - input)) {
            return num2;
        }
        return Math.min(num1, num2);
    }

    public static void main(String[] args) {
        solution();
    }
}
