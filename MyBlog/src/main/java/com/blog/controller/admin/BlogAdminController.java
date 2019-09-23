package com.blog.controller.admin;


import com.blog.entity.Blog;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.util.DateJsonValueProcessor;
import com.blog.util.ResponseUtil;
import com.blog.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Autowired
    private BlogService blogService;

    private BlogIndex blogIndex=new BlogIndex();

    /**
     * 添加一条博客信息
     */
    @RequestMapping("/saveBlog")
    public String saveBlog(Blog blog, HttpServletResponse response) throws IOException {
        Integer flag=0;
        if(blog.getId()==null){//新增
            flag=blogService.addBlog(blog);
            //把新增的博客同时放入lucene索引库中
            blogIndex.addIndexToBlog(blog);
        }else {//修改
            flag=blogService.updBlog(blog);
            //把修改后的博客同时更新到lucene索引库中
            blogIndex.updIndexToBlog(blog);
        }
        JSONObject result=new JSONObject();
        if(flag>0){
            result.put("success",Boolean.valueOf(true));
        }else {
            result.put("error",Boolean.valueOf(false));
        }
        try {
            ResponseUtil.write(response,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 展示所有博客记录
     */
    @RequestMapping("/findAllBlogByParam")
    public String findAllBlogByParam(@RequestParam(value = "page",required = false)String page,
                                     @RequestParam(value = "rows",required = false)String rows,
                                     Blog blog,HttpServletResponse response){
        Integer pageNumber=Integer.valueOf(page);
        Integer pageSize=Integer.valueOf(rows);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageStart",(pageNumber-1)*pageSize);
        map.put("pageSize",pageSize);
        //如果title不是空串，在其前后加%，以支持模糊查询
        map.put("title", StringUtil.formatLike(blog.getTitle()));
        //封装数据到json
        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
        //封装的同时把Date类型的数据转换为字符串，用于前台显示
        result.put("rows",JSONArray.fromObject(blogService.findAllBlogByParam(map),jsonConfig));
        result.put("total",blogService.findBlogTotal(map));
        try {
            ResponseUtil.write(response,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除博客记录
     */
    @RequestMapping("/deleteBlog")
    public String deleteBlog(HttpServletResponse response,
                             @RequestParam("ids") String ids)throws Exception{
        String[] strings = ids.split(",");
        Integer flag=0;
        JSONObject result=new JSONObject();
        for (String id : strings) {
            flag+=blogService.delBlogById(Integer.valueOf(id));
            //删除博客的同时也删除lucene索引库中的记录
            blogIndex.delIndexToBlog(id);
        }
        if(flag>0){
            result.put("info",Boolean.valueOf(true));
        }else {
            result.put("info",Boolean.valueOf(false));
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 根据id查询一条博客记录
     */
    @RequestMapping("/findBlogById")
    public String findBlogById(@RequestParam("id")String id,HttpServletResponse response)
                               throws Exception{
        Blog blog = blogService.findBlogById(Integer.valueOf(id));
        /*把查出的Blog对象转换为json对象*/
        JSONObject jsonObject=JSONObject.fromObject(blog);
        ResponseUtil.write(response,jsonObject);
        return null;
    }















}
