package October.JianXin;

public class Test1 {
    public static int carsTrans (int[] cars, int num) {
        // write code here
        int[] results = new int[num + 1];
        results[0] = 0;
        for (int i = 1; i <= num; i ++) {
            results[i] = -1;
        }
        for (int i = 0; i < cars.length; i ++) {
            int curCarLoadable = cars[i];
            for (int j = 0; j + curCarLoadable <= num; j ++) {
                if (results[j] < 0) {
                    continue;
                } else {
                    int next = j + curCarLoadable;
                    if (results[next] <= 0) {
                        results[next] = results[j] + 1;
                    } else {
                        results[next] = Math.min(results[next], results[j] + 1);
                    }
                }
            }
        }
        return results[num];
    }

    public static void main(String[] args) {
        int[] cars = {1, 2, 5};
        int num = 11;
        System.out.println(carsTrans(cars, num));
    }
}
