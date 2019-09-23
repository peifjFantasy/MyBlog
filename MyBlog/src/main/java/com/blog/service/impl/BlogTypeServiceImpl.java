package com.blog.service.impl;

import com.blog.dao.BlogTypeMapper;
import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

    @Autowired
    private BlogTypeMapper blogTypeMapper;

    @Override
    public List<BlogType> findAllBlogType() {
        return blogTypeMapper.getAllBlogType();
    }

    @Override
    public BlogType findBlogTypeById(Integer id) {

        return blogTypeMapper.getBlogTypeById(id);
    }

    @Override
    public PageBean findAllBlogTypeByPage(Integer pageNumber,Integer pageSize) {
        PageBean pageBean=new PageBean();
        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);
        long count=blogTypeMapper.getBlogTypeTotal();
        pageBean.setCount(count);
        pageBean.setTotalPage(count%pageSize==0? count/pageSize:count/pageSize+1);
        pageBean.setList(blogTypeMapper.getAllBlogTypeByPage((pageNumber-1)*pageSize,pageSize));
        return pageBean;
    }

    @Override
    public Integer addBlogType(BlogType blogType) {
        return blogTypeMapper.insertBlogType(blogType);
    }

    @Override
    public Integer updBlogType(BlogType blogType) {
        return blogTypeMapper.updateBlogType(blogType);
    }

    @Override
    public Integer delBlogTypeById(Integer id) {
        return blogTypeMapper.deleteBlogTypeById(id);
    }
}
