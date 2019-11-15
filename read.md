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
1.select
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
2.insert
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
3.update
```text
1.接口
2.mapper.xml中的sql语句
3.测试
```
4.deletc
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
5.万能的Map
我们实体类.或者数据库中的表，字段或者参数过多,我们可以考虑使用Map

注:
Map传递参数，直接在sql中取出key即可！【parameterType="map"】

对象传递参数，直接在sql中取对象属性即可！【parameterType="pojo.User"】

只有一个基本类型的参数情况下，直接在sql中取到！【parameterType="int"(也可不写)】

多个参数用Map，或者注解



