package October;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class day20201026 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static boolean backspaceCompare(String S, String T) {


        return false;
    }

    public static void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode middleNode = findMiddleNode(head);
        if (middleNode == null) {
            return;
        }
        ListNode reversedList = reverseList(middleNode);
        head = mergeTwoList(head, reversedList);
        ListNode start = head;
        while (start != null) {
            System.out.print(start.val + " ");
            start = start.next;
        }
    }

    public static ListNode findMiddleNode(ListNode head) {
        ListNode faster = head;
        ListNode slower = head;
        ListNode pre = slower;
        while (faster != null && slower != null) {
            pre = slower;
            slower = slower.next;
            faster = faster.next;
            if (faster == null) {
                pre.next = null;
                return slower;
            } else {
                faster = faster.next;
            }
        }
        pre.next = null;
        return slower;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode curNode = head;
        ListNode nextNode = curNode.next;
        curNode.next = null;
        while (nextNode != null) {
            ListNode tempNextNode = nextNode.next;
            nextNode.next = curNode;
            curNode = nextNode;
            nextNode = tempNextNode;
        }
        return curNode;
    }

    private static ListNode mergeTwoList(ListNode head1, ListNode head2) {
        ListNode start1 = head1;
        ListNode start2 = head2;
        while (start1 != null && start2 != null) {
            ListNode temp1 = start1.next;
            ListNode temp2 = start2.next;
            start1.next = start2;
            start2.next = temp1;
            start1 = temp1;
            start2 = temp2;
        }
        return head1;
    }

    public static boolean isLongPressedName(String name, String typed) {
        Stack<Character> stack = new Stack<>();
        int i = 0;
        int j = 0;
        while (i < name.length() && j < typed.length()) {
            char c1 = name.charAt(i);
            char c2 = typed.charAt(j);
            if (c1 == c2) {
                stack.push(c1);
                i ++;
                j ++;
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.peek();
                if (top == c2) {
                    j ++;
                } else {
                    return false;
                }
            }
        }
        if (i < name.length()) {
            return false;
        }
        if (j < typed.length()) {
            if (stack.isEmpty()) {
                return false;
            } else {
                char top = stack.peek();
                while (j < typed.length()) {
                    char c = typed.charAt(j);
                    if (c != top) {
                        return false;
                    }
                    j ++;
                }
            }
        }
        return true;
    }

    /**
     * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。返回一个表示每个字符串片段的长度的列表。
     * 示例：
     *
     * 输入：S = "ababcbacadefegdehijhklij"
     * 输出：[9,7,8]
     * 解释：
     * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
     * 每个字母最多出现在一个片段中。
     * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/partition-labels
     * @param S
     * @return
     */
    public static List<Integer> partitionLabels(String S) {
        List<ArrayList<Character>> results = new ArrayList<>();
        char c = S.charAt(0);
        ArrayList<Character> chars = new ArrayList<>();
        chars.add(c);
        results.add(chars);
        for (int i = 1; i < S.length(); i ++) {
            char curC = S.charAt(i);
            int start = 0;
            while (start < results.size()) {
                ArrayList<Character> result = results.get(start);
                if (result.contains(curC)) {
                    break;
                }
                start ++;
            }
            mergetOrExtendList(results, start, curC);
        }
        List<Integer> resultsCount = new ArrayList<>();
        for (ArrayList<Character> hashSet : results) {
            resultsCount.add(hashSet.size());
        }
        return resultsCount;
    }

    private static void mergetOrExtendList(List<ArrayList<Character>> results, int index, char c) {
        if (index == results.size()) {
            ArrayList<Character> newHashSet = new ArrayList<>();
            newHashSet.add(c);
            results.add(newHashSet);
            return;
        }
        int removeTime = results.size() - 1 - index;
        ArrayList<Character> result = results.get(index);
        for (int i = index + 1; i < results.size(); i ++) {
            ArrayList<Character> temp = results.get(i);
            result.addAll(temp);
        }
        result.add(c);
        for(int i = 0; i < removeTime; i ++) {
            results.remove(results.size() - 1);
        }
    }

    /**
     * 请判断一个链表是否为回文链表。
     *
     * 示例 1:
     *
     * 输入: 1->2
     * 输出: false
     * 示例 2:
     *
     * 输入: 1->2->2->1
     * 输出: true
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode middleNode = findMiddleNode(head);
        if (middleNode == null) {
            return true;
        }
        ListNode reversed = reverseList(middleNode);
        ListNode start1 = head;
        ListNode start2 = reversed;
        while (start1 != null && start2 != null) {
            if (start1.val != start2.val) {
                return false;
            }
            start1 = start1.next;
            start2 = start2.next;
        }
        return true;
    }

    /**
     * 你将会获得一系列视频片段，这些片段来自于一项持续时长为 T 秒的体育赛事。这些片段可能有所重叠，也可能长度不一。
     *
     * 视频片段 clips[i] 都用区间进行表示：开始于 clips[i][0] 并于 clips[i][1] 结束。我们甚至可以对这些片段自由地再剪辑，例如片段 [0, 7] 可以剪切成 [0, 1] + [1, 3] + [3, 7] 三部分。
     *
     * 我们需要将这些片段进行再剪辑，并将剪辑后的内容拼接成覆盖整个运动过程的片段（[0, T]）。返回所需片段的最小数目，如果无法完成该任务，则返回 -1 。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
     * 输出：3
     * 解释：
     * 我们选中 [0,2], [8,10], [1,9] 这三个片段。
     * 然后，按下面的方案重制比赛片段：
     * 将 [1,9] 再剪辑为 [1,2] + [2,8] + [8,9] 。
     * 现在我们手上有 [0,2] + [2,8] + [8,10]，而这些涵盖了整场比赛 [0, 10]。
     * 示例 2：
     *
     * 输入：clips = [[0,1],[1,2]], T = 5
     * 输出：-1
     * 解释：
     * 我们无法只用 [0,1] 和 [1,2] 覆盖 [0,5] 的整个过程。
     * 示例 3：
     *
     * 输入：clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
     * 输出：3
     * 解释：
     * 我们选取片段 [0,4], [4,7] 和 [6,9] 。
     * 示例 4：
     *
     * 输入：clips = [[0,4],[2,8]], T = 5
     * 输出：2
     * 解释：
     * 注意，你可能录制超过比赛结束时间的视频。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/video-stitching
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param clips
     * @param T
     * @return
     */
    public int videoStitching(int[][] clips, int T) {
        return 0;
    }

    // 大顶堆
    private static void heapSort(int[] nums, int start) {
        int length = nums.length;
        boolean ordered = true;
        while (ordered) {
            ordered = false;
            for (int i = length - 1; i >= start && i / 2 >= start; i --) {
                int parentIndex = start + ((i - start) / 2);
                if (nums[parentIndex] < nums[i]) {
                    int temp = nums[parentIndex];
                    nums[parentIndex] = nums[i];
                    nums[i] = temp;
                    ordered = true;
                }
            }
        }
        System.out.println(nums[start]);
    }


    public static void main(String[] args) {
        int[] nums = {2, 3, 5, 6, 10, 19, 15, 21, 17 , 23 , 16};
        IntStream.rangeClosed(0, 4).forEach(i -> {
            heapSort(nums, i);

        });

    }
}
