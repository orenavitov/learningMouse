package Mih.demo.Dao.Services;

import Mih.demo.Modules.User;

public interface UserService {

    void createUser(User user);

    int getUserId(String userName, String password);
}
