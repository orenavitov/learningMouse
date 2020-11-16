package Mih.demo.Dao;

import Mih.demo.Dao.Services.UserService;
import Mih.demo.Mappers.UserMapper;
import Mih.demo.Modules.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(User user) {
        String role = user.getRole();
        String passWord = user.getPassWord();
        String userName = user.getUserName();
        userMapper.createUser(passWord, userName, role);
    }

    @Override
    public int getUserId(String userName, String password) {
        return userMapper.getUserId(userName, password);
    }
}
