package com.blog.controller.admin;


import com.blog.entity.Comment;
import com.blog.service.CommentService;
import com.blog.util.DateJsonValueProcessor;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    /**
     * 展示所有用户评论记录
     */
    @RequestMapping("/showAllComment")
    public String showAllComment(@RequestParam(value = "page",required = false) String page,
                                 @RequestParam(value = "rows",required = false) String rows,
                                 @RequestParam(value = "state",required = false) String state,
                                 HttpServletResponse response){
        Integer pageNumber=Integer.valueOf(page);
        Integer pageSize=Integer.valueOf(rows);
        //设置分页状态参数
        Map<String,Object> map=new HashMap<>();
        map.put("pageStart",(pageNumber-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("state",state);
        //放入json
        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        result.put("rows", JSONArray.fromObject(commentService.findAllComment(map),jsonConfig));
        result.put("total",commentService.findCommentTotal(map));
        try {
            ResponseUtil.write(response,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除评论记录
     */
    @RequestMapping("/deleteComment")
    public String deleteComment(@RequestParam(value = "ids",required = false)String ids,
                                HttpServletResponse response){
        //接收前端用“，”连接起来的字符串，并用“，”拆分为一个数组
        String[] strings = ids.split(",");
        JSONObject result=new JSONObject();
        int flag=0;
        for (String s : strings) {
            flag=commentService.delCommentById(Integer.valueOf(s));
        }
        if(flag>0){
            result.put("info",Boolean.TRUE);
        }else {
            result.put("info",Boolean.FALSE);
        }
        try {
            ResponseUtil.write(response,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 审核用户评论记录
     */
    @RequestMapping("/commentReview")
    public String commentReview(@RequestParam(value = "ids",required = false)String ids,
                                @RequestParam(value = "state",required = false)String state,
                                HttpServletResponse response){
            //接收前端用“，”连接起来的字符串，并用“，”拆分为一个数组
            String[] strings = ids.split(",");
            JSONObject result=new JSONObject();
            int flag=0;
            Comment comment=new Comment();
            for (String s : strings) {
                comment=commentService.findCommentById(Integer.valueOf(s));
                comment.setState(Integer.valueOf(state));
                flag+=commentService.editComment(comment);
            }
            if(flag>0){
                result.put("info",Boolean.TRUE);
            }else {
                result.put("info",Boolean.FALSE);
            }
            try {
                ResponseUtil.write(response,result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

    }



















}
