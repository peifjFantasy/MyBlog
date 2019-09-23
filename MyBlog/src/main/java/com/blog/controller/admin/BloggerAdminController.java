package com.blog.controller.admin;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;
import com.blog.util.CryptographyUtil;
import com.blog.util.DateUtil;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author: john
 * @Date: 2019/9/14 18:28
 * @Description: 处理后台博主信息
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {
    
    @Autowired
    private BloggerService bloggerService;

    /**
     * 功能描述：处理管理员修改个人信息
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/saveBlogger")
    public String saveBlogger(@RequestParam("imageFile") MultipartFile imageFile,
                       Blogger blogger, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!imageFile.isEmpty()){
            String filePath= request.getServletContext().getRealPath("/");
            String imageName= DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImageName(imageName);
        }
        Integer flag=bloggerService.updBlogger(blogger);
        StringBuilder result=new StringBuilder();
        if(flag==1){
            result.append("<script language='javascript'>alert('信息修改成功！');</script>");
        }else{
            result.append("<script language='javascript'>alert('信息修改失败！');</script>");
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 功能描述：解决UEditor提交个人简介后，该富文本无法回显数据的问题，使用json的方式正常回显修改后的
     *          富文本中的数据
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/display")
    public String display(HttpServletResponse response) throws Exception {
        Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession().getAttribute(Const.CURRENT_USER);
        //将blogger转换为一个json对象
        JSONObject result=JSONObject.fromObject(blogger);
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 功能描述：修改管理员登录密码
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(@RequestParam("userName")String userName,
                                 @RequestParam("newPassword")String newPassword,
                                 HttpServletResponse response) throws Exception {
        Blogger blogger=bloggerService.findByUserName(userName);
        //将修改后的明文密码进行加密，盐值保持不变且唯一（用户名不变）
        Object obj=CryptographyUtil.md5(newPassword,blogger.getUserName());
        blogger.setPassword(obj.toString());
        Integer flag=bloggerService.updBlogger(blogger);
        JSONObject result=new JSONObject();
        if(flag==1){
            result.put("info",Boolean.TRUE);
        }else {
            result.put("info",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 功能描述：安全退出
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }






}
