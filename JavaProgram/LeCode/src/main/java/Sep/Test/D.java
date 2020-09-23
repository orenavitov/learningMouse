package Sep.Test;

import Sep.quShi.A;

public class D extends A {
    public static void main(String[] args) {
        new Thread(() -> {
            int i = 1;
            while (true) {
                i ++;
            }
        }).start();

        new Thread(() -> {
            int j = 1;
            while (true) {
                j ++;
            }
        }).start();

    }
}
