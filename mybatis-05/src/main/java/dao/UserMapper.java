package dao;

import org.apache.ibatis.annotations.*;
import pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //查询全部用户
    @Select("select * from user")
    List<User> getUserList();

    //根据用户id和name查询
    @Select("select * from user where id=#{id} and name=#{name}")
    User getUserByIdAndName(@Param("id") int id,@Param("name") String name);

    //删除一个用户
    @Delete("delete from user where id=#{id}")
    Integer deleteUserById(int id);
}
