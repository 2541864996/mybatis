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
public class Student {
    private int id;
    private String name;
    //学生需要关联一个老师
    private Teacher teacher;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    //学生需要关联一个老师
    private Teacher teacher;
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

7.测试查询是否成功
```java
public interface TeacherMapper {
    Teacher getTeacherById(@Param("id") int id);
}
```
```xml
<select id="getTeacherById" parameterType="int" resultType="Teacher">
   select * from teacher where id=#{id};
</select>
```
```java
    @Test
    public void getTeacherById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacherById = mapper.getTeacherById(1);
        System.out.println(teacherById);
        sqlSession.close();
    }
```

###多对一(多个学生对应一个老师)
- 由于每个学生类中都有一个属性是老师类
- 我们学生表中只有当前学生对应的老师的id
- 我们可以利用association查询出来老师的值

###第一种（嵌套查询）

先查询出所有学生的信息
```xml
<select id="getStudents" resultMap="StudentTeacher">
  select * from student;
</select>
```
可以利用resultMap中的association
```xml
    <!--type 为当前返回的类型，当前返回是一个Student-->
    <resultMap id="StudentTeacher" type="Student">
        <!--类中属性名 和表中字段名对应-->
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--复杂的属性，我们需要单独处理 对象：association 集合：collection-->
        <!--property 类中的属性名 column 表中的字段名 javaType 当前这个属性在类中的类型是什么  select 子查询语句-->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>

    <!--#{id} 就相当于上面map中  column="tid"的值-->
    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id=#{id};
    </select>
```

###第二种(关联查询)
xml配置:
```xml
    <select id="getStudents2" resultMap="StudentTeacher2">
        select s.id sid,s.name sname,t.id tid,t.name tname from student s,teacher t where s.tid =t.id;
    </select>
    <resultMap id="StudentTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <!--复杂的类型用association  property在student类中的属性名 javaType 设置这个属性的类型 -->
        <association property="teacher" javaType="Teacher">
            <!--property 这个是这个属性类型中的属性名 column 上面查询的别名-->
            <result property="id" column="tid"/>
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```

测试
```java
    @Test
    public void getStudents2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudents2();
        for (Student student : students) {
            System.out.println(student);
        }
        sqlSession.close();
    }
```

结果都为

![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-16-14-20.png?raw=true);

