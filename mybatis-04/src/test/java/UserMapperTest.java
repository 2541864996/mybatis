import dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import pojo.User;
import util.MybatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {
    static Logger log4j=Logger.getLogger(UserMapperTest.class);
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
    public void log4j(){
       log4j.info("log4j:进入info方法");
       log4j.debug("log4j:进入debug方法");
       log4j.error("log4j:进入error方法");
    }

    @Test
    public void getUserListByLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("index",0);
        map.put("size",3);
        List<User> list = mapper.getUserListByLimit(map);
        for (User user : list) {
            System.out.println(user);
        }
        sqlSession.close();
    }
}
