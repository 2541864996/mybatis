#Mybatis
##简介
1.1、什么是Mybatis

![avatar](https://mybatis.org/images/mybatis-logo.png);

- MyBatis 是一款优秀的 **持久层框架**
- 它支持定制化 SQL、存储过程以及高级映射。
- MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。
- MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录
- MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。
- 2013年11月迁移到Github。

如何获得Mybatis
- maven仓库：
```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.3</version>
</dependency>
```
Github: https://github.com/mybatis/mybatis-3

中文文档： https://mybatis.org/mybatis-3/zh/index.html

1.2、持久化
数据持久化（一个动作，将数据持久化）
- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：断点即失
- 数据库(jdbc),io文件持久化
- 生活:冷藏,罐头
为什么需要持久化？
- 有一些对象，不能让他丢掉
- 内存太贵

1.3、持久层
（一个概念）
- 完成持久化工作的代码块
- 层界限十分明显

1.4为什么需要Mybatis
- 帮助程序员将数据存入到数据库中
- 方便
- 传统的jdbc代码太复杂了
- 更容易上手（技术没有高低之分，只有使用技术的人有差别）
- 优点

   1.简单易学
   
   2.解除sql与程序代码的耦合:通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
   
   3.提供映射标签，支持对象与数据库的orm字段关系映射
   
   4.提供对象关系映射标签，支持对象关系组建维护
   
   5.提供xml标签，支持编写动态sql。
   
###使用[mybatis-01]
###CRUD
###1.select
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
###2.insert
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
###3.update
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
###4.deletc
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
注意：
增删改需要提交事务！
```xml
qlSession.commit();
```
###5.万能的Map
我们实体类.或者数据库中的表，字段或者参数过多,我们可以考虑使用Map

注:
Map传递参数，直接在sql中取出key即可！【parameterType="map"】

对象传递参数，直接在sql中取对象属性即可！【parameterType="pojo.User"】

只有一个基本类型的参数情况下，直接在sql中取到！【parameterType="int"(也可不写)】

多个参数用Map，或者注解

###6.配置解析
- mybatis-config.xml
- Mybatis的配置文件包含了会深深影响Mybatis行为的设置和属性信息
```text
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）
```
###环境配置(environments)
MyBatis可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

例如:

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-13-14.png?raw=true);

Mybatis默认的事务管理器就是JDBC,连接池：POOLED

####属性(properties)
详情见[mybatis-02]

我们可以使用properties属性来引用配置文件

- 编写数据库连接的db.properties文件
```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&amp;characterEncoding=UTF-8
username=root
password=123456
```
- 将数据库连接配置文件导入到mybatis的核心xml中
```xml
<!--引入数据库的配置文件-->
<properties resource="db.properties"></properties>
```
1.可以利用property 设置其他的配置
```xml
<property name="username" value="root"/>
```
2.如果配置文件中和property标签中都配置了username,只会读取配置文件中的
- 读取信息连接数据库
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
####类型别名(typeAliases)
- 类型别名是为java类型设置一个短的名字
- 存在的意义仅在于用来减少类完全限定名的冗余
- 常见的 Java 类型内建的相应的类型别名。它们都是不区分大小写的，注意对基本类型名称重复采取的特殊命名风格。
```text
别名	        映射的类型
_byte	        byte
_long	        long
_short	        short
_int	        int
_integer	    int
_double	        double
_float	        float
_boolean    	boolean
string	        String
byte	        Byte
long	        Long
short	        Short
int	            Integer
integer	        Integer
double	        Double
float	        Float
boolean	        Boolean
date	        Date
decimal	        BigDecimal
bigdecimal	    BigDecimal
object	        Object
map	            Map
hashmap	        HashMap
list	        List
arraylist	    ArrayList
collection	    Collection
iterator	    Iterator
```
####设置
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-14-43.png?raw=true);
####其他配置
- typeHandlers（类型处理器）
- objectFactory（对象工厂）
- plugins（插件）
```text
1.mybatis-generator-core
2.mybatis-plus
3.通用mapper
```

####映射器(sappers)
MapperRegistry：注册绑定我们的Mapper文件

官网有4种方式

[Mybatis](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)
我们使用配置路径的就好了
```xml
<mappers>
   <mapper resource="dao/UserMapper.xml"/>
</mappers>
```

###生命周期和作用域
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-15-21.png?raw=true);

不同作用域和生命周期类是至关重要的，因为错误的使用会导致非常严重的并发问题。

SqlSessionFactoryBuilder：
- 一旦创建了SqlSessionFactory,就不再需要它了
- 局部变量

SqlSessionFactory：
- 说白了就是可以想象为：数据库连接池
- 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例
- SqlSessionFactory 的最佳作用域是应用作用域
- 最简单的就是使用单例模式或者静态单例模式

SqlSession：
- 连接到连接池的一个请求
- SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
- 用完之后需要赶紧关闭，否则资源被占用

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-15-15-22.png?raw=true);

这里面的每一个Mapper就代表一个具体业务

###解决属性名和字段名不一致的问题
数据库中的字段