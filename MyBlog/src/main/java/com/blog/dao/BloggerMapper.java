package com.blog.dao;

import com.blog.entity.Blogger;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BloggerMapper {

    //根据用户名获取博主信息
    Blogger getByUserName(String userName);

    //修改博主信息
    Integer updateBlogger(Blogger blogger);

    //查询所有博主的信息
    List<Blogger> getAllBlogger();









}
