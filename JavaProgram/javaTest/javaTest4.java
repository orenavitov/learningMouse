public class javaTest4 {

    public int inc() {
        int x;
        try {
            x = 1;
            int b = 1 / 0;
            return x;
        } catch (Exception e) {
            x = 3;
            return x;
        } finally {
            x = 4;
        }
    }

    public static void main(String args[]) {
        javaTest4 test4 = new javaTest4();
        int result = test4.inc();
        System.out.println("x is :" + result);
    }
}
