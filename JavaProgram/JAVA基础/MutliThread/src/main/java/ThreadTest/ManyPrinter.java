package ThreadTest;

import java.util.concurrent.atomic.AtomicInteger;

public class ManyPrinter {

    private static final Object o = new Object();

    private static final AtomicInteger flag = new AtomicInteger(0);

    private static class NewPrinter extends Thread {
        private int order;
        private String messge;
        private int nextOrder;
        private AtomicInteger flag;
        public NewPrinter(int order, int nextOrder, String messge) {
            this.order = order;
            this.messge = messge;
            this.nextOrder = nextOrder;
        }

        public void setFlag(AtomicInteger flag) {
            this.flag = flag;
        }

        public void print() {
            while (true) {
                if(flag.get() == this.order) {
                    System.out.println(this.messge);
                    flag.set(nextOrder);
                }
            }
        }

        @Override
        public void run() {
            print();
        }
    }

    private static class Printer extends Thread {
        private String messgae;

        private Object o;
        public Printer(String messge) {
            this.messgae = messge;
        }

        public void setLockInstance(Object o) {
            this.o = o;
        }

        public void print() {
            while (true) {
                synchronized (o) {
                    System.out.println(messgae);
                    try {
                        o.notifyAll();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void run() {
            print();
        }
    }



    private static void print() {
        Printer printerA = new Printer("A");
        Printer printerB = new Printer("B");
        printerA.setLockInstance(o);
        printerB.setLockInstance(o);
        printerA.start();
        printerB.start();
    }

    private static void newPrint() {

        NewPrinter printerA = new NewPrinter(0, 1,"A");
        NewPrinter printerB = new NewPrinter(1, 0, "B");
        printerA.setFlag(flag);
        printerB.setFlag(flag);
        printerA.start();
        printerB.start();
    }

    public static void main(String[] args) {
        newPrint();
    }
}
