package Sep.Test58;

import java.util.ArrayList;
import java.util.Collections;

public class Test3 {
    public static ArrayList<Integer> mergerArrays (ArrayList<Integer> arrayA, ArrayList<Integer> arrayB) {
        // write code here
//        Collections.sort(arrayA);
//        Collections.sort(arrayB);
        ArrayList<Integer> results = new ArrayList<>();
        int startA = 0;
        int startB = 0;
        int endA = arrayA.size();
        int endB = arrayB.size();
        while (startA <= endA - 1 && startB <= endB - 1) {
            int a = arrayA.get(startA);
            int b = arrayB.get(startB);
            if (a == b) {
                results.add(a);
                startA ++;
                startB ++;
                continue;
            }
            if (a < b) {
                startA ++;
                continue;
            }
            if (a > b) {
                startB ++;
                continue;
            }

        }
        return results;

    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayA = new ArrayList<>();
        ArrayList<Integer> arrayB = new ArrayList<>();
        arrayA.add(-5);
        arrayA.add(0);
        arrayA.add(6);
        arrayA.add(8);
        arrayA.add(9);
        arrayA.add(10);
        arrayB.add(0);
        arrayB.add(8);
        arrayB.add(9);
        arrayB.add(11);
        arrayB.add(15);
//        arrayB.add(0);
        ArrayList<Integer> results =mergerArrays(arrayA, arrayB);
        System.out.println(results.toString());
    }
}
