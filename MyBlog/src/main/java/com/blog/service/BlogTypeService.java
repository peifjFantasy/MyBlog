package com.blog.service;

import com.blog.entity.BlogType;
import com.blog.entity.PageBean;

import java.util.List;
import java.util.Map;

public interface BlogTypeService {

    /**无参数查询所有博客类型列表*/
    List<BlogType> findAllBlogType();

    /**根据id查询一条博客类型记录*/
    BlogType findBlogTypeById(Integer id);

    /**带参数查询博客类型列表*/
    PageBean findAllBlogTypeByPage(Integer pageNumber,Integer pageSize);


    /**插入一条记录*/
    Integer addBlogType(BlogType blogType);

    /**修改一条记录*/
    Integer updBlogType(BlogType blogType);

    /**根据id删除一条记录*/
    Integer delBlogTypeById(Integer id);

}
