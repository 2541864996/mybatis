##一级缓存
测试步骤：
1.开启日志
2.接口方法
```java
    //根据id查询用户
    User queryUserById(@Param("id") int id);
```
3.sql
```xml
    <select id="queryUserById" resultType="User" >
        select * from user where id =#{id}
    </select>
```
4.测试(其中用了两遍相同的sql查询，观察日志输出)
```java
    @Test
    public void queryUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUserById(1);
        System.out.println(user);

        System.out.println("============================");
        User user1=mapper.queryUserById(1);
        System.out.println(user1);

        sqlSession.close();
    }
```
5.日志

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-13-14.png?raw=true)

其中2次相同的sql却只执行了一次(只要在一个SqlSession中查询相同的)

####缓存失效的情况
1.查询两个不同的东西
```java
    @Test
    public void queryUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUserById(1);
        System.out.println(user);

        System.out.println("============================");
        User user1=mapper.queryUserById(2);
        System.out.println(user1);

        sqlSession.close();
    }
```

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-13-23.png?raw=true)

2.执行了insert update delete就会让缓存失效

在两次相同查询中加入修改语句(就算修改其他当前查询的其他用户也会让缓存失效)
```java
    //修改用户信息
    Integer updateUser(User user);
```
```xml
    <update id="updateUser" parameterType="User">
        update user set  name = #{name} where id=#{id};
    </update>
```
```java
    @Test
    public void queryUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUserById(1);
        System.out.println(user);

        System.out.println("============================");
        user.setName("久久");
        Integer integer = mapper.updateUser(user);


        System.out.println("============================");
        User user1=mapper.queryUserById(1);
        System.out.println(user1);

        sqlSession.close();
    }
```
结果

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-13-35.png?raw=true)

3.查询不同的Mapper.xml

4.手动清理了缓存

利用 sqlSession.clearCache() 手动清理缓存

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-13-43.png?raw=true)


####小结:一级缓存默认是开启的，只在一次SqlSession中有效，也就是拿到连接和关闭连接这个区间
一级缓存就是一个Map

##二级缓存
1.开启缓存
```xml
   <!--显示的开启缓存-->
   <setting name="cacheEnabled" value="true"/>
```
2.在要使用二级缓存的mapper中开启
```xml
<!--在当前Mapper.xml中使用二级缓存-->
<cache/>
```
也可以自定义参数
```xml
<!--在当前Mapper.xml中使用二级缓存-->
<cache eviction="FIFO"
     flushInterval="60000" 
     size="512"
     readOnly="true"/>
```
可以在select标签中设置是否开启二级缓存 useCache
也可在insert这些标签中设置不刷新缓存 flushCache 
```xml
<select id="queryUserById" resultType="User" flushCache="true">
   select * from user where id =#{id}
</select>
```
测试：两个不同的SqlSession来获取相同数据
```java
    @Test
    public void queryUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUserById(1);
        System.out.println(user);
        sqlSession.close();

        SqlSession sqlSession2 = MybatisUtils.getSqlSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = mapper2.queryUserById(1);
        System.out.println(user2);
        sqlSession.close();
    }
```
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-14-11.png?raw=true)

注意:实体类需要实现序列化接口
小结：

    1.只有开启了二级缓存，在同一个Mapper下就有效
    2.所有的数据都会先放在一级缓存中
    3.只有当会话提交，或者关闭的时候，才会提交到二级缓存中
   
###缓存原理
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-15-40.png?raw=true)
