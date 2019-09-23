package com.blog.service.impl;

import com.blog.dao.BloggerMapper;
import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Override
    public Blogger findByUserName(String userName) {

        return bloggerMapper.getByUserName(userName);
    }

    @Override
    public Integer updBlogger(Blogger blogger) {
        /*
          刷新存放在session中的用户修改前的信息，blogger的信息已经是修改后提交到控制器的信息了,
          会覆盖登录时的旧用户信息
        */
        SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER,blogger);
        return bloggerMapper.updateBlogger(blogger);
    }

    @Override
    public List<Blogger> findAllBlogger() {
        return bloggerMapper.getAllBlogger();
    }
}
