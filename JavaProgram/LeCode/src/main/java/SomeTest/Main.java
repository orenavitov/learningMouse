package SomeTest;

public class Main {

    interface Interface0 { 
        int A = 10;
    }
    interface Interface1 extends Interface0 { 
        int A = 1; 
    }
//    interface Interface2 { 
//        int A = 2; 
//    }
//    static class Parent implements Interface1 { 
//        public static int A = 3; 
//    }
    static class Sub implements Interface1 {
//        public static int A = 4;
    }
    public static void main(String[] args) { 
        System.out.println(Sub.A); 
    }
}
