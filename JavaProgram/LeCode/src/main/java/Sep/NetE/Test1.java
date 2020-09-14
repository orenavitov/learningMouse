package Sep.NetE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Test1 {
    public static void Solution() {
        int result = 0;
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int nodeCount = Integer.valueOf(firstLineDetials[0]);
        int edgeCount = Integer.valueOf(firstLineDetials[1]);
        HashMap<Integer, List<Integer>> parentAndChilds = new HashMap<>();
        for (int i = 0; i < edgeCount; i ++) {
            String line = scanner.nextLine();
            String[] detials = line.split(" ");
            int parent = Integer.valueOf(detials[0]);
            int child = Integer.valueOf(detials[2]);
            if (!parentAndChilds.containsKey(parent)) {
                parentAndChilds.put(parent, new ArrayList<>());
            }
            parentAndChilds.get(parent).add(child);
        }
        for (Integer parent : parentAndChilds.keySet()) {
            boolean match = true;
            List<Integer> childs = parentAndChilds.get(parent);

            for (Integer child : childs) {
                if (parentAndChilds.containsKey(child)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                result ++;
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        Solution();
    }
}
