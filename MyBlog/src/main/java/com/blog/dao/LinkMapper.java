package com.blog.dao;


import com.blog.entity.BlogType;
import com.blog.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkMapper {

    /**根据id查询一条链接记录*/
    Link getLinkById(Integer id);

    /**不固定参数查询链接列表*/
    List<Link> getAllLink(Map<String,Object> map);

    /**无参数查询链接记录数*/
    Long getLinkTotal();

    /**插入一条链接*/
    Integer insertLink(Link link);

    /**修改一条链接*/
    Integer updateLink(Link link);

    /**根据id删除一条链接记录*/
    Integer deleteLinkById(Integer id);














}
