package dao;

import pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

    //插入一个用户
    Integer addUser(User user);

    //修改用户
    Integer updateUser(User user);

    //删除一个用户
    Integer deleteUser(int id);
}
