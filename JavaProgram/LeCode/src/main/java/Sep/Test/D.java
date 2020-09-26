package Sep.Test;


public class D {

    public static void test(A a) {
        System.out.println("a");
    }

    public static void test(B b) {
        System.out.println("b");
    }

    public static void main(String[] args) {
        C c = new C();
        A a = new B();
        test(a);
    }
}
