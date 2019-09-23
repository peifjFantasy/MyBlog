package com.blog.dao;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    /**无参数查询所有博客列表*/
    List<Blog> getAllBlog();

    /**根据id查询一条博客记录*/
    Blog getBlogById(Integer id);

    /**带参数查询博客列表*/
    List<Blog> getAllBlogByParam(Map<String,Object> map);

    /**带参数查询博客列表记录数*/
    Long getBlogTotal(Map<String,Object> map);

    /**插入一条记录*/
    Integer insertBlog(Blog blog);

    /**修改一条记录*/
    Integer updateBlog(Blog blog);

    /**根据id删除一条记录*/
    Integer deleteBlogById(Integer id);

    /**根据博客类型id查询其包含的博客数量*/
    Integer getBlogByTypeId(Integer id);

    /**上一篇*/
    Blog getLastBlog(Integer id);

    /**下一篇*/
    Blog getNextBlog(Integer id);


}
