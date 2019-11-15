import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


import dao.UserMapper;
import pojo.User;
import util.MybatisUtils;

public class UserMapperTest {
	 @Test
	 public void getUserList(){
	 	//第一步:获得SqlSession对象
		 SqlSession sqlSession= MybatisUtils.getSqlSession();

		 //方式一:getMapper
		 UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		 List<User> userList = mapper.getUserList();
		 for (User user : userList) {
		 	System.out.println(user);
		 }
		 //关闭sqkSession
		 sqlSession.close();
	 }

}
