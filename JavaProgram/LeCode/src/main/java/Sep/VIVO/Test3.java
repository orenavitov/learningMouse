package Sep.VIVO;

import java.util.ArrayList;
import java.util.List;
// "1,2,-1,1"
public class Test3 {
    public static String compileSeq (String input) {
        // write code here
        String[] detialsString = input.split(",");
        Integer[] detialsInt = new Integer[detialsString.length];
        List<Integer> used = new ArrayList<>();
        for (int i = 0; i < detialsString.length; i ++) {
            detialsInt[i] = Integer.valueOf(detialsString[i]);
        }
        while (used.size() < detialsInt.length) {
            for (int i = 0; i < detialsInt.length; i ++) {
                int pre = detialsInt[i];
                if (used.contains(pre)) {
                    used.add(i);
                    detialsInt[i] = -1;
                    break;
                }
                if (used.contains(i) || pre != -1) {
                    continue;
                } else {
                    if (used.contains(pre) || pre ==-1) {
                        used.add(i);
                        detialsInt[i] = -1;
                    }

                }
            }
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < used.size(); i ++) {
            result.append(used.get(i));
            if (i != used.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "1,2,-1,1";
        System.out.println(compileSeq(input));
    }
}
