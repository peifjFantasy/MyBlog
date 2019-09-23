package com.blog.controller.admin;


import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台博客类型管理
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    /**
     * 展示所有博客类型
     */
    @RequestMapping("/showAllBlogType")
    public String showAllBlogType(@RequestParam(value = "page",required = false) String page,
                                  @RequestParam(value = "rows",required = false) String rows,
                                  HttpServletResponse response){
        Integer pageNumber=Integer.valueOf(page);
        Integer pageSize=Integer.valueOf(rows);
        PageBean pageBean=blogTypeService.findAllBlogTypeByPage(pageNumber,pageSize);
        //把pageBean中封装的实体类集合转换为JSONArray
        JSONObject result=new JSONObject();
        JSONArray blogTypelist=JSONArray.fromObject(pageBean.getList());
        //把具体java对象放入JSONObject中
        result.put("rows",blogTypelist);
        result.put("total",pageBean.getCount());
        try {
            //将JSON对象写入响应流
            ResponseUtil.write(response,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增、修改博客类型
     */
    @RequestMapping("/saveBlogType")
    public String saveBlogType(BlogType blogType,HttpServletResponse response){
        Integer flag=0;
        if(blogType.getId()==null){//新增
            flag=blogTypeService.addBlogType(blogType);
        }else {//修改
            flag=blogTypeService.updBlogType(blogType);
        }
        JSONObject result=new JSONObject();
        if(flag>0){//表明操作成功
            //把操作结果标识符写入到response中
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
     * 删除博客类型
     */
    @RequestMapping("/deleteBlogType")
    public String deleteBlogType(@RequestParam("ids")String ids,HttpServletResponse response){
        JSONObject result=new JSONObject();
        //把页面传入的字符串ids，按照","拆分成一个字符串数组
        String[] idsStr=ids.split(",");
        //遍历这个字符串数组，把其中的每个String类型对象转换为Integer对象
        //根据该id删除对应的记录
        for (String s : idsStr) {
            int count=blogService.findBlogByTypeId(Integer.valueOf(s));
            if(count>0){//如果该博客类别下有博客记录
                result.put("exist", "该博客类别下已有博客，不能删除！");
            }else {//没有博客记录的话就删除该博客类别
                blogTypeService.delBlogTypeById(Integer.valueOf(s));
            }
        }
        //把操作结果标识符写入到response中
        result.put("success",Boolean.valueOf(true));
        try {
            ResponseUtil.write(response,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



















}
