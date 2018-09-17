package demo.dao.impl;

import demo.dao.service.UserDao;

/**
 * Created by mihao on 2018/3/25.
 */
public class UserDaoImpl implements UserDao {
    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("123")) {
            return true;
        }
        return false;
    }

    public void addUser(String userName, String password) {
        System.out.println(userName + "用户添加成功");
    }

    public void delUser(int id) {
        System.out.println("编号为" + id + "的用户删除");
    }
}
