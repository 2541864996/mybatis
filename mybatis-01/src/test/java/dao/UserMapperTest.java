package dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.User;
import util.MybatisUtils;

import java.util.List;

public class UserMapperTest {
    @Test
    public void getUserList(){
        //第一步:获得SqlSession对象
        SqlSession sqlSession= MybatisUtils.getSqlSession();

        //方式一:getMapper
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }

        //关闭sqkSession
        sqlSession.close();
    }
}
