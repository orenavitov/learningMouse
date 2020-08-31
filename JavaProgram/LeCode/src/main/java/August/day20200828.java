package August;

import java.util.Stack;

public class day20200828 {
    /**
     * 在二维平面上，有一个机器人从原点 (0, 0) 开始。给出它的移动顺序，判断这个机器人在完成移动后是否在 (0, 0) 处结束。
     *
     * 移动顺序由字符串表示。字符 move[i] 表示其第 i 次移动。机器人的有效动作有 R（右），L（左），U（上）和 D（下）。如果机器人在完成所有动作后返回原点，则返回 true。否则，返回 false。
     *
     * 注意：机器人“面朝”的方向无关紧要。 “R” 将始终使机器人向右移动一次，“L” 将始终向左移动等。此外，假设每次移动机器人的移动幅度相同。
     *
     *  
     *
     * 示例 1:
     *
     * 输入: "UD"
     * 输出: true
     * 解释：机器人向上移动一次，然后向下移动一次。所有动作都具有相同的幅度，因此它最终回到它开始的原点。因此，我们返回 true。
     * 示例 2:
     *
     * 输入: "LL"
     * 输出: false
     * 解释：机器人向左移动两次。它最终位于原点的左侧，距原点有两次 “移动” 的距离。我们返回 false，因为它在移动结束时没有返回原点。
     *
     * 链接：https://leetcode-cn.com/problems/robot-return-to-origin
     * @param moves
     * @return
     */
    public static boolean judgeCircle(String moves) {
        int result = 0;
        int length = moves.length();
        for(int i = 0; i < length; i ++) {
            char c = moves.charAt(i);
            if (c == 'U') {
                result = result + 1;
                continue;
            }
            if (c == 'D') {
                result = result - 1;
                continue;
            }
            if (c == 'L') {
                result = result + 2;
                continue;
            }
            if (c == 'R') {
                result = result - 2;

            }
        }
        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 公司有编号为 1到 n的 n个工程师，给你两个数组 speed和 efficiency，其中 speed[i]和 efficiency[i]分别代表第 i位工程师的速度和效率。请你返回由最多k个工程师组成的最大团队表现值，由于答案可能很大，请你返回结果对 10^9 + 7 取余后的结果。
     *
     * 团队表现值的定义为：一个团队中「所有工程师速度的和」乘以他们「效率值中的最小值」。
     *
     *
     *
     * 示例 1：
     *
     * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
     * 输出：60
     * 解释：
     * 我们选择工程师 2（speed=10 且 efficiency=4）和工程师 5（speed=5 且 efficiency=7）。他们的团队表现值为 performance = (10 + 5) * min(4, 7) = 60 。
     * 示例 2：
     *
     * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
     * 输出：68
     * 解释：
     * 此示例与第一个示例相同，除了 k = 3 。我们可以选择工程师 1 ，工程师 2 和工程师 5 得到最大的团队表现值。表现值为 performance = (2 + 10 + 5) * min(5, 4, 7) = 68 。
     * 示例 3：
     *
     * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 4
     * 输出：72
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-performance-of-a-team
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param
     */
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        return 0;
    }
    public static void main(String[] args) {
        System.out.println(judgeCircle("UD"));
    }
}
