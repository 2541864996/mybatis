环境搭建

1.表创建
```sql
create table teacher(
    id int(10) not null primary key,
    name varchar(30) default null
    );

insert into teacher values(1,"艾老師");

create table student(
    id int(10) not null primary key,
    name varchar(30),
    tid int(10),
    foreign key(tid) references teacher(id)
    );

insert into student values(1,"艾希",1);
insert into student values(2,"艾克",1);
insert into student values(3,"金克斯",1);
insert into student values(4,"拉克丝",1);
insert into student values(5,"泽拉斯",1);
```
2.导入lombok
```xml
<dependency>
     <groupId>org.projectlombok</groupId>
     <artifactId>lombok</artifactId>
     <version>1.18.10</version>
</dependency>
```
3.创建实体类
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String name;
    //一个老师拥有多个学生
    private List<Student> students;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    private int tid;
}
```

4.创建Mapper接口
```java
StudentMapper
TeacherMapper
```
5.建立mapper的xml文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="mapper.TeacherMapper">
    
</mapper>


<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="mapper.StudentMapper">

</mapper>
```

6.在核心配置文件中绑定我们的Mapper接口或者xml文件
```xml
<mappers>
    <mapper resource="mapper/StudentMapper.xml"/>
    <mapper resource="mapper/TeacherMapper.xml"/>
</mappers>
```
7.测试
```java
    @Test
    public void getTeachers(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.getTeachers();
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
        sqlSession.close();
    }
```

###一对多
####第一种(关联查询)
```java
    //获取指定老师下的所有学生及老师信息
    Teacher getTeacher(@Param("tid") int id);
```
```xml
    <select id="getTeacher" resultMap="Teacher">
        select t.id tid,t.name tname,s.id sid,s.name sname,s.tid stid from teacher t,student s where t.id=s.tid and t.id=#{tid};
    </select>

    <resultMap id="Teacher" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <!--复杂的属性，我们要单独处理 对象association 集合collection
        javaType 指定属性的类型
        集合中的泛型信息，我们使用ofType获取
        -->
        <collection property="students" column="sid" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```
测试
```java
    @Test
    public void getTeacher(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
```
####第二种(嵌套查询)
1.接口方法
```java
    //获取指定老师下的所有学生及老师信息
    Teacher getTeacher2(@Param("tid") int id);
```
2.xml
```xml
    <select id="getTeacher2" resultMap="Teacher2">
        select  * from  teacher where id =#{tid};
    </select>
    <resultMap id="Teacher2" type="Teacher">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <collection property="students" column="id" select="getStudent">
            <result property="id" column="id"/>
            <result property="name" column="name"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
    <select id="getStudent" resultType="Student">
        select * from student where tid=#{tid};
    </select>
```
3.测试
```java
    @Test
    public void getTeacher2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher2(1);
        System.out.println(teacher);
        sqlSession.close();
    }
```

结果

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-16-15-09.png?raw=true)
