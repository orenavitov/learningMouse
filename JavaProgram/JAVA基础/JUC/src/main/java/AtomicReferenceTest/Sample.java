package AtomicReferenceTest;

public class Sample {
    private String message;
    private int num;

    public Sample(String message, int num) {
        this.message = message;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sample)) {
            return false;
        }
        Sample other = (Sample) obj;
        if (other.getMessage().equals(this.message) && other.getNum() == this.num) {
            return true;
        }
        return false;
    }
}
