package com.blog.dao;

import com.blog.entity.BlogType;

import java.util.List;

public interface BlogTypeMapper {

    /**无参数查询所有博客类型列表*/
    List<BlogType> getAllBlogType();

    /**根据id查询一条博客类型记录*/
    BlogType getBlogTypeById(Integer id);

    /**带参数分页查询博客类型列表*/
    List<BlogType> getAllBlogTypeByPage(Integer pageStart,Integer pageSize);

    /**查询博客类型列表记录数*/
    Long getBlogTypeTotal();

    /**插入一条记录*/
    Integer insertBlogType(BlogType blogType);

    /**修改一条记录*/
    Integer updateBlogType(BlogType blogType);

    /**根据id删除一条记录*/
    Integer deleteBlogTypeById(Integer id);










}
