package WorkerThreadTest;

public class Request {
    private final String name;
    private final int number;

    public Request(final String name, final int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " execute!");
    }

    @Override
    public String toString() {
        return "Requeest name : " + name + " - " + "numebr : " + number;
    }
}
