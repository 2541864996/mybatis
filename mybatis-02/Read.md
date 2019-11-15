###属性(properties)
我们可以使用properties属性来引用配置文件

1.比如我们有一个连接数据库的配置文件
```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&amp;characterEncoding=UTF-8
username=root
password=123456
```
2.我们就可以利用mybatis中的properties标签引入他
```xml
<properties resource="db.properties"/>
```
3.我们也可以给他添加一些其他的
```xml
 <!--引入数据库的配置文件-->
<properties resource="db.properties">
   <property name="username" value="root"/>
</properties>
```
4.我们就可以在连接数据库时读取出来(${xxx} 固定语法)
```xml
<environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
```
注意：如果配置文件和property中都有username这个的时候，只会读取配置文件中的(配置文件没有才用property中的)

![avatar](https://github.com/2541864996/img/blob/master/img/2019-15-13-57.png?raw=true);

###给类设置别名
####第一种
1.在mybatis核心配置文件中
```xml
<!--给类全名起别名-->
<typeAliases>
    <typeAlias type="pojo.User" alias="User"/>
</typeAliases>
```
2.我们在mapper.xml中写返回值类型就可以改成别名的样子
```xml
<select id="getUserList" resultType="User">
    select * from user;
</select>
```
####第二种
添加要扫描的包，他会自动扫描包中的类，别名就是当前包中类名(首字母小写)
```xml
 <!--给类全名起别名-->
    <typeAliases>
        <!--第二种-->
        <package name="pojo"/>
    </typeAliases>
```
2.在mapper.xml中使用别名
```xml
<select id="getUserList" resultType="user">
  select * from user;
</select>
```
3.如果不喜欢默认的扫描生成的类别名,可以使用@Aliases在类上自定义
```java
@Alias("UserA")
public class User {
    private int id;
    private String name;
    private String pwd;
}
```
#####建议:在类不是很多的情况下可以使用第一种 如果类比较多建议使用第二种