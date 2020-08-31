package August;

public class day20200811 {
    private static void fastSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int forwardIndex = start;
        int backwardIndex = end;
        int targetNum = nums[start];
        while (forwardIndex < backwardIndex) {
            if(nums[backwardIndex] < nums[forwardIndex]) {
                nums[forwardIndex] = nums[backwardIndex];
                forwardIndex = forwardIndex + 1;
            } else {
                backwardIndex --;
                continue;
            }
            while (forwardIndex < backwardIndex) {
                if (nums[forwardIndex] > nums[backwardIndex]) {
                    nums[backwardIndex] = nums[forwardIndex];
                    backwardIndex --;
                    break;
                } else {
                    forwardIndex ++;
                }
            }
        }
        nums[backwardIndex] = targetNum;
        fastSort(nums, start, backwardIndex);
        fastSort(nums, backwardIndex + 1, end);
    }

    private static int findMthMinNum(int nums[], int M, int start, int end) {
        if (end - start < 0) {
            return nums[0];
        }
        int forwardIndex = start;
        int backwardIndex = end;
        int targetNum = nums[start];
        while (forwardIndex < backwardIndex) {
            if(nums[backwardIndex] < nums[forwardIndex]) {
                nums[forwardIndex] = nums[backwardIndex];
                forwardIndex = forwardIndex + 1;
            } else {
                backwardIndex --;
                continue;
            }
            while (forwardIndex < backwardIndex) {
                if (nums[forwardIndex] > nums[backwardIndex]) {
                    nums[backwardIndex] = nums[forwardIndex];
                    backwardIndex --;
                    break;
                } else {
                    forwardIndex ++;
                }
            }
        }
        nums[backwardIndex] = targetNum;
        if (backwardIndex + 1 == M) {
            return targetNum;
        } else {
            if (backwardIndex + 1 > M) {
                return findMthMinNum(nums, M, start, backwardIndex);
            } else {
                return findMthMinNum(nums, M, backwardIndex + 1, end);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, 4, 1, 5, 6, 10, 8};
        int length = nums.length;
        int result = findMthMinNum(nums, 5, 0, length - 1);
        System.out.println(result);
    }
}
