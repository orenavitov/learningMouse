package Sep.PerfectWord;
import java.util.*;
public class Test1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //建筑物个数
        int n = Integer.parseInt(sc.nextLine());
        int[][] buildings = new int[n][3];
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            String[] arr = s.split(" ");
            for (int j = 0; j < arr.length; j++) {
                buildings[i][j] = Integer.parseInt(arr[j]);
            }
        }

        //获取天际线，待实现
        List<List<Integer>> skyline = getSkyline(buildings);

        //输出skyline到标准输出
        for (List<Integer> point : skyline) {
            int size = point.size();
            for (int i = 0; i < size; i++) {
                System.out.print(point.get(i));
                if (i < size-1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    public static List<List<Integer>> getSkyline(int[][] buildings) {
        //todo 实现算法
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> rightBounds = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < buildings.length; i ++) {
            int leftBound = buildings[i][0];
            int rightBound = buildings[i][1];
            int height = buildings[i][2];
            boolean rightest = true;
            boolean heightest = true;
            int rightestIndex = 0;
            for (int j = 0; j < rightBounds.size(); j ++) {
                if (leftBound < rightBounds.get(j) ) {
                    rightest = false;
                    break;
                }
            }
            for (int j = 0; j < heights.size(); j ++) {
                if (height < heights.get(j)) {
                    heightest = false;
                    break;
                }
            }
            if (rightest || heightest) {
                results.add(Arrays.asList(leftBound, height));
            }
            if (!heightest) {
                for (int j = 0; j < rightBounds.size(); j ++) {
                    if (rightBounds.get(j) > rightestIndex) {
                        rightestIndex = rightBounds.get(j);
                    }
                    if (rightBound < rightBounds.get(j) ) {
                        rightest = false;
                        break;
                    }
                }
                if (rightest) {
                    results.add(Arrays.asList(rightestIndex, height));
                }
            }
            rightBounds.add(rightBound);
            heights.add(height);

        }
        return results;
    }
}
