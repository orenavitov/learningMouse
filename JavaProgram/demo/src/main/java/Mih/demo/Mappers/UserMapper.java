package Mih.demo.Mappers;

import Mih.demo.Modules.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void createUser(@Param("password")String password,
                    @Param("username")String userName,
                    @Param("role")String role);

    int getUserId(@Param("username") String userName,
                  @Param("password") String passWord);
}
