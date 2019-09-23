package com.blog.service.impl;

import com.blog.dao.CommentMapper;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment findCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    public Integer addComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    public Integer editComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    @Override
    public List<Comment> findAllComment(Map<String, Object> map) {
        return commentMapper.getAllComment(map);
    }

    @Override
    public Long findCommentTotal(Map<String, Object> map) {
        return commentMapper.getCommentTotal(map);
    }

    @Override
    public Integer delCommentById(Integer id) {
        return commentMapper.deleteCommentById(id);
    }

    @Override
    public Integer delCommentByBlogId(Integer id) {
        return commentMapper.deleteCommentByBlogId(id);
    }
}
