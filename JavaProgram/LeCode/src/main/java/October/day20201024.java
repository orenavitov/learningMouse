package October;

public class day20201024 {
    public static int FindGreatestSumOfSubArray(int[] array) {
        int length = array.length;
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            int sum = 0;
            for (int j = i; j < length; j++) {
                sum = sum + array[j];
                result = Math.max(result, sum);
            }
        }
        return result;
    }

    public static int findKth(int[] a, int n, int K) {
        // write code here
        return dfs(a, K, 0, n - 1);
    }

    private static int dfs(int[] a, int K, int start, int end) {
        int tempStart = start;
        int tempEnd = end;
        int target = a[tempStart];
        while (tempStart < tempEnd) {
            if (a[tempEnd] > target) {
                a[tempStart] = a[tempEnd];
                tempStart++;
            } else {
                tempEnd--;
                continue;
            }
            while (tempStart < tempEnd) {
                if (a[tempStart] < target) {
                    a[tempEnd] = a[tempStart];
                    tempEnd--;
                    break;
                } else {
                    tempStart++;
                }
            }
        }
        if (tempStart == K - 1) {
            return target;
        } else {
            if (tempStart > K - 1) {
                return dfs(a, K, start, tempStart - 1);
            }
            if (tempStart < K - 1) {
                return dfs(a, K, tempStart + 1, end);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {663954, 529281, 348640, 613110, 294139, 1501791,
                677297, 973861, 233123, 688719, 1336422, 513275, 457801,
                460797, 194364, 996212, 1546617, 1964106, 1143995, 1938234,
                556211, 748578, 996235, 1755260, 230290, 1819017, 104171, 495676,
                740495, 1894423, 48370, 154755, 117533, 1175380, 1793371, 564934,

                966119, 1560347, 168959, 1659572, 375568, 1801070, 177386, 1748197, 1103505, 1271931, 408093, 1194755, 1265619, 1066084, 372610, 826176, 1745906, 993624, 168670, 1757298, 382955, 162498, 504874, 1567007, 1744814, 1583314, 1466021, 1633680, 1794482, 610820, 688946, 167435, 838339, 170057, 651409, 1550833, 1434024, 1294060, 441398, 1537385, 30565, 823593, 1836464, 652724, 1516025, 314633, 1961969, 941978, 1697553, 1617156, 951725, 1140526, 757379, 1888646, 743230, 1596735, 201447, 1123448, 347609, 804229, 1376064, 1415807, 66479, 967926, 1795616, 58026, 71199, 1450009, 815676, 1517541, 1548094, 1841906, 778020, 825331, 1950310, 1104908, 102170, 1885999, 1759085, 226606, 1386679, 1655428, 143044, 972708, 1412993, 670556, 1453614, 613177};


        System.out.println(findKth(array, 124, 37));
    }
}
