//package Sep;
//
//import java.util.*;
//
//public class day20200917 {
//    public static int[] findRedundantDirectedConnection(int[][] edges) {
//        HashMap<Integer, Integer> inMap = new HashMap();
//        HashMap<Integer, Integer> outMap = new HashMap();
//        Set<Integer> nodes = new HashSet<>();
//        for (int i =0; i < edges.length; i ++) {
//            int[] edge = edges[i];
//            int src = edge[0];
//            int dst = edge[1];
//            outMap.computeIfAbsent(src, k -> {
//                return 0;
//            });
//            outMap.computeIfPresent(src, (k, v) -> {
//                return v + 1;
//            });
//            inMap.computeIfAbsent(dst, k -> {
//                return 0;
//            });
//            inMap.computeIfPresent(dst, (k, v) -> {
//                return v + 1;
//            });
//            nodes.add(src);
//            nodes.add(dst);
//        }
//        if (inMap.size() == outMap.size()) {
//            return edges[edges.length - 1];
//        }
//        List<Integer> starts = new ArrayList<>();
//        for (Integer src : outMap.keySet()) {
//            if(outMap.get(src) > 1) {
//
//            }
//        }
//
//
//
//
//        for (int i = edges.length - 1;  i >= 0; i --) {
//            int[] edge = edges[i];
//            int src = edge[0];
//            int dst = edge[1];
//            int outCount = outMap.get(src) - 1;
//            int inCount = inMap.get(dst) - 1;
//            if (inCount > 0) {
//                return edge;
//            } else {
//                if (dst == start) {
//                    return edge;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        int[][] edges = new int[][] {
//                {1,2},
//                {2,3},
//                {3,4},
//                {4,1},
//                {1,5}
//        };
//        int[] result = findRedundantDirectedConnection(edges);
//        System.out.print("[" + result[0] + ", " + result[1] + "]");
//    }
//}
