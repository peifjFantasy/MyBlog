package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: john
 * @Date: 2019/9/17 20:29
 * @Description: 处理博客的前台展示
 * @version: 1.0
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    private BlogIndex blogIndex=new BlogIndex();

    /**
     * 功能描述：根据博客id查询博客详细信息，并在该请求域中放入详细信息页需要的所有数据
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView showDetials(@PathVariable("id")Integer id,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        Blog blog = blogService.findBlogById(id);
        mv.addObject("blog",blog);
        //点击数+1
        blog.setClickHit(blog.getClickHit()+1);
        blogService.updBlog(blog);

        //获取上一篇博客
        Blog lastBlog = blogService.findLastBlog(id);
        mv.addObject("lastBlog",lastBlog);
        //获取下一篇博客
        Blog nextBlog = blogService.findNextBlog(id);
        mv.addObject("nextBlog",nextBlog);

        //获取该条博客下的所有审核已经通过的评论
        Map<String,Object> map=new HashMap<>();
        map.put("blogId",blog.getId());
        map.put("state",1);
        List<Comment> commentList = commentService.findAllComment(map);
        mv.addObject("commentList",commentList);
        mv.addObject("mainPage","foreground/blog/detail.jsp");
        mv.addObject("pageTitle",blog.getTitle()+"_个人博客系统");

        //处理该条博客所有的关键字
        String keyWord=blog.getKeyWord();
        if(StringUtil.isNotEmpty(keyWord)){
            String[] keyWords = keyWord.split(" ");
            mv.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(keyWords)));
        }else {
            mv.addObject("keyWords",null);
        }

        mv.setViewName("index");
        return mv;
    }

    /**
     * 功能描述：使用lucene完成首页博客的关键字查询
     * @return:
     * @author: john
     * @Date:
     */
    @RequestMapping("/searchBlog")
    public ModelAndView searchBlog(@RequestParam(value = "q",required = false)String q,
                                   @RequestParam(value = "page",required = false)String page,
                                   HttpServletRequest request) throws Exception {
        System.out.println("*************************111");
        if(!StringUtil.isNotEmpty(page)){
            page="1";
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("mainPage","foreground/blog/luceneResult.jsp");

        //使用lucene查询符合条件的博客列表
        List<Blog> blogList = blogIndex.searchBlog(q);
        Integer toIndex=0;
        //如果查询结果的实际总记录数不小于总共的理论显示条数，就以理论显示条数为准去截取
        if(blogList.size() >= Integer.valueOf(page)*7){
            toIndex = Integer.valueOf(page)*7;
        }else {//否则，就以实际的结果总记录数为准去截取
            toIndex=blogList.size();
        }
        mv.addObject("blogList",blogList.subList((Integer.valueOf(page)-1)*7,toIndex));
        mv.addObject("result",changePageCode(Integer.valueOf(page),blogList.size(),q,7,request.getServletContext().getContextPath()));
        mv.addObject("q",q);
        mv.addObject("resultTotal",blogList.size());
        mv.addObject("pageTitle","搜索关键字‘"+q+"’结果页面_个人博客系统");
        mv.setViewName("index");
        return mv;
    }

    /**
     * 功能描述：对lucene查询出的所有结果进行分页处理
     * @return:
     * @author: john
     * @Date:
     */
    public String changePageCode(Integer currentPage,Integer totalNum,String q,Integer pageSize,
                                 String proContext){
        StringBuilder result=new StringBuilder();
        //总页数
        Integer totalPage=totalNum%pageSize==0? totalNum/pageSize:totalNum/pageSize+1;
        if(totalPage==0){
            return "";
        }
        result.append("<nav>");
        result.append("<ul class='pager'>");

        //上一页
        if(currentPage > 1){//当前页不是第一页
            result.append("<li><a href='"+proContext+"/blog/searchBlog.html?currentPage="+(currentPage-1)+"&q="+q+"'>上一页</a></li>");
        }else {//当前页是第一页
            result.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }

        //下一页
        if(currentPage < totalPage){//当前页不是最后一页
            result.append("<li><a href='"+proContext+"/blog/searchBlog.html?currentPage="+(currentPage+1)+"&q="+q+"'>上一页</a></li>");
        }else {//如果当前页是最后一页，“下一页”的按钮就不允许点击
            result.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }

        result.append("</ul>");
        result.append("</nav>");
        return result.toString();
    }











}
