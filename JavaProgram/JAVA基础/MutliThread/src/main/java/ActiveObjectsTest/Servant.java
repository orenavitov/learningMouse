package ActiveObjectsTest;

class Servant implements ActiveObject {
    @Override
    public Result makeString(int count, char c) {
        char[] buf = new char[count];
        for (int i = 0; i < count; i ++) {
            buf[i] = c;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult(String.valueOf(buf));
    }

    @Override
    public void displayString(String text) {
        System.out.println("Display : " +text);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
