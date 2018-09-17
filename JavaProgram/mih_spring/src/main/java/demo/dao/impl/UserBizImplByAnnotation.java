package demo.dao.impl;

import demo.dao.service.UserBiz;
import demo.dao.service.UserDao;

import javax.annotation.Resource;

/**
 * Created by mihao on 2018/3/25.
 */
public class UserBizImplByAnnotation implements UserBiz {

    /**
     * 通过注解方式对beeen进行注入
     * @Resource 基于name
     * @Autowire 基于类型
     */

    @Resource(name = "userDao" )
    private UserDao userDao;

    public boolean login(String username, String password) {
        return false;
    }

    public void addUser(String userName, String password) {

    }

    public void delUser(int id) {

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
