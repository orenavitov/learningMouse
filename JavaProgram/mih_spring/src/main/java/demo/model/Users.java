package demo.model;

import java.util.Random;

/**
 * Created by mihao on 2018/3/25.
 */
public class Users {

    private String loginName;

    private String loginPwd;

    public String getLoginName() {
        return loginName;
    }

    public Users() {
        this.loginPwd = String.valueOf(new Random().nextInt(10000));
    }

    public Users(String loginName, String loginPwd) {
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
