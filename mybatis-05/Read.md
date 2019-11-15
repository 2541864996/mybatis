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



