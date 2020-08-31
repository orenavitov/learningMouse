package August;

import java.util.*;

public class day20200807 {
    /**
     * 我叫王大锤，是一家出版社的编辑。我负责校对投稿来的英文稿件，这份工作非常烦人，因为每天都要去修正无数的拼写错误。但是，优秀的人总能在平凡的工作中发现真理。我发现一个发现拼写错误的捷径：
     *
     * 1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
     * 2. 两对一样的字母（AABB型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
     * 3. 上面的规则优先“从左到右”匹配，即如果是AABBCC，虽然AABB和BBCC都是错误拼写，应该优先考虑修复AABB，结果为AABCC
     *
     * 我特喵是个天才！我在蓝翔学过挖掘机和程序设计，按照这个原理写了一个自动校对器，工作效率从此起飞。用不了多久，我就会出任CEO，当上董事长，迎娶白富美，走上人生巅峰，想想都有点小激动呢！
     * ……
     * 万万没想到，我被开除了，临走时老板对我说： “做人做事要兢兢业业、勤勤恳恳、本本分分，人要是行，干一行行一行。一行行行行行；要是不行，干一行不行一行，一行不行行行不行。” 我现在整个人红红火火恍恍惚惚的……
     *
     * 请听题：请实现大锤的自动校对程序
     *
     * 输入描述:
     * 第一行包括一个数字N，表示本次用例包括多少个待校验的字符串。
     *
     * 后面跟随N行，每行为一个待校验的字符串。
     *
     * 输出描述:
     * N行，每行包括一个被修复后的字符串。
     *
     * 输入例子1:
     * 2
     * helloo
     * wooooooow
     *
     * 输出例子1:
     * hello
     * woow
     */

    private static String modifyer(String s) {


        boolean repeatTwice = false;
        int length = s.length();
        for (int i = 0; i < length - 1; ) {
            int repeatTime = 0;
            char currentChar = s.charAt(i);
            for(int j = i + 1; j < length; j ++) {
                char followChar = s.charAt(j);
                if (followChar != currentChar) {
                    repeatTwice = false;
                    i ++;
                    break;
                } else {
                    repeatTime ++;
                    if (repeatTime == 2) {
                        if (repeatTwice) {
                            s.replace(followChar, (char) 0);
                        }
                        repeatTwice = true;
                        currentChar = followChar;
                        continue;
                    }
                    if (repeatTime == 3) {
                        s.replace(followChar, (char) 0);
                        continue;
                    }
                    i ++;
                }
            }
        }

        return s;
    }

    /**
     * 我叫王大锤，是一名特工。我刚刚接到任务：在字节跳动大街进行埋伏，抓捕恐怖分子孔连顺。和我一起行动的还有另外两名特工，我提议
     *
     * 1. 我们在字节跳动大街的N个建筑中选定3个埋伏地点。
     * 2. 为了相互照应，我们决定相距最远的两名特工间的距离不超过D。
     *
     * 我特喵是个天才! 经过精密的计算，我们从X种可行的埋伏方案中选择了一种。这个方案万无一失，颤抖吧，孔连顺！
     * ……
     * 万万没想到，计划还是失败了，孔连顺化妆成小龙女，混在cosplay的队伍中逃出了字节跳动大街。只怪他的伪装太成功了，就是杨过本人来了也发现不了的！
     *
     * 请听题：给定N（可选作为埋伏点的建筑物数）、D（相距最远的两名特工间的距离的最大值）以及可选建筑的坐标，计算在这次行动中，大锤的小队有多少种埋伏选择。
     * 注意：
     * 1. 两个特工不能埋伏在同一地点
     * 2. 三个特工是等价的：即同样的位置组合(A, B, C) 只算一种埋伏方法，不能因“特工之间互换位置”而重复使用
     *
     *
     * 输入描述:
     * 第一行包含空格分隔的两个数字 N和D(1 ≤ N ≤ 1000000; 1 ≤ D ≤ 1000000)
     *
     * 第二行包含N个建筑物的的位置，每个位置用一个整数（取值区间为[0, 1000000]）表示，从小到大排列（将字节跳动大街看做一条数轴）
     *
     * 输出描述:
     * 一个数字，表示不同埋伏方案的数量。结果可能溢出，请对 99997867 取模
     *
     * 输入例子1:
     * 4 3
     * 1 2 3 4
     *
     * 输出例子1:
     * 4
     *
     * 例子说明1:
     * 可选方案 (1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)
     *
     * 输入例子2:
     * 5 19
     * 1 10 20 30 50
     *
     * 输出例子2:
     * 1
     *
     * 例子说明2:
     * 可选方案 (1, 10, 20)
     * @param args
     */

