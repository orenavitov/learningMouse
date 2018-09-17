package demo.dao.service;

/**
 * Created by mihao on 2018/3/25.
 */
public interface UserDao {

    public boolean login(String username, String password);

    public void addUser(String userName, String password);

    public void delUser(int id);
}
