package Sep;

import java.util.*;

public class TX {
    private static class Node {
        int value;
        Node next;
        public Node(int value) {
            this.value = value;
        }
    }

    private static void Solution1() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        Node head1= new Node(Integer.valueOf(secondLineDetials[0]));
        Node pre1 = head1;
        for (int i = 1; i < n; i ++) {
            Node node = new Node(Integer.valueOf(secondLineDetials[i]));
            pre1.next = node;
            pre1 = node;
        }
        int m = Integer.valueOf(scanner.nextLine());
        String forthLine = scanner.nextLine();
        String[] forthLineDetials = forthLine.split(" ");
        Node head2 = new Node(Integer.valueOf(forthLineDetials[0]));
        Node pre2 = head2;
        for (int i = 1; i < m; i ++) {
            Node node = new Node(Integer.valueOf(forthLineDetials[i]));
            pre2.next = node;
            pre2 = node;
        }
        Node start1 = head1;
        Node start2 = head2;
        while (start1 != null && start2 != null) {
            int val1 = start1.value;
            int val2 = start2.value;
            if (val1 == val2) {
                System.out.print(val1 + " ");
                start1 = start1.next;
                start2 = start2.next;
                continue;
            }
            if (val1 < val2) {
                start2 = start2.next;
                continue;
            }
            if (val2 < val1) {
                start1 = start1.next;
                continue;
            }
        }
    }

    private static int Solution2() {
        List<List<Integer>> allGroups = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int m = Integer.valueOf(firstLineDetials[1]);
        int[] counts = new int[m];
        for (int i = 0; i < m; i ++) {
            String line = scanner.nextLine();
            String[] lineDetials = line.split(" ");
            int length = Integer.valueOf(lineDetials[0]);
            counts[i] = length;
            List<Integer> groupMembers = new ArrayList<>();
            for (int j = 1; j < length + 1; j ++) {
                groupMembers.add(Integer.valueOf(lineDetials[j]));
            }
            allGroups.add(groupMembers);
        }
        LinkedList<Integer> que = new LinkedList<>();
        List<Integer> acceptMembers = new ArrayList<>();
        que.addLast(0);
        while (!que.isEmpty()) {
            int member = que.peek();
//            acceptMembers.add(member);
            for (int i = 0; i < allGroups.size();) {
                List<Integer> groupMembers = allGroups.get(i);
                if (groupMembers.contains(member)) {
                    for (Integer mem : groupMembers) {
                        if (!acceptMembers.contains(mem)) {
                            que.addLast(mem);
                            acceptMembers.add(mem);
                        }
                    }
                    allGroups.remove(groupMembers);
                    i = 0;
                } else {
                    i ++;
                }

            }
            que.removeFirst();
        }
        return acceptMembers.size();
    }

    private static void Solution4() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        String secondLine = scanner.nextLine();
        String[] seconLineDetials = secondLine.split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i ++) {
            nums[i] = Integer.valueOf(seconLineDetials[i]);
        }
        int left = fastSort(nums, 0, n - 1, n / 2 - 1);
        int right = fastSort(nums, 0, n - 1, n / 2);
        for (int i = 0; i < n; i ++) {
            if (i < n / 2) {
                System.out.println(right);
            } else {
                System.out.println(left);
            }
        }
    }

    private static int fastSort(int[] nums, int s, int e, int k) {
        int length = nums.length;
        int start = s;
        int end = e;
        int target = nums[start];
        while (start < end) {
            if (nums[end] < target) {
                nums[start] = nums[end];
                start ++;

            } else {
                end --;
                continue;
            }
            while (nums[start] < target && start < end) {
                start ++;
            }
            nums[end] = nums[start];
            end --;
        }
        nums[start] = target;
        if (start == k) {
            return nums[start];
        } else {
            if (start < k) {
               return fastSort(nums, start + 1, e, k);
            }
            if (start > k) {
                return fastSort(nums, s, end, k);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, 4, 1, 5, 7, 3};
//        System.out.println(fastSort(nums, 0, nums.length - 1, 2));
        Solution4();
    }
}
