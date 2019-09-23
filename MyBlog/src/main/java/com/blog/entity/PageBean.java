package com.blog.entity;

import java.util.List;

/**
 * 分页bean，封装分页所需的属性
 */

public class PageBean {

	/**当前第几页，从1开始*/
	private int pageNumber;
	/**页面显示几条记录*/
	private int pageSize;
	/**总记录数*/
	private long count;
	/**总页数*/
	private long totalPage;
	/**查询出的某一实体集合*/
	private List<?> list;


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public PageBean(int pageNumber, int pageSize, List<?> list) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.list = list;
	}

	public PageBean() {}
}
