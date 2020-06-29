package ActiveObjectsTest;

public class DisplayTextRequest extends MethodRequest {

    private final String text;


    public DisplayTextRequest(Servant servant, final String text) {
        super(servant, null);
        this.text = text;
    }

    @Override
    public void execute() {
        this.servant.displayString(this.text);

    }
}
