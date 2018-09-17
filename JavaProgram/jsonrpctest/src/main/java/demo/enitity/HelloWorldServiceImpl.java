package demo.enitity;

public class HelloWorldServiceImpl implements HelloWorldService {

    int  count = 0;

    public HelloWorldBean getDemoBean(String code, String msg) {
        System.out.println("HelloWorldBean get");

        HelloWorldBean bean1 = new HelloWorldBean();
        bean1.setCode(Integer.parseInt(code));
        bean1.setMsg(msg+",javaBean is fine!");
        return bean1;
    }

    public Integer getInt(Integer code) {
        return code + count;
    }

    public String getString(String msg) {
        return msg + ",server is fine!";
    }

    public void doSomething() {
        count++;
        System.out.println("do something"+"; count=>"+count);
    }
}
