package October.yongyou;

public class Test1 {
    public static String[] select_sort (String[] array) {
        // write code here
        for (int i = 0; i < array.length; i ++) {
            int minIndex = findTheMin(array, i, array.length);
            String temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        return array;
    }

    private static int findTheMin(String[] array, int start, int end) {
        int minIndex = start;
        String minString = array[start];
        for (int i = start + 1; i < end; i ++) {
            if (isBiggerThan(minString, array[i])) {
                minIndex = i;
                minString = array[i];
            }
        }
        return minIndex;
    }

    private static boolean isBiggerThan(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        for (int i = 0; i < length; i ++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (c1 < c2) {
                return false;
            }
            if (c1 > c2) {
                return true;
            }
        }
        if (str2.length() > str1.length()) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        String[] strings = {"nice","try","duck","zoo"};
        String[] orderedString = select_sort(strings);
        for(String str : orderedString) {
            System.out.print(str + " ");
        }
    }
}
