环境配置
1.导包
2.mybatis核心配置
3.工具类
4.建表
```sql
create table blog (
    id varchar(50) not null comment '博客id',
    title varchar(100) not null comment '博客标题',
    author varchar(30) not null comment '博客作者',
    create_time datetime not null comment '创建时间',
    views int(30) not null comment '浏览量'
);
```
5.实体类
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private String id ;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
```
6.接口
7.接口xml
8.将xml配入mybatis核心xml中

9.创建一个可以生成UUID的工具类
```java
public class IDUtils {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Test
    public void getUUID(){
        System.out.println(IDUtils.getId());
        System.out.println(IDUtils.getId());
        System.out.println(IDUtils.getId());
    }
}
```

10.开启驼峰命名(由于类中为createTime 表中为 create_time 我们即可在mybatis的总配置文件中开启驼峰命名)
```xml
<!--开启驼峰命名-->
<setting name="mapUnderscoreToCamelCase" value="true"/>
```

11.利用测试类插入数据
```java
    @Test
    public void addBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog=new Blog();
        blog.setId(IDUtils.getId());
        blog.setTitle("Java太简单了吧");
        blog.setAuthor("小茂");
        blog.setCreateTime(new Date());
        blog.setViews(999);
        int i = mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("spring很好呀");
        int i1 = mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("mybatis还是很简单的");
        int i2 = mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("动态SQL");
        int i3 = mapper.addBlog(blog);
        sqlSession.close();
    }
```

### \<if>  & \<where>
接口方法
```java
    //查询博客
    List<Blog> queryBlogF(Map map);
```
xml中的动态SQL
```xml
    <select id="queryBlogF" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <if test="title != null">
                title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </where>
    </select>
```
###注：where标签 
    1.如果where标签中第一个if成立 则会在sql上添加一个where 
    2.如果第二个成立那么就会将and 去掉改为 where 
    3.一个都不成立where自动去掉
测试

这样是查询所有的信息
```java
    @Test
    public void queryBlogF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String> map=new HashMap<String,String>();
        List<Blog> blogs = mapper.queryBlogF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
查询指定标题的信息
```java
    @Test
    public void queryBlogTitle(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String> map=new HashMap<String,String>();
        map.put("title","Java太简单了吧");
        List<Blog> blogs = mapper.queryBlogF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
查询指定作者的信息
```java
    @Test
    public void queryBlogAuthor(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String> map=new HashMap<String,String>();
        map.put("author","小茂");
        List<Blog> blogs = mapper.queryBlogF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
查询指定作者和标题的信息
```java
@Test
    public void queryBlogTitleAuthor(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String> map=new HashMap<String,String>();
        map.put("title","Java太简单了吧");
        map.put("author", "小茂");
        List<Blog> blogs = mapper.queryBlogF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
###choose (when, otherwise)
    1.相当于java中的switch 
    2.choose ：switch
    3.when:case
    4.otherwise：default
接口方法
```java
    //查询博客 choose
    List<Blog> queryBlogChoose(Map map);
```
xml中的SQL
```sql
<select id="queryBlogChoose" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <choose>
                <when test="title != null">
                    title =#{title}
                </when>
                <when test="author !=null">
                    and author=#{author}
                </when>
                <otherwise>
                    and views=999
                </otherwise>
            </choose>
        </where>
    </select>
```
不同的测试:

map什么值都没默认走otherwise
```java
    @Test
    public void queryBlogChooseOtherwise(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String>map=new HashMap<String, String>();
        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
map中传入一个key为title
```java
    @Test
    public void queryBlogChooseWhenTitle(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String>map=new HashMap<String, String>();
        map.put("title","Java太简单了吧");
        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
map中传入一个key为author
```java
    @Test
    public void queryBlogChooseWhenAuthor(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String>map=new HashMap<String, String>();
        map.put("author","小茂");
        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
如果又有title又有author,就只能先满足第一个when
```java
    @Test
    public void queryBlogChooseWhenAuthor(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String>map=new HashMap<String, String>();
        map.put("author","小茂");
        map.put("title","Java太简单了吧");
        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
###trim (where, set)
#### \<where>  
#### \<set>
    1.修改语句中会用到set
    2.如果map中只有title这个key的话 没有set 后面的逗号就会让sql报错
    3.会自动在sql中加set 和智能去逗号
接口方法
```java
    //更新博客
    Integer updateBlog(Map map);
```
xml中sql语句
```xml
    <update id="updateBlog" parameterType="map">
        update blog
        <set>
            <if test="title != null">
                title =#{title},
            </if>
            <if test="author !=null">
                author =#{author}
            </if>
        </set>
        where id=#{id}
    </update>
```
测试
```java
    @Test
    public void updateBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,String> map=new HashMap<String, String>();
        map.put("id","4904ce3338fe4777bac5698baef66bcf");
        map.put("title","修改了标题");
        map.put("author","茂茂");
        Integer integer = mapper.updateBlog(map);
        sqlSession.close();
    }
```
###SQL片段 \<sql>
有的时候，我们可能会将一些公共部分抽取出来，方便复用
   
    1.使用sql标签抽取公共部分
    2.在需要使用的地方使用include标签引用即可
注意：
- 最好基于单表定义sql片段
- 不要存在where标签

例如:
```xml
    <sql id="f-title-author">
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </sql>
    <select id="queryBlogF" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <!--refid sql片段的id名-->
            <include refid="f-title-author"></include>
        </where>
    </select>
```
```xml
<select id="queryBlogF" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <if test="title != null">
                title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </where>
    </select>
```
两种方式都是一样的,用\<sql>标签定义片段sql语句 用\<include>标签根据\<sql>中的id名引入就好了引入就好了

###foreach
接口
```java
//查询第1-2-3-4号记录的博客
List<Blog> queryBlogForeach(Map map);
```
xml中的sql
```xml
    <select id="queryBlogForeach" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <foreach collection="ids" item="id" open="and (" close=")" separator="or" >
                id = #{id}
            </foreach>
        </where>
    </select>
```
測試
```java
    @Test
    public void queryBlogForeach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,Object> map=new HashMap<String, Object>();
        List<String> list=new ArrayList<String>();
        list.add("513bb2a35a074aa9804a5f2046b3f406");
        list.add("adaed22c43814b52a3de7bd87d57f610");
        list.add("9571b2108a7646f3833877759af1f475");
        list.add("4904ce3338fe4777bac5698baef66bcf");
        map.put("ids",list);
        List<Blog> blogs = mapper.queryBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```
![avatar](https://github.com/2541864996/img/blob/master/img/2019-11-17-11-59.png?raw=true)