package com.blog.util;

/**
 * @Author: john
 * @Date: 2019/9/16 23:25
 * @Description: 前台展示翻页工具类
 * @version: 1.0
 */
public class ChangePageUtil {

	/**
	 * 功能描述：使用StringBuilder拼接前台的翻页区内容，包括首页、上一页、下一页、尾页的按钮，以及页码
	 * @param:
	 * @return:
	 * @author: john
	 * @date:
	 */
    public static String genPagination(String targetUrl,long totalNum,Integer currentPage,
									   Integer pageSize,String param){
    	//总记录数
    	if(totalNum==0){
    		return "未查询到数据";
		}

    	//总页数
    	long totalPage = totalNum%pageSize==0? totalNum/pageSize:(totalNum/pageSize)+1;
    	StringBuilder pageCode=new StringBuilder();

    	//首页
    	pageCode.append("<li><a href='"+targetUrl+"?page=1&"+param+"'>首页</a></li>");

    	//上一页
    	if(currentPage > 1){//如果当前页不是第一页，就允许点击“上一页”按钮
    		pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a></li>");
		}else {//如果当前页是第一页，“上一页”的按钮就不允许点击
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}

    	//显示所有的页码，供用户点击直接跳转到该页
		for(int i=1;i<=totalPage;i++){
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>"  );
			}else {
				pageCode.append("<li><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>"  );
			}
		}

		//下一页
		if(currentPage < totalPage){//当前页不是最后一页
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a></li>");
		}else {//如果当前页是最后一页，“下一页”的按钮就不允许点击
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}

		//尾页
		pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+"&"+param+"'>尾页</a></li>");

    	return pageCode.toString();
	}

}
