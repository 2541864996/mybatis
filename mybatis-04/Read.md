###日志
####方式一(STDOUT_LOGGING标准日志输出)
在mybatis-config.xml文件中添加(注意标签放置位置)
```xml
<settings>
   <!--设置日志-->
   <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-17-40.png?raw=true)
####方式2(LOG4J)
1.添加依赖
```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```
2.log4j.properties的配置
```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/wei.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```
3.配置log4j为日志实现
```xml
<settings>
     <!--设置日志-->
     <setting name="logImpl" value="LOG4J"/>
</settings>
```
#####简单的使用
1.在要使用Log4j的类中,导入包
```java
import org.apache.log4j.Logger;
```
2.日志对象,参数为当前类的class
```java
 static Logger log4j=Logger.getLogger(UserMapperTest.class);
```
3.日志的级别
```java
//普通输出
log4j.info("log4j:进入info方法");
//DEBUG输出
log4j.debug("log4j:进入debug方法");
//紧急情况输出
log4j.error("log4j:进入error方法");
```
###分页
1.接口
```java
//分页查询
List<User> getUserListByLimit(Map<String, Integer> map);
```
2.sql
```xml
<select id="getUserListByLimit" parameterType="map" resultMap="UserMap">
    select * from user limit #{index},#{size};
</select>
```
3.测试
```java
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
```
结果

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-20-30.png?raw=true)

