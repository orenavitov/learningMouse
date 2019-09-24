package test.test;

public class Test1 {
    public static void main(String args[]) {
        String ips[] = {"192.68.0.1", "192.189.2.1", "10.189.2.1", "10.10.2.1"};
        Long ipl[] = new Long[4];
        for (String ip : ips) {
            ip.split(".");
        }
    }
}
