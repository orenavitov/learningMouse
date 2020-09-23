package Sep.Test58;

public class Test2 {
    public static int[] countBits (int num) {
        // write code here
        int[] results = new int[num + 1];
        if (num == 0) {
            results[0] = 0;
            return results;
        }
        if (num == 1) {
            results[0] = 0;
            results[1] = 1;
            return results;
        }
        if (num > 1) {
            results[0] = 0;
            results[1] = 1;

            int e = 1;
            int start = 2;
            while (start <= num) {
//                start = (int)Math.pow(2, e);
                while (start < Math.pow(2, e + 1) && start <= num) {
                    results[start] = results[start - (int)Math.pow(2, e)] + 1;
                    start ++;
                }
                e ++;
            }

        }
        return results;
    }

    public static void main(String[] args) {
        int num = 12;
        int[] results = countBits (num);
        for (int i = 0; i < results.length; i ++) {
            System.out.print(results[i] + " ");
        }
    }
}
