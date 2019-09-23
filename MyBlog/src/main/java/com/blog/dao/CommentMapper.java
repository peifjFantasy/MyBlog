package com.blog.dao;

import com.blog.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    /**根据id查询一条评论*/
    Comment getCommentById(Integer id);

    /**添加一条评论*/
    Integer insertComment(Comment comment);

    /**修改一条评论,只修改该条评论的审核状态*/
    Integer updateComment(Comment comment);

    /**带参数查询评论记录*/
    List<Comment> getAllComment(Map<String,Object> map);

    /**带参数查询评论列表记录数*/
    Long getCommentTotal(Map<String,Object> map);

    /**根据评论id删除该评论*/
    Integer deleteCommentById(Integer id);

    /**根据该条评论所属的博客id删除该条评论*/
    Integer deleteCommentByBlogId(Integer id);


}
