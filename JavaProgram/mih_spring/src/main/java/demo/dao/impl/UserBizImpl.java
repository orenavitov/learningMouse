package demo.dao.impl;

import demo.dao.service.UserBiz;
import demo.dao.service.UserDao;

/**
 * Created by mihao on 2018/3/25.
 */
public class UserBizImpl implements UserBiz {

    private UserDao userDao;

    public boolean login(String username, String password) {
        return userDao.login(username, password);
    }

    public void addUser(String userName, String password) {
        userDao.addUser(userName, password);
    }

    public void delUser(int id) {
        userDao.delUser(id);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
