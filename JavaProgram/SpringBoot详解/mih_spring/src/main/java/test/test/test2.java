package test.test;


/*
 *一层因该是不需要坐电梯的， 那么就从第二层开始，假设电梯最多载10人
 *
 */
public class test2 {

    private static final int LOADNUMBER = 10;

    private  static float useTime(int startFlow, int endFlow) {
        float result = 0;
        float time = (endFlow -startFlow + 1) * 100 / 10;
        result = result + endFlow * 5;
        float waitTime = 0;
        for (int delay = 1; delay <= (endFlow -startFlow + 1); delay ++) {
            waitTime = delay * 20 + waitTime;
        }
        return  result + waitTime * time;
    }

    private static float getMinTime(float time1, float time2, float time3) {
        float result = 0;
        if (time1 <= time2) {
            result = time2;
        } else {
            result = time1;
        }
        if (result <= time3) {
            return time3;
        }
        return result;
    }



    public static void main(String args[]) {

        float useTime1 = 0;
        float useTime2 = 0;
        float useTime3 = 0;
        int end1 = 12;
        int end2 = 12;
        int end3 = 12;
        float result = useTime(2, 12) / 3;
        for (int endFlow1 = 2; endFlow1 <= 10; endFlow1 ++) {

            useTime1 = useTime(2, endFlow1);
            int endFlow2 = endFlow1 + 1;
            int startFlow2 = endFlow1 + 1;
            for (;endFlow2 <= 11; endFlow2 ++) {
                useTime2 = useTime(startFlow2, endFlow2);
                int startFlow3 = endFlow2 + 1;
                int endFlow3 = 12;
                useTime3 = useTime(startFlow3, endFlow3);
            }
            float currentMaxTime = getMinTime(useTime1, useTime2, useTime3);
            if (currentMaxTime < result) {
                end1 = endFlow1;
                end2 = endFlow2;
            }
        }
        System.out.println("the first one: "  + "start: " + 2  + " end: " + end1);
        System.out.println("the second one: "  + "start: " + (end1 + 1)  + " end: " + end2);
        System.out.println("the third one: "  + "start: " + (end2 + 1)  + " end: " + 12);
    }
}
