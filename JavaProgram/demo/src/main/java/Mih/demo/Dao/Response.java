package Mih.demo.Dao;

public class Response {
    private int state;
    private String messge;

    public Response() {}

    public Response(int state, String message) {
        this.state = state;
        this.messge = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }
}
