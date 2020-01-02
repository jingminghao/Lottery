package com.lottery.common.base;

import java.util.List;

public class PageBean<T> {

	private Integer current = 1; // 默认当前页
	private Integer pageSize = 10; // 默认分页数
	private Integer totalRecord; // 总记录数
	private Integer totalPage; // 总页数
	private Boolean pre;
	private Boolean next;
	private List<T> list = null;
	private String sql;
	private Integer start; // 开始

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		try {
			this.current = Integer.parseInt(current);
			if(this.current<=0){
				this.current = 1;
			}
		} catch (Exception e) {
			this.current = 1;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
		this.getTotalPage();
	}

	public Integer getTotalPage() {
		if(totalRecord==null||pageSize==null){
			return totalPage;
		}
		if (totalRecord % pageSize > 0) {
			this.totalPage = new Integer(totalRecord / pageSize) + 1;
		} else {
			this.totalPage = new Integer(totalRecord / pageSize);
		}
		if (totalPage == 0 || totalRecord == 0) {
			totalPage = 1;
			this.list = null;
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	/*public Boolean isPre() {
		pre = current > 1 ? true : false;
		return pre;
	}*/
	public Boolean getPre() {
		pre = current > 1 ? true : false;
		return pre;
	}

	public void setPre(Boolean pre) {
		this.pre = pre;
	}

/*	public Boolean isNext() {
		next = current < totalPage ? true : false;
		return next;
	}*/
	
	public Boolean getNext() {
		next = current < totalPage ? true : false;
		return next;
	}

	public void setNext(Boolean next) {
		this.next = next;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getStart() {
		if(null != current && null != pageSize){
			this.start = (current - 1) * pageSize;
		}
		return start;
	}

	public void setStart(Integer start) {
		this.start=start;
	}

}
