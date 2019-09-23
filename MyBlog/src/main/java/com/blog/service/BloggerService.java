package com.blog.service;

import com.blog.entity.Blogger;

import java.util.List;

public interface BloggerService {

    //根据用户名获取博主信息
    Blogger findByUserName(String userName);

    //修改博主信息
    Integer updBlogger(Blogger blogger);

    //查询所有博主的信息
    List<Blogger> findAllBlogger();












}
