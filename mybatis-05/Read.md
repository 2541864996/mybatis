###注解开发
注解
```text
@Select() 查询
@Update() 修改
@Insert() 插入
@Delete() 删除
```
1.接口
```java
 //查询全部用户
    @Select("select * from user")
    List<User> getUserList();
```
2.配置mybatis核心配置文件
```xml
 <mappers>
    <mapper class="dao.UserMapper"/>
 </mappers>
```
3.测试
```java
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
```
结果:
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-21-45.png?raw=true)


###接口方法多参数时
1.接口(方法传入2个参数)
```java
//根据用户id和name查询
@Select("select * from user where id=#{id} and name=#{name}")
User getUserByIdAndName(@Param("id") int id,@Param("name") String name);
```
2.上面配置了mybatis的核心xml就不配置了

3.测试
```java
@Test
public void getUserByIdAndName(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = mapper.getUserByIdAndName(1, "小茂");
    System.out.println(user);
    sqlSession.close();
}
```

注：@Param("")中的参数名字就是当前在sql中用到的变量名


###设置自动提交事务
```java
//传入true就代表开启自动提交事务
return sqlSessionFactory.openSession(true);
```
开启自动提交事务执行增删改就不用  sqlSession.commit();

例如:

1.修改MybatiUtils中的getSqlSession开启自动提交事务
```java
//既然有了 SqlSessionFactory，顾名思义，我们可以从中获得SqlSession的实例
//SqlSession 完全包含了面向数据库执行SQL命名所需的所有方法
   public static SqlSession getSqlSession(){
    //传入true就代表开启自动提交事务
    return sqlSessionFactory.openSession(true);
}
```
2.Mapper接口
```java
//删除一个用户
@Delete("delete from user where id=#{id}")
Integer deleteUserById(int id);
```
3.测试
```java
    @Test
    public void deleteUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer integer = mapper.deleteUserById(6);
        sqlSession.close();
    }
```
