package October.QiAnXin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Test2 {

    private static void getJCResult() {
        Scanner scanner = new Scanner(System.in);
        String N = scanner.nextLine();
        int _N = Integer.valueOf(N);
        String pre = "" +(_N -2);
        String temp = mut("" + (_N - 1), "" + _N);
        _N = _N - 2;
        while (_N >= 1) {
            temp = mut(temp, pre);
            _N = _N - 1;
            pre = "" + (_N);
        }

        System.out.println(temp);
    }


    private static String mut(String a, String b) {
        String pre = "0";
        for (int i = b.length() - 1; i >= 0; i --) {
            char c = b.charAt(i);
            String tempResult = subMut(a, c, (b.length() - 1 - i));
            pre = add(pre, tempResult);
        }
        return pre;
    }

    private static String add(String a, String b) {
        int lengthA = a.length();
        int lengthB = b.length();
        StringBuilder result = new StringBuilder();
        int add = 0;
        for (int i = lengthA - 1, j = lengthB - 1; i >= 0 && j >= 0; i --, j --) {
            char _a = a.charAt(i);
            char _b = b.charAt(j);
            int __a = _a - 48;
            int __b = _b - 48;
            int temp = __a + __b + add;
            if (temp >= 10) {
                add = 1;
                temp = temp - 10;
            } else {
                add = 0;
            }
            result.append(temp);
        }
        if (lengthA > lengthB) {
            for (int i = lengthA - lengthB - 1; i >= 0; i --) {
                char _a = a.charAt(i);
                int __a = _a - 48;
                int temp = __a + add;
                if (temp >= 10) {
                    add = 1;
                    temp = temp - 10;
                } else {
                    add = 0;
                }
                result.append(temp);
            }
            if (add == 1) {
                result.append(1);
            }
        }

        if (lengthA < lengthB) {
            for (int i = lengthB - lengthA - 1; i >= 0; i --) {
                char _b = b.charAt(i);
                int __b = _b - 48;
                int temp = __b + add;
                if (temp >= 10) {
                    add = 1;
                    temp = temp - 10;
                } else {
                    add = 0;
                }
                result.append(temp);
            }
            if (add == 1) {
                result.append(1);
            }
        }
        if (lengthA == lengthB) {
            if (add == 1) {
                result.append(1);
            }
        }
        result = result.reverse();
        return result.toString();
    }

    private static String subMut(String a, char b, int l) {
        int add = 0;
        StringBuilder result = new StringBuilder();
        int _b = b - 48;
        for (int i = a.length() - 1; i >= 0; i --) {
            char t = a.charAt(i);
            int _t = t - 48;
            int temp = _b * _t;
            int temp1 = temp / 10;
            int temp2 = temp % 10 + add;
            if (temp2 >= 10) {
                temp1 = temp1 + 1;
                temp2 = temp2 - 10;
            }
            add = temp1;
            result.append(temp2);
        }
        if (add > 0) {
            result.append(add);
        }
        result = result.reverse();
        for (int i = 0; i < l; i ++) {
            result.append("0");
        }
        return result.toString();
    }

    public static void main(String[] args) {

        getJCResult();
    }
}
