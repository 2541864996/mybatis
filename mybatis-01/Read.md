###Mybatis的第一個入門程序
- 连接数据库

  xml文件连接mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration核心配置文件-->
<configuration>
    <environments default="development">

        <environment id="development">
            <!--事务管理-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--驱动-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <!--&amp;转义符:相当于& useUnicode=true characterEncoding=UTF-8 支持中文-->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <!--数据库名字-->
                <property name="username" value="root"/>
                <!--数据库密码-->
                <property name="password" value="123456"/>
            </dataSource>
        </environment>

    </environments>
</configuration>
```
- 编写mybatis工具类
```java
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步:获取sqlSessionFactory对象
            String resource="mybatis-config.xml";
            InputStream inputStream= Resources.getResourceAsStream(resource);
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得SqlSession的实例
    //SqlSession 完全包含了面向数据库执行SQL命名所需的所有方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```
- 编写代码
   
   1.实体类
   
![avatar](https://github.com/2541864996/img/blob/master/img/1.png?raw=true);
```java
public class User {
    private int id;
    private String name;
    private String pwd;
```
   2.Dao接口
```java
public interface UserDao {
    List<User> getUserList();
}
```
   3.在xml中配置sql語句
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace=绑定一个dao/mapper接口-->
<mapper namespace="dao.UserDao">
    <!--id 接口的方法名  resultType:返回的结果类型-->
    <select id="getUserList" resultType="pojo.User">
        select * from user;
    </select>
</mapper>
```
  4.在mybatis总配置文件中将这个mapper.xml文件配置进去
```xml
    <!--每一个mapper.xml都需要在Mybatis核心文件中注册-->
        <mappers>
            <mapper resource="dao/UserMapper.xml"/>
        </mappers>
```
  5.测试
```java
 @Test
 public void getUserList(){
    //第一步:获得SqlSession对象
    SqlSession sqlSession= MybatisUtils.getSqlSession();

    /方式一:getMapper
    UserDao mapper = sqlSession.getMapper(UserDao.class);
    List<User> userList = mapper.getUserList();
    for (User user : userList) {
        System.out.println(user);
     }

     关闭sqkSession
    sqlSession.close();
}
```
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-00%EF%BC%9A00.png?raw=true);
