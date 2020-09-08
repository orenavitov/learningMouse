package Sep;

import java.util.*;

public class day20200907 {

    private static class element {
        int value;
        int time;
        public element(int value, int time) {
            this.value = value;
            this.time = time;
        }
    }

    /**
     * 给定一个非空的整数数组，返回其中出现频率前k高的元素。
     * 示例 1:
     *
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2:
     *
     * 输入: nums = [1], k = 1
     * 输出: [1]
     * 提示：
     *
     * 你可以假设给定的k总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
     * 你的算法的时间复杂度必须优于 O(n log n) ,n是数组的大小。
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
     * 你可以按任意顺序返回答案。
     * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
     * @param nums
     * @param k
     * @return
     */
    public static int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> elementAndAppearTime = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            int num = nums[i];
            if (!elementAndAppearTime.keySet().contains(num)) {
                elementAndAppearTime.put(num, 1);
            } else {
                int time = elementAndAppearTime.get(num);
                elementAndAppearTime.put(num, time + 1);
            }
        }
        int type = elementAndAppearTime.size();
        element[] elements = new element[type];
        int i = 0;
        for(Integer num : elementAndAppearTime.keySet()) {
            elements[i] = new element(num, elementAndAppearTime.get(num));
            i ++;
        }
        element e = fastSort(elements, 0, type - 1, k - 1);
        int[] results = new int[k];
        for (int j = 0, l = 0; j < type && l < k; j ++, l ++) {
            if (elements[j].time >= e.time) {
                results[l] = elements[j].value;
            }
        }
        return results;
    }

    private static element fastSort(element[] elements, int s, int e, int k) {
        int start = s;
        int end = e;
        element target = elements[start];
        while (start < end) {
            if (elements[end].time > target.time) {
                elements[start] = elements[end];
                start ++;

            } else {
                end --;
                continue;
            }
            while (elements[start].time > target.time && start < end) {
                start ++;
            }
            elements[end] = elements[start];
            end --;
        }
        elements[start] = target;
        if (start == k) {
            return elements[start];
        } else {
            if (start < k) {
                return fastSort(elements, start + 1, e, k);
            }
            if (start > k) {
                return fastSort(elements, s, end, k);
            }
        }
        return elements[0];
    }

    /*
     * 获取图的子图个数， 假设图是无向图；
     */
    private static int getSubGraphCount(int[][] graph) {
        int n = graph.length;
        int subGraphCount = 0;
        List<Integer> visited = new ArrayList<>();
        LinkedList<Integer> que = new LinkedList<>();
        for (int i = 0; i < n; i ++) {
            if(visited.contains(i)) {
                continue;
            }
            que.addLast(i);
            visited.add(i);
            while (!que.isEmpty()) {
                int node = que.poll();
                int[] neighbors = graph[node];
                for (int j = i; j < n; j ++) {
                    if (neighbors[j] == 1 && !visited.contains(j)) {
                        que.addLast(j);
                        visited.add(j);
                    }
                }
            }
            subGraphCount ++;
        }
        return subGraphCount;
    }

    public static void main(String[] args) {
        int[][] graph = new int[][] {
                {0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        int subGraphCount = getSubGraphCount(graph);
        System.out.println(subGraphCount);
    }
}
