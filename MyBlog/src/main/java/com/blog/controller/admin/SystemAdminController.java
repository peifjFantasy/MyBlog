package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.util.Const;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {


    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private LinkService linkService;

    /**
     * 刷新系统缓存
     */
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request, HttpServletResponse response)
            throws Exception{
        //获取ServletContext对象
        ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
        //当新增完博客类型后，再次访问数据库更新博客类型信息
        List<BlogType> allBlogType = blogTypeService.findAllBlogType();
        application.setAttribute(Const.BLOG_TYPE_LIST,allBlogType);

        Blogger blogger=bloggerService.findAllBlogger().get(0);
        application.setAttribute(Const.BLOGGER,blogger);

        List<Blog> bloglist = blogService.findAllBlog();
        application.setAttribute(Const.BLOG_INFO,bloglist);

        List<Link> linkList = linkService.findAllLink(null);
        application.setAttribute(Const.LINK_LIST,linkList);

        JSONObject result=new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;
    }








}
