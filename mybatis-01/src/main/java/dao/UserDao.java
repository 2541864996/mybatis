package dao;

import pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //模糊查询
    List<User> getUserLike(String value);

    //查询全部用户
    List<User> getUserList();

    //根据id查询用户
    User getUserById(int id);

    //插入一个用户
    Integer addUser(User user);

    //万能的Map
    Integer addUser2(Map<String,Object> map);

    //修改用户
    Integer updateUser(User user);

    //删除一个用户
    Integer deleteUser(int id);
}
