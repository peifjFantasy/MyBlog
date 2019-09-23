package com.blog.service;

import com.blog.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**根据id查询一条评论*/
    Comment findCommentById(Integer id);

    /**添加一条评论*/
    Integer addComment(Comment comment);

    /**修改一条评论,只修改该条评论的审核状态*/
    Integer editComment(Comment comment);

    /**带参数查询评论记录*/
    List<Comment> findAllComment(Map<String,Object> map);

    /**带参数查询评论列表记录数*/
    Long findCommentTotal(Map<String,Object> map);

    /**根据评论id删除该评论*/
    Integer delCommentById(Integer id);

    /**根据该条评论所属的博客id删除该条评论*/
    Integer delCommentByBlogId(Integer id);

}
