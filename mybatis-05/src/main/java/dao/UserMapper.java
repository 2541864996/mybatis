package dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //查询全部用户
    @Select("select * from user")
    List<User> getUserList();
}
