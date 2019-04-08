public class JavaTest3 {
    final Object obj1 = new Object();
    static final Object obj2 = new Object();

    class synchronizedClass {
        private Object obj;

        public synchronizedClass() {

        }

        public synchronizedClass(Object obj) {
            this.obj = obj;
        }

        void synchronizedMethod1() throws InterruptedException {
            synchronized (obj) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " hi!  " + i);
                    if (i == 50) {
                        obj.wait();
                    }
                }
            }
        }

        void synchronizedMethod2() {
            synchronized (obj) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " hello!" + i);
                }
                try {
                    Thread.sleep(2000);
                    obj.notify();
                    System.out.println("--------------------------------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

        synchronized void synchronizedMethod3() {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " good!   " + i);
                if (i == 50) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        synchronized void synchronizedMethod4(Object obj) {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " good!   " + i);
            }
            obj.notify();
        }
    }

    synchronized static void synchronizedMethod4() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " goodBye!");
        }
    }

    void synchronizedMethod5() {
        try {
            synchronized (obj1) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " good morning!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void synchronizedMethod6() {
        try {
            synchronized (obj2) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " good night!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        JavaTest3 jt3 = new JavaTest3();
        JavaTest3 jt3_ = new JavaTest3();
        String clock = new String("mihao");
        synchronizedClass synchronizedclass = jt3.new synchronizedClass(clock);
        synchronizedClass synchronizedclass_ = jt3.new synchronizedClass(clock);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedclass.synchronizedMethod3();
            }
        }, "t1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedclass_.synchronizedMethod4(synchronizedclass);
            }
        }, "t2");
        thread1.start();
        thread2.start();

    }
}
