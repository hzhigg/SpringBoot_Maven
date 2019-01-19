package com.demo.util.page;

import java.util.List;

import com.demo.global.RtnResult;
import com.demo.global.enums.RtnResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 分页返回结果集(需要返回分页数据时，需要调用)
 * 
 * @author hzgg
 *
 * @param <T>
 */
@JsonInclude(Include.NON_NULL)
public class ApiPageResult<T> extends RtnResult {

	/**
	 * 
	 */
	private List<T> list;
	/**
	 * 页码，从1开始
	 */
	private int currPage;

	/**
	 * 页面大小
	 */
	private int pageSize;

	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 末行
	 */
	private int endRow;

	/**
	 * 总数
	 */
	private long totalCount;
	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 排序
	 */
	private String orderBy;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public ApiPageResult() {
		super();
	}

	public ApiPageResult(RtnResultCode code) {
		super(code);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
