package ReadWriteLock;

public class Test {

    private static final int BUFF_LENGTH = 10;

    private static final String MESS = "abcdefghij";

    public static void main(String[] args) {
        SharedData sharedData = new SharedData(BUFF_LENGTH);
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Reader(sharedData).start();
        new Writer(sharedData).start();
        new Writer(sharedData).start();
    }

    public static class Writer extends Thread {

        private SharedData sharedData;

        public Writer(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        @Override
        public void run() {
            int index = 0;
            while (true) {
                doWrite(MESS.charAt(index), index ++);
                if (index == BUFF_LENGTH) {
                    index = 0;
                }
            }
        }

        private void doWrite(char c, int index) {
            this.sharedData.write(c, index);
        }
    }

    public static class Reader extends Thread {
        private SharedData sharedData;
        public Reader(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        @Override
        public void run() {
            while (true) {
                char[] newBuf = sharedData.read();
                String s = new String(newBuf);
                System.out.println("the buff is : " + s);
            }
        }
    }
}


