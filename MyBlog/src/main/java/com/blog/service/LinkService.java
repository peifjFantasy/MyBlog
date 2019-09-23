package com.blog.service;

import com.blog.entity.Link;

import java.util.List;
import java.util.Map;

/**
 * @Author: john
 * @Date: 2019/9/15 17:35
 * @Description: 友情链接service接口
 * @version: 1.0
 */
public interface LinkService {

    /**根据id查询一条链接记录*/
    Link findLinkById(Integer id);

    /**不固定参数查询链接列表*/
    List<Link> findAllLink(Map<String,Object> map);

    /**不固定参数查询链接记录数*/
    Long findLinkTotal();

    /**插入一条链接*/
    Integer addLink(Link link);

    /**修改一条链接*/
    Integer updLink(Link link);

    /**根据id删除一条链接记录*/
    Integer delLinkById(Integer id);

}
