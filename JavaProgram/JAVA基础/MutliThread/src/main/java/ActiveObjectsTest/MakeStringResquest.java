package ActiveObjectsTest;

public class MakeStringResquest extends MethodRequest {

    private final int count;
    private final char c;

    public MakeStringResquest(Servant servant, FutureResult futureResult, int count, char c) {
        super(servant, futureResult);
        this.count = count;
        this.c = c;
    }

    @Override
    public void execute() {
        Result result = this.servant.makeString(count, c);
        futureResult.setResult(result);
    }
}
