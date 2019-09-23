package com.blog.service.impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.CommentMapper;
import com.blog.entity.Blog;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Blog> findAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public Blog findBlogById(Integer id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> findAllBlogByParam(Map<String, Object> map) {
        return blogMapper.getAllBlogByParam(map);
    }

    @Override
    public Long findBlogTotal(Map<String, Object> map) {
        return blogMapper.getBlogTotal(map);
    }

    @Override
    public Integer addBlog(Blog blog) {
        return blogMapper.insertBlog(blog);
    }

    @Override
    public Integer updBlog(Blog blog) {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public Integer delBlogById(Integer id) {
        /**
         * 删除博客之前先删除该博客下的评论（因为外键约束的存在）
         */
        commentMapper.deleteCommentByBlogId(id);
        return blogMapper.deleteBlogById(id);
    }

    @Override
    public Integer findBlogByTypeId(Integer id) {
        return blogMapper.getBlogByTypeId(id);
    }

    @Override
    public Blog findLastBlog(Integer id) {
        return blogMapper.getLastBlog(id);
    }

    @Override
    public Blog findNextBlog(Integer id) {
        return blogMapper.getNextBlog(id);
    }
}
