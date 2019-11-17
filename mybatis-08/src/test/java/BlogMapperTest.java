import mapper.BlogMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.Blog;
import util.IDUtils;
import util.MybatisUtils;

import java.util.*;

public class BlogMapperTest {
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
}
