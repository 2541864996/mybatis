package mapper;

import pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //插入数据
    int addBlog(Blog blog);

    //查询博客 if
    List<Blog> queryBlogF(Map map);

    //查询博客 choose
    List<Blog> queryBlogChoose(Map map);

    //更新博客
    Integer updateBlog(Map map);

    //查询第1-2-3-4号记录的博客
    List<Blog> queryBlogForeach(Map map);
}
