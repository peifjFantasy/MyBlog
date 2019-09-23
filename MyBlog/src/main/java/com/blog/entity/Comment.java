package com.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论记录
 */
public class Comment implements Serializable {

	/**评论记录id*/
	private Integer id;
	/**评论用户的ip地址*/
	private String userIp;
	/**所评论的博客*/
	private Blog blog;
	/**评论内容*/
	private String content;
	/**评论时间*/
	private Date commentDate;
	/**评论状态*/
	private Integer state;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
