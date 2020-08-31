package August;

import java.util.Map;
import java.util.Scanner;

public class day20200802 {
    /**
     * Z国的货币系统包含面值1元、4元、16元、64元共计4种硬币，以及面值1024元的纸币。现在小Y使用1024元的纸币购买了一件价值为的商品，请问最少他会收到多少硬币？
     *
     * 输入描述:
     * 一行，包含一个数N。
     *
     * 输出描述:
     * 一行，包含一个数，表示最少收到的硬币数。
     *
     * 输入例子1:
     * 200
     *
     * 输出例子1:
     * 17
     *
     * 例子说明1:
     * 花200，需要找零824块，找12个64元硬币，3个16元硬币，2个4元硬币即可。
     */
    private static void Solution1() {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.valueOf(scanner.nextLine());
        int target = 1024 - N;
        if (target % 1024 == 0) {
            System.out.println(target / 1024);
            return;
        }
        int[] results = new int[target + 1];
        results[0] = 0;
        for (int i = 1; i < target + 1; i ++) {
            if (i / 1024 >= 1) {
                results[i] = results[i - 1024] + 1;
                continue;
            }

            if (i / 64 >= 1) {
                results[i] = results[i - 64] + 1;
                continue;
            }
            if (i / 16 >= 1) {
                results[i] = results[i - 16] + 1;
                continue;
            }
            if (i / 4 >= 1) {
                results[i] = results[i - 4] + 1;
                continue;
            }
            results[i] = results[i - 1] + 1;
        }
        System.out.println(results[target]);
    }

    /**
     * 机器人正在玩一个古老的基于DOS的游戏。游戏中有N+1座建筑——从0到N编号，从左到右排列。编号为0的建筑高度为0个单位，编号为i的建筑的高度为H(i)个单位。
     *
     * 起初， 机器人在编号为0的建筑处。每一步，它跳到下一个（右边）建筑。假设机器人在第k个建筑，且它现在的能量值是E, 下一步它将跳到第个k+1建筑。它将会得到或者失去正比于与H(k+1)与E之差的能量。如果 H(k+1) > E 那么机器人就失去 H(k+1) - E 的能量值，否则它将得到 E - H(k+1) 的能量值。
     *
     * 游戏目标是到达第个N建筑，在这个过程中，能量值不能为负数个单位。现在的问题是机器人以多少能量值开始游戏，才可以保证成功完成游戏？
     *
     * 输入描述:
     * 第一行输入，表示一共有 N 组数据.
     *
     * 第二个是 N 个空格分隔的整数，H1, H2, H3, ..., Hn 代表建筑物的高度
     *
     * 输出描述:
     * 输出一个单独的数表示完成游戏所需的最少单位的初始能量
     *
     * 输入例子1:
     * 5
     * 3 4 3 2 4
     *
     * 输出例子1:
     * 4
     *
     * 输入例子2:
     * 3
     * 4 4 4
     *
     * 输出例子2:
     * 4
     *
     * 输入例子3:
     * 3
     * 1 6 4
     *
     * 输出例子3:
     * 3
     * @param
     */
    private static void Solution2() {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.valueOf(scanner.nextLine());
        String buildingHeights = scanner.nextLine();
        String[] buildingHeightsStrings = buildingHeights.split(" ");
        int[] builingHeightsIntegers = new int[N];
        for(int i = 0; i < N; i ++) {
            builingHeightsIntegers[i] = Integer.valueOf(buildingHeightsStrings[i]);
        }
        int endPower = 0;
        for(int i = N - 1; i >= 0; i --) {
            float increase = ((float) builingHeightsIntegers[i] - (float) endPower) / 2;
            int preMinPower = (int) Math.ceil(endPower + increase);
            endPower = preMinPower;
        }
        System.out.println(endPower);
    }

    /**
     * 小明目前在做一份毕业旅行的规划。打算从北京出发，分别去若干个城市，然后再回到北京，每个城市之间均乘坐高铁，且每个城市只去一次。由于经费有限，希望能够通过合理的路线安排尽可能的省一些路上的花销。给定一组城市和每对城市之间的火车票的价钱，找到每个城市只访问一次并返回起点的最小车费花销。
     *
     * 输入描述:
     * 城市个数n（1<n≤20，包括北京）
     *
     * 城市间的车票价钱 n行n列的矩阵 m[n][n]
     *
     * 输出描述:
     * 最小车费花销 s
     *
     * 输入例子1:
     * 4
     * 0 2 6 5
     * 2 0 4 4
     * 6 4 0 2
     * 5 4 2 0
     *
     * 输出例子1:
     * 13
     *
     * 例子说明1:
     * 共 4 个城市，城市 1 和城市 1 的车费为0，城市 1 和城市 2 之间的车费为 2，城市 1 和城市 3 之间的车费为 6，城市 1 和城市 4 之间的车费为 5，依次类推。假设任意两个城市之间均有单程票可购买，且票价在1000元以内，无需考虑极端情况。
     * @param args
     */

    public static void main(String[] args) {
        Solution2();
    }
}
