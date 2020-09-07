package August;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day20200831 {
    /**
     * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
     * 在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，其中 N = rooms.length。 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
     * 最初，除 0 号房间外的其余所有房间都被锁住。
     * 你可以自由地在房间之间来回走动。
     * 如果能进入每个房间返回 true，否则返回 false。
     * 示例 1：
     * 输入: [[1],[2],[3],[]]
     * 输出: true
     * 解释:
     * 我们从 0 号房间开始，拿到钥匙 1。
     * 之后我们去 1 号房间，拿到钥匙 2。
     * 然后我们去 2 号房间，拿到钥匙 3。
     * 最后我们去了 3 号房间。
     * 由于我们能够进入每个房间，我们返回 true。
     * 示例 2：
     * 输入：[[1,3],[3,0,1],[2],[0]]
     * 输出：false
     * 解释：我们不能进入 2 号房间。
     * 链接：https://leetcode-cn.com/problems/keys-and-rooms
     */
    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        List<Integer> visited = new ArrayList<>();
        List<Integer> unVisited = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i ++) {
            unVisited.add(i);
        }
        bfs(0, visited, unVisited, rooms);
        if (unVisited.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private static void bfs(int curRoom, List<Integer> visited, List<Integer> unVisited, List<List<Integer>> rooms) {
        visited.add(curRoom);
        if (unVisited.contains(curRoom)) {
            unVisited.remove(new Integer(curRoom));
        }
        List<Integer> tempRooms = rooms.get(curRoom);
        for(int tempRoom : tempRooms) {
            if (!visited.contains(tempRoom)) {
                bfs(tempRoom, visited, unVisited, rooms);
            }
        }
    }

    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组nums1 和nums2。
     * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为O(log(m + n))。
     * 你可以假设nums1和nums2不会同时为空。
     * 示例 1:
     * nums1 = [1, 3]
     * nums2 = [2]
     * 则中位数是 2.0
     * 示例 2:
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * 则中位数是 (2 + 3)/2 = 2.5
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0.0;
    }



    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 3);
        List<Integer> list2 = Arrays.asList(3, 0, 1 );
        List<Integer> list3 = Arrays.asList(2);
        List<Integer> list4 = Arrays.asList(0);
        List<List<Integer>> rooms = new ArrayList<>();
        rooms.add(list1);
        rooms.add(list2);
        rooms.add(list3);
        rooms.add(list4);
        System.out.println(canVisitAllRooms(rooms));
    }
}
