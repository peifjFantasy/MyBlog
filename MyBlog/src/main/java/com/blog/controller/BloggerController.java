package com.blog.controller;


import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import com.blog.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 处理博主前台的相关操作
 */

@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;


    /**
     * 处理博主登录
     * @return
     *
     * AuthenticationException是shiro中所有认证异常的父类
     */
    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest request){
        String userName=blogger.getUserName();
        String password=blogger.getPassword();
        //获取当前用户subject
        Subject subject = SecurityUtils.getSubject();
        //如果subject没有认证
        if(!subject.isAuthenticated()){
            //把用户名和密码封装成一个UsernamePasswordToken对象
            UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
            //记住我
            token.setRememberMe(true);
            try{
                //传递token给shiro的realm,具体的验证交给shiro去做
                subject.login(token);
                return "redirect:/admin/main.jsp";
            }catch (AuthenticationException e){
                e.printStackTrace();
                //blogger为接受页面参数而封装后的Blogger对象，放到request域中，以便表单回显
                request.setAttribute("blogger",blogger);
                request.setAttribute("errorInfo","用户名或密码错误！");
            }

        }
        return "login";
    }

    /**
     * 功能描述：关于博主
     * @return:
     * @author: john
     * @Date:
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe(HttpSession session){
        ModelAndView mv=new ModelAndView();
        Blogger blogger = bloggerService.findByUserName((String) session.getAttribute(Const.CURRENT_USER));
        mv.addObject("blogger",blogger);
        mv.addObject("mainPage","foreground/blogger/bloggerInfo.jsp");
        mv.addObject("pageTitle","关于博主_个人博客系统");
        mv.setViewName("index");
        return mv;
    }







}
