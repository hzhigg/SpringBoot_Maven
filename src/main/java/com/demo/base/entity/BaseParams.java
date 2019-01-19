package com.demo.base.entity;

/**
 * 基本参数 VO基类
 * @author hzhigg
 * 2019年1月19日
 */
public abstract class BaseParams {

	private Integer currentPage=1; //当前页
	private Integer pageSize=20; //一页显示数量
	private String search; //搜索参数
	private String startTime; //搜索开始时间
	private String endTime; //搜索结束时间
	
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
