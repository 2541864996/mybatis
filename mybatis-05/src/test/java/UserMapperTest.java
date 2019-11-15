import dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.User;
import util.Dog;
import util.MybatisUtils;

import java.util.List;

public class UserMapperTest {
    @Test
    public void getUserList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void getUserByIdAndName(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserByIdAndName(1, "小茂");
        System.out.println(user);
        sqlSession.close();

    }
    
    @Test
    public void deleteUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer integer = mapper.deleteUserById(6);
        sqlSession.close();
    }
}
