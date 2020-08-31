package August;

public class SXF {
    /**
     * 输入 str1: “abcde", str2: "bcedaf"
     * 输出 "f"
     * @param str1
     * @param str2
     * @return
     */
    public static String find_diff_char (String str1, String str2) {
        // write code here
        int length1 = str1.length();
        int length2 = str2.length();
        int result1 = 0;
        int result2 = 0;
        for (int i = 0; i < length1; i ++) {
            result1 = str1.charAt(i) + result1;
        }
        for (int i = 0; i < length2; i ++) {
            result2 = str2.charAt(i) + result2;
        }
        int result = 0;
        if (result2 > result1) {
            result = result2 - result1;
        } else {
            result = result1 - result2;
        }
        return "" + (char) result;
    }



    public static void main(String[] args) {
        String str1 = "abbbcdf";
        String str2 = "abfcbbde";
        System.out.println(find_diff_char(str1, str2));
    }
}
