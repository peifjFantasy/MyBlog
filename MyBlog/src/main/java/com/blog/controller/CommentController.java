package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.service.BlogService;
import com.blog.service.BloggerService;
import com.blog.service.CommentService;
import com.blog.util.ResponseUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: john
 * @Date: 2019/9/18 19:11
 * @Description: 处理前台用户发表评论
 * @version: 1.0
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;


    /**
     * 功能描述：保存前台用户提交的评论
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/saveComment")
    public String saveComment(Comment comment, @RequestParam("imageCode")String imageCode,
                              HttpServletRequest request, HttpServletResponse response,
                              HttpSession session) throws Exception {
        String sRand= (String) session.getAttribute("sRand");
        JSONObject result=new JSONObject();
        Integer flag=0;
        if(!imageCode.equals(sRand)){
            result.put("info",Boolean.FALSE);
            result.put("errorInfo","验证码输入错误！");
        }else {
            //获取评论用户的IP地址
            String userIp = request.getRemoteAddr();
            comment.setUserIp(userIp);
            if(comment.getId()==null){//新增评论
                flag=commentService.addComment(comment);
                Blog blog = blogService.findBlogById(comment.getBlog().getId());
                //对应博客的评论数+1
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.updBlog(blog);
            }
        }
        if(flag>0){
            result.put("info",Boolean.TRUE);
        }else{
            result.put("info",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }
















}
