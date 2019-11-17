package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.User;

import java.util.List;

public interface UserMapper {

    //根据id查询用户
    User queryUserById(@Param("id") int id);

    //修改用户信息
    Integer updateUser(User user);
}
