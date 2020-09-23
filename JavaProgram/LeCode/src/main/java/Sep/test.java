package Sep;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class test {



    int num = 81;

    public static void main(String[] args) {
        new test().go();
    }

    public void go() {
        increse(++num);
        System.out.println(num);
    }

    public void increse(int num) {
        this.num = this.num + 10;
    }

    
}


