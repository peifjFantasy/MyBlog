package com.blog.service;

import com.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**无参数查询所有博客列表*/
    List<Blog> findAllBlog();

    /**根据id查询一条博客记录*/
    Blog findBlogById(Integer id);

    /**带参数查询博客列表*/
    List<Blog> findAllBlogByParam(Map<String,Object> map);

    /**带参数查询博客列表记录数*/
    Long findBlogTotal(Map<String,Object> map);

    /**插入一条记录*/
    Integer addBlog(Blog blog);

    /**修改一条记录*/
    Integer updBlog(Blog blog);

    /**根据id删除一条记录*/
    Integer delBlogById(Integer id);

    /**根据博客类型id查询包含的博客数量*/
    Integer findBlogByTypeId(Integer id);

    /**上一篇*/
    Blog findLastBlog(Integer id);

    /**下一篇*/
    Blog findNextBlog(Integer id);













}
