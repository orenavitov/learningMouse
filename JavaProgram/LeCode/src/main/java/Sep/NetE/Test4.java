package Sep.NetE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Test4 {

    private static int max = 0;

    public static void Solution() {
        max = 0;
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int boysCount = firstLineDetials.length;
        ArrayList<Integer> boysNum = new ArrayList<>();
//        int[] boysNum = new int[boysCount];
        for (int i = 0; i < boysCount; i ++) {
            boysNum.add(Integer.valueOf(firstLineDetials[i]));
        }
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        int girlsCount = secondLineDetials.length;
        ArrayList<Integer> girlsNum = new ArrayList();
        for (int i = 0; i < girlsCount; i ++) {
            girlsNum.add(Integer.valueOf(secondLineDetials[i]));
        }
        int n = Integer.valueOf(scanner.nextLine());
        HashMap<Integer, List<Integer>> boysLikeGirls = new HashMap<>();
        for (int i = 0; i < n; i ++) {
            String line = scanner.nextLine();
            String[] lineDetials = line.split(" ");
            int boyNum = Integer.valueOf(lineDetials[0]);
            int girlNum = Integer.valueOf(lineDetials[1]);
            if (!boysLikeGirls.containsKey(boyNum)) {
                ArrayList<Integer> girls = new ArrayList<>();
                boysLikeGirls.put(boyNum, girls);
            }
            boysLikeGirls.get(boyNum).add(girlNum);
        }
        dfs(0, boysCount, 0, boysNum, girlsNum, boysLikeGirls, 0);

    }

    private static void dfs(int matchedBoys, int boysCount, int curBoyNum, List<Integer> boysNum,
                            List<Integer> matchedGirl,
                            HashMap<Integer, List<Integer>> boysLikeGirls, int matched) {
        if (matchedBoys == boysCount) {
            if (matched > max) {
                max = matched;
                return;
            }
        }
        int range = boysNum.size();
        for (int i = curBoyNum; i < range; i ++) {
            int boy = boysNum.get(i);
            boysNum.remove(i);
            List<Integer> girls = boysLikeGirls.get(boy);
            for (Integer girl : girls) {
                if (!matchedGirl.contains(girl)) {
                    matchedGirl.add(girl);
                    dfs(matchedBoys + 1, boysCount, curBoyNum, boysNum, new ArrayList<>(matchedGirl),
                            boysLikeGirls, matched + 1);
                    matchedGirl.remove(matchedGirl.size() - 1);
                }
            }
            boysNum.add(i, boy);

            dfs(matchedBoys + 1, boysCount, curBoyNum + 1, boysNum, new ArrayList<>(matchedGirl), boysLikeGirls,
                    matched);
        }

    }

    public static void main(String[] args) {
        Solution();
        System.out.println(max);
    }
}
