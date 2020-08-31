package August;

import java.util.*;

public class day20200806 {
    /**
     * 给定一组唯一的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
     * <p>
     * 示例 1:
     * <p>
     * 输入: ["abcd","dcba","lls","s","sssll"]
     * 输出: [[0,1],[1,0],[3,2],[2,4]]
     * 解释: 可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
     * 示例 2:
     * <p>
     * 输入: ["bat","tab","cat"]
     * 输出: [[0,1],[1,0]]
     * 解释: 可拼接成的回文串为 ["battab","tabbat"]
     * <p>
     * 链接：https://leetcode-cn.com/problems/palindrome-pairs
     */
    public static List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> results = new ArrayList<>();
        int N = words.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    continue;
                }
                if (isPair(words[i], words[j])) {
                    results.add(Arrays.asList(i, j));
                }
            }
        }
        return results;
    }

    public static boolean isPair(String str1, String str2) {
        int n1 = str1.length();
        int n2 = str2.length();
        int middle = (n1 + n2) / 2;
        String str = str1 + str2;
        for (int i = 0; i < middle; i++) {
            if (str.charAt(i) == str.charAt(n1 + n2 - 1 - i)) {
                continue;
            } else {
                return false;
            }
        }
        str = null;
        return true;
    }


    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     *
     * 示例 1:
     *
     * 输入: "()"
     * 输出: true
     * 示例 2:
     *
     * 输入: "()[]{}"
     * 输出: true
     * 示例 3:
     *
     * 输入: "(]"
     * 输出: false
     * 示例 4:
     *
     * 输入: "([)]"
     * 输出: false
     * 示例 5:
     *
     * 输入: "{[]}"
     * 输出: true
     *
     * 链接：https://leetcode-cn.com/problems/valid-parentheses
     * @param
     */

    public static boolean isValid(String s) {

        Stack<Character> stringStack = new Stack<>();
        for(int i = 0; i < s.length(); i ++) {
            if ('(' == s.charAt(i) || '[' == s.charAt(i) || '{' == s.charAt(i)) {
                stringStack.push(s.charAt(i));
            } else {
                if (!(stringStack.size() > 0)) {
                    return false;
                } else {
                    char top = stringStack.pop();
                    if (')' == s.charAt(i) && top == '(' ||
                        ']' == s.charAt(i) && top == '[' ||
                        '}' == s.charAt(i) && top == '{') {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        if (stringStack.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 2：
     *
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     *
     * 链接：https://leetcode-cn.com/problems/house-robber
     * @param
     */
    public static int rob(int[] nums) {
        return Math.max(steal(0, nums), notSteal(0, nums));
    }

    public static int steal(int numIndex, int[] nums) {
        if (numIndex >= nums.length) {
            return 0;
        } else {
            return nums[numIndex] + notSteal(numIndex + 1, nums);
        }
    }

    public static int notSteal(int numIndex, int[] nums) {
        if (numIndex >= nums.length) {
            return 0;
        } else {
            return Math.max(steal(numIndex + 1, nums), notSteal(numIndex + 1, nums));
        }
    }

    public static int modifyRob(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }
        for (int i = 0; i < length; i ++) {
            int notStealResultIndex = i - 1;
            int notStealResult = notStealResultIndex < 0 ? 0 : nums[notStealResultIndex];
            int stealResultIndex = i - 2;
            int stealResult = stealResultIndex < 0 ? nums[i] : nums[stealResultIndex] + nums[i];
            nums[i] = Math.max(notStealResult, stealResult);
        }
        return nums[length - 1];
    }

    /**
     * 1. 两数之和
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     *
     *
     * 示例:
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * @param
     */
    public static int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        int[] result = new int[2];
        Map<Integer, Integer> keyIndexMap = new HashMap<>();
        for (int i = 0; i < length; i ++) {
            if(keyIndexMap.containsKey(target - nums[i])) {
                result[0] = keyIndexMap.get(target - nums[i]);
                result[1] = i;
                return result;
            } else {
                keyIndexMap.put(nums[i], i);
            }
        }
        return result;
    }


    /**
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *
     *  
     *
     * 示例：
     *
     * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     *
     * 满足要求的三元组集合为：
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     *
     * 链接：https://leetcode-cn.com/problems/3sum
     * @param
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int numsSize = nums.length;
        Arrays.sort(nums);
        HashMap<Integer, List<Integer>> numPositions = new HashMap<>();
        for (int i = 0; i < numsSize; i ++) {
            final int position = i;
            numPositions.putIfAbsent(nums[i], new ArrayList<>());
            numPositions.compute(nums[i], (key, value) -> {
                value.add(position);
                return value;
            });
        }
        for (int i = 0; i < numsSize - 2; i ++) {
            final int start = i;
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < numsSize - 1; j ++) {
                final int board = j;
                if (board > i + 1 && nums[board] == nums[board - 1]) {
                    continue;
                }
                int thirdNum = -(nums[i] + nums[j]);

                List<Integer> thirdNumPositions = numPositions.get(thirdNum);
                if (thirdNumPositions != null) {
                    thirdNumPositions.parallelStream().filter(position -> {
                        return position > board;
                    }).findFirst().ifPresent(position -> {
                                List<Integer> tuples = new ArrayList<>();
                                tuples.add(nums[start]);
                                tuples.add(nums[board]);
                                tuples.add(nums[position]);
                                result.add(tuples);
                    });
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        int[] nums = new int[] {-1, 0, 1, 2, -1, -4};
        int[] nums = new int[] {0,0,0,0};
        List<List<Integer>> result = threeSum(nums);
        result.forEach(tuple -> {
            System.out.println("[" + tuple.get(0) + ", " + tuple.get(1) + ", " + tuple.get(2) + "]");
        });
    }
}
