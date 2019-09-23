package com.blog.controller.admin;

import com.blog.entity.Link;
import com.blog.service.LinkService;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: john
 * @Date: 2019/9/15 17:44
 * @Description: 处理友情链接
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Autowired
    private LinkService linkService;

    /**
     * 功能描述：展示所有友情链接记录
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/showAllLink")
    public String showAllLink(@RequestParam(value = "page",required = false)String page,
                              @RequestParam(value = "rows",required = false)String rows,
                              HttpServletResponse response) throws Exception {
        Integer pageNumber=Integer.valueOf(page);
        Integer pageSize=Integer.valueOf(rows);
        Map<String,Object> map=new HashMap<>();
        map.put("pageStart",(pageNumber-1)*pageSize);
        map.put("pageSize",pageSize);
        //将数据以json格式写入response
        JSONObject result=new JSONObject();
        result.put("rows", JSONArray.fromObject(linkService.findAllLink(map)));
        result.put("total",linkService.findLinkTotal());
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 功能描述：新增或修改一条友情链接
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/saveLink")
    public String saveLink(Link link,HttpServletResponse response) throws Exception {
        Integer flag=0;
        if(link.getId()==null){
            flag=linkService.addLink(link);
        }else {
            flag=linkService.updLink(link);
        }
        JSONObject result=new JSONObject();
        if(flag>0){
            result.put("success",Boolean.TRUE);
        }else {
            result.put("error",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 功能描述：根据id删除友情链接
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/deleteLink")
    public String deleteLink(@RequestParam("ids")String ids,HttpServletResponse response)
            throws Exception{
        Integer flag=0;
        JSONObject result=new JSONObject();
        //把页面传入的字符串ids，按照","拆分成一个字符串数组
        String[] idsStr=ids.split(",");
        //遍历这个字符串数组，把其中的每个String类型对象转换为Integer对象
        //根据该id删除对应的记录
        for (String s : idsStr) {
                flag+=linkService.delLinkById(Integer.valueOf(s));
            }
        //把操作结果标识符写入到response中
        if(flag>0){
            result.put("info",Boolean.TRUE);
        }else {
            result.put("info",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }



















}
