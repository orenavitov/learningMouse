package JAVA8.LambdaTest;

public class AddTest {


    private static void add(String A, String B, AddInterface addInterface) {
        String result = addInterface.add(A, B);
        System.out.println("result : " + result);
    }

    public static void main(String[] args) {
        add("mi", "hao", (A, B) -> {
            return A.toUpperCase() + " " + B.toUpperCase();
        });
    }
}
