package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.service.BlogService;
import com.blog.util.ChangePageUtil;
import com.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: john
 * @Date: 2019/9/15 23:13
 * @Description: 前台首页
 * @version: 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page",required = false)String page,
                              HttpServletRequest request,
                              @RequestParam(value = "typeId",required = false)String typeId,
                              @RequestParam(value = "releaseDateStr",required = false)String releaseDateStr){

        ModelAndView mv=new ModelAndView();
        mv.addObject("title","个人博客系统");
        //如果传入的page为null或是空串，则默认查询第1页
        if(!StringUtil.isNotEmpty(page)){
            page="1";
        }
        Integer pageNumber=Integer.valueOf(page);
        Integer pageSize=7;
        //Map中存放各种查询条件来获取所有符合条件的博客，用于前台首页展示
        Map<String,Object> map=new HashMap<>();
        map.put("pageStart",(pageNumber-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("typeId",typeId);
        map.put("releaseDateStr",releaseDateStr);

        StringBuilder param=new StringBuilder();
        if(StringUtil.isNotEmpty(typeId)){
            param.append("typeId="+typeId+"&");
        }
        if(StringUtil.isNotEmpty(releaseDateStr)){
            param.append("releaseDateStr="+releaseDateStr+"&");
        }
        String pageCode= ChangePageUtil.genPagination(request.getContextPath()+"/index.html",blogService.findBlogTotal(map),
                pageNumber,pageSize,param.toString());

        mv.addObject("pageCode",pageCode);
        mv.addObject("bloglist",blogService.findAllBlogByParam(map));
        mv.addObject("mainPage","foreground/blog/list.jsp");

        return mv;
    }














}
