package UnSafeTest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class SomeInterestingTest {
    private static Unsafe getUnSafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
