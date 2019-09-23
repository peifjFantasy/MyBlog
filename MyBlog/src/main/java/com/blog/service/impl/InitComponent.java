package com.blog.service.impl;

import com.blog.entity.Blogger;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.util.Const;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能描述：把查询出的某些常用后台信息放入到application对象中，这样在整个项目中都能获取到该数据，
 *          且只会在项目启动时加载一次，而不是每次都去访问数据库，提升效率
 * @param:
 * @return:
 * @author: john
 * @date:
 */
@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /*注入applicationContext*/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application=sce.getServletContext();
        //在application中放入博客类别
        BlogTypeService blogTypeService= (BlogTypeService) applicationContext.getBean("blogTypeService");
        application.setAttribute(Const.BLOG_TYPE_LIST,blogTypeService.findAllBlogType());

        //在application中放入博主信息
        BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
        Blogger blogger = bloggerService.findAllBlogger().get(0);
        //仅在前台作普通信息展示，为安全起见，该博主对象密码置为null
        blogger.setPassword(null);
        application.setAttribute(Const.BLOGGER,blogger);

        //按年月分类的博客信息
        BlogService blogService= (BlogService) applicationContext.getBean("blogService");
        application.setAttribute(Const.BLOG_INFO,blogService.findAllBlog());

        //友情链接
        LinkService linkService= (LinkService) applicationContext.getBean("linkService");
        application.setAttribute(Const.LINK_LIST,linkService.findAllLink(null));


    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


}
