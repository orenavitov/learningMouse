package SomeTest;

public class Aimpl implements a {
    static {
        System.out.println("Aimpl static");
    }

    {
        System.out.println("Aimpl.");
    }

    static int val = 1;
}
