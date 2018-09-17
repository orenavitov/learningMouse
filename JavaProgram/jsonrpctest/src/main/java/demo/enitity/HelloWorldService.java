package demo.enitity;

public interface HelloWorldService {
    public HelloWorldBean getDemoBean(String code, String msg);

    public Integer getInt(Integer code);

    public String getString(String msg);

    public void doSomething();
}
