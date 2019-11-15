###ResultMap 
1.由于类中属性名字和数据库中字段名字不匹配导致查不出东西来

如：
>实体来
```java
public class User {
    private int id;
    private String name;
    private String password;
}
```
>表字段
```text
id
name
pwd
```
>xml中的sql语句
```xml
 <select id="getUserList" resultType="User">
    select * from user;
</select>
```
>测试
```java
 @Test
public void getUserList(){
	SqlSession sqlSession= MybatisUtils.getSqlSession();

	UserMapper mapper = sqlSession.getMapper(UserMapper.class);
	List<User> userList = mapper.getUserList();
	for (User user : userList) {
		System.out.println(user);
	}

	sqlSession.close();
}
```
结果:

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-16-07.png?raw=true)

由于我们类中的password属性在表中没有这个字段，所以没有获取回来解决方法 配置ResultMap

在Mapper.xml文件中添加一个ResultMap标签
```xml
<!--id相当于这个map的唯一标识 type为某个类：可以填入设置的类别名-->
<resultMap id="UserMap" type="User">
     <!--property是在类中的名字 column是表中的字段名 将他们两关联起来 查询出来的pwd的值就赋到password上-->
     <result property="password" column="pwd"/>
</resultMap>
```
在原来select标签中添加resulMap="一个map的id名"
```xml
<select id="getUserList" resultMap="UserMap">
    select * from user;
</select>
```
测试
```java
@Test
public void getUserList(){
	SqlSession sqlSession= MybatisUtils.getSqlSession();

	UserMapper mapper = sqlSession.getMapper(UserMapper.class);
	List<User> userList = mapper.getUserList();
	for (User user : userList) {
		System.out.println(user);
	}

	sqlSession.close();
}
```
结果

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-16-13.png?raw=true)
