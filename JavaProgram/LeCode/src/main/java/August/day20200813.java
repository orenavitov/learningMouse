package August;

public class day20200813 {
    /**
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     *
     * 示例 1:
     *
     * 输入: num1 = "2", num2 = "3"
     * 输出: "6"
     * 示例 2:
     *
     * 输入: num1 = "123", num2 = "456"
     * 输出: "56088"
     * 说明：
     *
     * num1 和 num2 的长度小于110。
     * num1 和 num2 只包含数字 0-9。
     * num1 和 num2 均不以零开头，除非是数字 0 本身。
     * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/multiply-strings
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static String multiply(String num1, String num2) {

        int length1 = num1.length();
        int length2 = num2.length();

        String preResult = "0";
        for (int j = length2 - 1; j >= 0; j --) {
            int increaseNum = 0;
            StringBuilder tempMultResult = new StringBuilder();
            int subNum2 = num2.charAt(j) - 48;
            if (subNum2 == 0) {
                continue;
            }
            for (int i = length1 - 1; i >= 0; i --) {

                int subNum1 = num1.charAt(i) - 48;

                int currentIndexNum = (subNum1 * subNum2) % 10 + increaseNum;
                if (currentIndexNum >= 10) {
                    currentIndexNum = currentIndexNum - 10;
                    increaseNum = subNum1 * subNum2 / 10 + 1;
                } else {
                    increaseNum = subNum1 * subNum2 / 10;
                }

                tempMultResult.append(currentIndexNum);

            }
            if (increaseNum != 0) {
                tempMultResult.append(increaseNum);
            }
            tempMultResult = tempMultResult.reverse();
            for (int k = 0; k < length2 - 1 - j; k ++) {
                tempMultResult.append(0);
            }
            int preLength = preResult.length();
            int currentLength = tempMultResult.length();
            String longer = preLength >= currentLength ? preResult : tempMultResult.toString();
            String shorter = preLength < currentLength ? preResult : tempMultResult.toString();
            boolean needIncrease = false;
            StringBuilder tempSumResult = new StringBuilder();
            for (int i = 0; i < shorter.length(); i ++) {
                int subPreResultNum = preResult.charAt(shorter.length() - 1 - i) - 48;
                int subCurResultNum = tempMultResult.charAt(longer.length() - 1 - i) - 48;
                int tempSum = 0;
                if (needIncrease) {
                    tempSum = subCurResultNum + subPreResultNum + 1;
                } else {
                    tempSum = subCurResultNum + subPreResultNum;
                }
                if (tempSum >= 10) {
                    tempSumResult.append(tempSum - 10);
                    needIncrease = true;
                } else {
                    tempSumResult.append(tempSum);
                    needIncrease = false;
                }
                if (i == shorter.length() - 1) {
                    for (int k = i + 1; k < longer.length(); k ++) {
                        int subLongerNum = longer.charAt(longer.length() - 1 - k) - 48;
                        int newTempSum = 0;
                        if (needIncrease) {
                            newTempSum = subLongerNum + 1;
                        } else {
                            newTempSum = subLongerNum;
                        }
                        if (newTempSum >= 10) {
                            tempSumResult.append(newTempSum - 10);
                            needIncrease = true;
                        } else {
                            tempSumResult.append(newTempSum);
                            needIncrease = false;
                        }

                    }
                }

            }
            if (j == 0) {
                if (needIncrease) {
                    tempSumResult.append(1);
                }
                preResult = tempSumResult.reverse().toString();
                return preResult;
            }
            preResult = tempSumResult.reverse().toString();

        }

        return preResult.toString();
    }

    public static void main(String[] args) {
        System.out.println(multiply("999", "0"));
    }
}
