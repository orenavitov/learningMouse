package Sep.souGou;

public class Test1 {


    public static class Interval {
        int start;
        int end;
    }

    public static Interval solve(int n, int k, String str1, String str2) {
        int sameCount = 0;
        int differnenceCount = 0;
        for (int i = 0; i < n; i ++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (c1 == c2) {
                sameCount ++;
            } else {
                differnenceCount ++;
            }
        }
        int max = 0;
        if (k <= sameCount) {
            max = k + differnenceCount;
        }
        if (k > sameCount) {
            max = (differnenceCount - (k - sameCount)) + sameCount;
        }

        int min = 0;
        if (k <= differnenceCount) {
            min = 0;
        }
        if (k > differnenceCount) {
            min = k - differnenceCount;
        }
        Interval interval = new Interval();
        interval.start = min;
        interval.end = max;
        return interval;
    }

    public static void main(String[] args) {
        Interval interval1 = solve(3, 3, "ABC", "ABC");
        System.out.print(interval1.start + ", " + interval1.end);

    }
}
