public class javaTest {

    static void quickSort(int numbers[], int low, int high) {
        int currentLow = low;
        int currentHigh = high;
        int key = numbers[low];
        while (currentLow < currentHigh) {
            while (key <= numbers[currentHigh] && currentLow < currentHigh) {
                    currentHigh --;
            }

            numbers[currentLow] = numbers[currentHigh];

            while (key >= numbers[currentLow] && currentLow < currentHigh) {
                    currentLow ++;
            }
            numbers[currentHigh] = numbers[currentLow];

        }
        numbers[currentLow] = key;
        if (currentLow -1 - low >= 1) {
            quickSort(numbers, low, currentLow - 1);
        }
        if (high - currentLow - 1 >= 1) {
            quickSort(numbers, currentLow + 1, high);
        }
    }
    static void  insertSort(int numbers[]){
        int length = numbers.length;
        int temp;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (numbers[j] > numbers[i]) {
                    temp = numbers[j];
                    numbers[j] = numbers[i];
                    for (int k = i - 1; k > j; k--) {
                        numbers[k + 1] = numbers[k];
                    }
                    numbers[j + 1] = temp;
                }
            }
        }
    }

    static void selectSort(int numbers[]) {
        int length = numbers.length;

        for (int i = 0; i < length; i++) {
            int temp = numbers[i];
            int min = numbers[i];
            int index = i;
            for (int j = i + 1; j < length; j ++) {
                if (numbers[j] < min) {
                    min = numbers[j];
                    index = j;
                }
            }
            numbers[i] = numbers[index];
            numbers[index] = temp;
        }
    }

    static void merge(int numbers[], int start, int middle, int end, int copyNumbers[]) {
        int i = start;
        int j = middle + 1;
        int index = i;
        while (i <= middle && j <= end) {
            if (numbers[i] <= numbers[j]) {
                copyNumbers[index] = numbers[i];
                i ++;
                index ++;
            } else {
                copyNumbers[index] = numbers[j];
                j ++;
                index ++;
            }
        }

        while (i <= middle) {
            copyNumbers[index ++] = numbers[i ++];
        }
        while (j <= end) {
            copyNumbers[index ++] = numbers[j ++];
        }

        for (int k = start; k <= end; k++) {
            numbers[k] = copyNumbers[k];
        }
    }
    static void mergeSort(int numbers[], int start, int end, int copyNumbers[]) {

        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(numbers, start, middle, copyNumbers);
            mergeSort(numbers, middle + 1, end, copyNumbers);
            merge(numbers, start, middle, end, copyNumbers);
        }

    }

    public static void main(String[] args) throws Exception{
        System.out.println("Hello World!");
        int numbers[] = {3, 1, 5, 6, 2, 10, 8, 231, 2, 43, 454, 5672, 3432, 53, 265, 674, 3435};
        int copyNumbers[] = new int[17];
        mergeSort(numbers, 0, 16, copyNumbers);
        for(int n : numbers) {
            System.out.print(n + " ");
        }

    }
}
