package AtomicReferenceTest;

import java.util.concurrent.atomic.AtomicReference;

public class Test {
    public static void main(String[] args) {
        Sample sample = new Sample("mih", 6);

        Sample other_sample = new Sample("mih", 1);
        Sample new_sample = new Sample("mih", 7);
        AtomicReference<Sample> sampleAtomicReference = new AtomicReference<Sample>(sample);
        System.out.println(sampleAtomicReference.get());
        sample.setNum(7);
        // compareAndSet 比较的是引用对象;
        boolean result = sampleAtomicReference.compareAndSet(sample, new_sample);
        System.out.println(result);
        System.out.println(sampleAtomicReference.get());
    }
}
