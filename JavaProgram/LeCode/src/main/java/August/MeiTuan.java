package August;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Scanner;

public class MeiTuan {

    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int lines = Integer.valueOf(firstLine);
        List<String> inputs = new ArrayList<>();
        for(int i = 0; i < lines; i ++) {
            String input = scanner.nextLine();
            inputs.add(input);
        }
        for(String input : inputs) {
            if (check(input)) {
                System.out.println("Accept");
            } else {
                System.out.println("Wrong");
            }
        }
    }

    private static boolean check(String s) {
        boolean result = true;
        boolean hasNum = false;
        int length = s.length();
        for (int i = 0; i < length; i ++) {
            char c = s.charAt(i);
            int ascallNum = (int)c;
            if (i == 0) {
                if (isLetter(ascallNum)) {
                    continue;
                } else {
                    return false;
                }
            }
            if (isNumber(ascallNum) || isLetter(ascallNum)) {
                if(isNumber(ascallNum)) {
                    hasNum = true;
                }
            } else {
                return false;
            }

        }
        if (!hasNum) {
            return false;
        }
        return result;
    }

    private static boolean isLetter(int ascallNum) {
        if ((ascallNum >= 65 && ascallNum <= 90) || (ascallNum >= 97 && ascallNum <= 122)) {
            return true;
        }
        return false;
    }

    private static boolean isNumber(int ascallNum) {
        if(ascallNum >= 48 && ascallNum <= 57) {
            return true;
        }
        return false;
    }


    private static class Task {
        int num;
        int h;
        int w;
        int earn;
        public Task(int num, int h, int w) {
            this.num = num;
            this.h = h;
            this.w = w;
            this.earn = this.h + this.w * 2;
        }

        public int getEarn() {
            return this.earn;
        }
    }

    private static void Solution2() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int m = Integer.valueOf(firstLineDetials[1]);
        Task[] tasks = new Task[n];
        for(int i = 0; i < n; i ++) {
            String istLine = scanner.nextLine();
            String[] istLineDetials = istLine.split(" ");
            int h = Integer.valueOf(istLineDetials[0]);
            int w = Integer.valueOf(istLineDetials[1]);
            Task task = new Task(i, h, w);
            tasks[i] = task;
        }

        Arrays.sort(tasks, (task1, task2) -> {
            int earn1 = task1.getEarn();
            int earn2 = task2.getEarn();
            if (earn1 != earn2) {
                return -(earn1 - earn2);
            } else {
                return task1.num - task2.num;
            }
        });
        Task[] result = new Task[m];
        for(int i = 0; i < m; i ++) {
            Task task = tasks[i];
            result[i] = task;
        }

        Arrays.sort(result, (task1, task2) -> {
            return task1.num - task2.num;
        });
        for(int i = 0; i < m; i ++) {
            Task task = result[i];
            if (i == m - 1) {
                System.out.print((task.num + 1));
            } else {
                System.out.print((task.num + 1) + " ");
            }
        }

    }

//    private static class Item {
//        int num;
//        int weight;
//
//        public Item(int num, int weight) {
//            this.num = num;
//            this.weight = weight;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if(obj instanceof Item) {
//                Item other = (Item) obj;
//                if (other.num == this.num) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        }
//    }

    private static void Solution3() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int n = Integer.valueOf(firstLine);
        String secondLine = scanner.nextLine();
        String[] weightsString = secondLine.split(" ");
        int[] weights = new int[n];
        for(int i = 0; i < n; i ++) {
            weights[i] = Integer.valueOf(weightsString[i]);
        }
        String thirdLine = scanner.nextLine();
        String[] pickNumsString = thirdLine.split(" ");
        int[] pickNums = new int[n];
        for(int i = 0; i < n; i ++) {
            pickNums[i] = Integer.valueOf(pickNumsString[i]);
        }
        List<Integer> items = new ArrayList<>();
        for(int i = 0; i < n; i ++) {

            items.add(weights[i]);

        }
        List<Integer> hadRemove = new ArrayList<>();
        for(int i = 0; i < n; i ++) {
            int pickNum = pickNums[i];
            int prePickNum = getPrePickCount(hadRemove, pickNum);
            int curIndex = pickNum - prePickNum - 1;
            int leftSum = getSum(items, 0, curIndex);
            int rightSum = getSum(items, curIndex + 1, items.size());
            System.out.println(Math.max(leftSum, rightSum));
            items.remove(curIndex);
            hadRemove.add(pickNum);
        }
    }

    private static int getPrePickCount(List<Integer> hadRemove, int pickNum) {
        int count = 0;
        for(int remove : hadRemove) {
            if(remove < pickNum) {
                count ++;
            }
        }
        return count;
    }

    private static int getSum(List<Integer> items, int startIndex, int endIndex) {
        int sum = 0;
        for(int i = startIndex; i < endIndex; i ++) {
            sum = sum + items.get(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution3();
    }
}
