package com.blog.service.impl;

import com.blog.dao.LinkMapper;
import com.blog.entity.Link;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: john
 * @Date: 2019/9/15 17:37
 * @Description: 友情链接service接口实现类
 * @version: 1.0
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public Link findLinkById(Integer id) {
        return linkMapper.getLinkById(id);
    }

    @Override
    public List<Link> findAllLink(Map<String, Object> map) {
        return linkMapper.getAllLink(map);
    }

    @Override
    public Long findLinkTotal() {
        return linkMapper.getLinkTotal();
    }

    @Override
    public Integer addLink(Link link) {
        return linkMapper.insertLink(link);
    }

    @Override
    public Integer updLink(Link link) {
        return linkMapper.updateLink(link);
    }

    @Override
    public Integer delLinkById(Integer id) {
        return linkMapper.deleteLinkById(id);
    }
}
