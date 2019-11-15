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

- 带条件的查询

    1.接口中的方法
```java
//根据id查询用户
User getUserById(int id);
```
   2.xml中的sql语句
```xml
<!--parameterType参数类型-->
<select id="getUserById" parameterType="int" resultType="pojo.User">
    select * from user where id=#{id}
</select>
```
   3.测试
   获取到sqlSession利用他来获得接口，在调用方法
```java
@Test
public void getUserById(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDao userDao = sqlSession.getMapper(UserDao.class);
    User user = userDao.getUserById(1);
    System.out.println(user);
    sqlSession.close();
}
```
结果：![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-10.45.png?raw=true);

- 插入操作

   1.接口方法
```java
//插入一个用户
Integer addUser(User user);
```
   2.xml中的sql
```xml
<!--对象中的属性可以直接取出来-->
<insert id="addUser" parameterType="pojo.User">
    insert into user(id,name,pwd) values(#{id},#{name},#{pwd});
</insert>
```
   3.測試
```java
@Test
public void addUser(){
   SqlSession sqlSession = MybatisUtils.getSqlSession();
   UserDao userDao = sqlSession.getMapper(UserDao.class);

   Integer row=userDao.addUser(new User(5,"aixi","12345"));
   System.out.println(row);
    
   //提交事务
   sqlSession.commit();
   sqlSession.close();
}
```

- 修改

 1.接口方法
```java
//删除一个用户
Integer deleteUser(int id);
```
   2.xml中的sql
```xml
<update id="updateUser" parameterType="pojo.User">
    update user set name=#{name},pwd=#{pwd} where id=#{id};
</update>
```
   3.測試
```java
@Test
public void updateUser(){
   SqlSession sqlSession = MybatisUtils.getSqlSession();
   UserDao mapper = sqlSession.getMapper(UserDao.class);
   mapper.updateUser(new User(5,"艾希","54321"));
   sqlSession.commit();
   sqlSession.close();
}
```

- 删除

 1.接口方法
```java
//修改用户
Integer updateUser(User user);
```
   2.xml中的sql
```xml
<delete id="deleteUser" parameterType="int">
   delete from user where id=#{id};
</delete>
```
   3.測試
```java
@Test
public void deleteUser(){
   SqlSession sqlSession = MybatisUtils.getSqlSession();
   UserDao mapper = sqlSession.getMapper(UserDao.class);
   Integer integer = mapper.deleteUser(5);
   sqlSession.commit();
   sqlSession.close();
}
```
###万能的Map

1.接口
```java
//万能的Map
Integer addUser2(Map<String,Object> map);
```
2.mapper.xml中的sql语句
```xml
<!--万能Map 传递map的key-->
<insert id="addUser2" parameterType="map">
   insert into user(id,name,pwd) values(#{userId},#{userName},#{password});
</insert>
```
注意：

>在#{}中的变量名要在map的key中要存在
>parameterType:必须为map
3.测试
```java
@Test
public void addUser2(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDao userDao = sqlSession.getMapper(UserDao.class);

    Map<String,Object> map=new HashMap<String, Object>();
    map.put("userId",5);
    map.put("userName","艾克");
    map.put("password","888888");
    userDao.addUser2(map);

    //提交事务
    sqlSession.commit();
    sqlSession.close();
}
```

###模糊查詢(注意sql注入)
1.接口
```java
//模糊查询
List<User> getUserLike(String value);
```
2.sql配置
```xml
<select id="getUserLike" resultType="pojo.User">
  select * from user where name like "%"#{value}"%";
</select>
```
3.測試
```java
 @Test
public void getUserLike(){
   SqlSession sqlSession = MybatisUtils.getSqlSession();
   UserDao mapper = sqlSession.getMapper(UserDao.class);
   List<User> list = mapper.getUserLike("艾");
   for (User user : list) {
       System.out.println(user);
   }
   sqlSession.close();
}
```
第二种

1.接口
```java
List<User> getUserLike(String value);
```
2.sql配置
```xml
<select id="getUserLike" resultType="pojo.User">
   select * from user where name like #{value};
</select>
```
3.测试
```java
@Test
public void getUserLike(){
   SqlSession sqlSession = MybatisUtils.getSqlSession();
   UserDao mapper = sqlSession.getMapper(UserDao.class);
   List<User> list = mapper.getUserLike("%艾%");
   for (User user : list) {
      System.out.println(user);
   }
   sqlSession.close();
}
```

结果为:![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-12-47.png?raw=true);