    private static int solution1() {
        int result = 0;
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String secondLine = scanner.nextLine();
        String[] firstLineStrings = firstLine.split(" ");
        int N = Integer.valueOf(firstLineStrings[0]);
        int maxDistance = Integer.valueOf(firstLineStrings[1]);
        String[] secondLineStrings = secondLine.split(" ");
        List<String> possiblePosition = new ArrayList<>();
        for (int i = 0; i < secondLineStrings.length - 1; i ++) {
            int startPosition = Integer.valueOf(secondLineStrings[i]);
            int maxDistancePosition = startPosition + maxDistance;
            for (int j = i + 1; j < secondLineStrings.length; j ++) {
                if (Integer.valueOf(secondLineStrings[j]) <= maxDistancePosition) {
                    possiblePosition.add(secondLineStrings[j]);
                } else {
                    break;
                }
            }
            int possiblePositionNum = possiblePosition.size();
            if (possiblePositionNum < 2) {
                return result;
            } else {
                result = result + possiblePositionNum * (possiblePositionNum - 1) / 2;
            }
            possiblePosition.clear();
        }
        return result;
    }

    /**
     * 小明是一名算法工程师，同时也是一名铲屎官。某天，他突发奇想，想从猫咪的视频里挖掘一些猫咪的运动信息。为了提取运动信息，他需要从视频的每一帧提取“猫咪特征”。一个猫咪特征是一个两维的vector<x, y>。如果x_1=x_2 and y_1=y_2，那么这俩是同一个特征。
     *        因此，如果喵咪特征连续一致，可以认为喵咪在运动。也就是说，如果特征<a, b>在持续帧里出现，那么它将构成特征运动。比如，特征<a, b>在第2/3/4/7/8帧出现，那么该特征将形成两个特征运动2-3-4 和7-8。
     * 现在，给定每一帧的特征，特征的数量可能不一样。小明期望能找到最长的特征运动。
     *
     * 输入描述:
     * 第一行包含一个正整数N，代表测试用例的个数。
     *
     * 每个测试用例的第一行包含一个正整数M，代表视频的帧数。
     *
     * 接下来的M行，每行代表一帧。其中，第一个数字是该帧的特征个数，接下来的数字是在特征的取值；比如样例输入第三行里，2代表该帧有两个猫咪特征，<1，1>和<2，2>
     * 所有用例的输入特征总数和<100000
     *
     * N满足1≤N≤100000，M满足1≤M≤10000，一帧的特征个数满足 ≤ 10000。
     * 特征取值均为非负整数。
     *
     * 输出描述:
     * 对每一个测试用例，输出特征运动的长度作为一行
     *
     * 输入例子1:
     * 1
     * 8
     * 2 1 1 2 2
     * 2 1 1 1 4
     * 2 1 1 2 2
     * 2 2 2 1 4
     * 0
     * 0
     * 1 1 1
     * 1 1 1
     *
     * 输出例子1:
     * 3
     *
     * 例子说明1:
     * 特征<1,1>在连续的帧中连续出现3次，相比其他特征连续出现的次数大，所以输出
     * @param args
     */
    private static void solution2() {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.valueOf(scanner.nextLine());
        HashMap<Vector, List<Integer>> vectorListHashMap = new HashMap<>();
        for (int i = 0; i < N; i ++) {

            int M = Integer.valueOf(scanner.nextLine());
            for (int j = 0; j < M; j ++){
                String line = scanner.nextLine();
                String[] lineDetial = line.split(" ");
                int vectorsNum = Integer.valueOf(lineDetial[0]);
                if (vectorsNum == 0) {
                    continue;
                }
                for (int k = 0, v = 1; k < vectorsNum; k ++) {
                    int x = Integer.valueOf(lineDetial[v]);
                    int y = Integer.valueOf(lineDetial[v + 1]);
                    Vector vector = new Vector(x, y);
                    if (!vectorListHashMap.containsKey(vector)) {
                        vectorListHashMap.put(vector, new ArrayList<>());
                    }
                    vectorListHashMap.get(vector).add(j);

                    v = v + 2;
                }
            }
            mostContinueVector(vectorListHashMap);
            vectorListHashMap.clear();
        }

    }

    private static void mostContinueVector(HashMap<Vector, List<Integer>> vectorListHashMap) {
        int mostContinueVectorLength = 1;

        Set<Vector> vectors = vectorListHashMap.keySet();
        for(Vector vector : vectors) {
            int currentContinueVectorLength = 1;
            List<Integer> stamps = vectorListHashMap.get(vector);

            for(int i = 0; i < stamps.size() - 1; i ++) {
                int preStamp = stamps.get(i);
                if (stamps.get(i + 1) == preStamp + 1) {
                    currentContinueVectorLength ++;
                    if (currentContinueVectorLength > mostContinueVectorLength) {
                        mostContinueVectorLength = currentContinueVectorLength;
                    }
                } else {
                    currentContinueVectorLength = 1;
                }
            }
        }
        System.out.println(mostContinueVectorLength);
    }

    private static class Vector {
        int x;
        int y;

        public Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }



        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object obj) {
            Vector other = (Vector) obj;
            if (other.getX() == this.x && other.getY() == this.y) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        solution2();
    }
}
