package October;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class day20201020 {
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        int max = 0;
        int preStart = 0;
        for (int i = preStart; i < s.length();) {
            char cur = s.charAt(i);
            boolean hasSame = false;
            for (int j = preStart; j < i; j ++) {
                char pre = s.charAt(j);
                if (pre == cur) {
                    i = j + 1;
                    preStart = j + 1;
                    hasSame = true;
                    break;
                }
            }
            if (!hasSame) {
                i ++;
                result ++;
            } else {
                max = result > max ? result : max;
                result = 0;
            }
        }
        max = result > max ? result : max;
        return max;
    }

    public static int lengthOfLongestSubstring2(String s) {
        LinkedList<Character> que = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i ++) {
            char cur = s.charAt(i);
            if (que.contains(cur)) {
                max = Math.max(max, que.size());
                while (que.contains(cur)) {
                    que.poll();
                }
            } else {
                que.addFirst(cur);
            }

        }
        max = Math.max(max, que.size());
        return max;
    }

    public static void main(String[] args) {
        
        System.out.println(lengthOfLongestSubstring2("abcbefgh"));
    }
}
