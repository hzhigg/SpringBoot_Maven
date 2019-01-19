package com.demo.util.page;

import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.Page;

/**
 * 分页插件
 * 
 * @author hzgg
 *
 */
@Configuration
public class PgeHeplerUtils {

	/**
	 * mybatis pagehelper page类型转成 ApiPageResult类型
	 * 
	 * @param page
	 * @return
	 */
	public static <T> void toApiPageResult(ApiPageResult<T> result, Page<T> page) {
		if (page == null) {
			return;
		}
		result.setList(page.getResult());
		result.setCurrPage(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setStartRow(page.getStartRow());
		result.setEndRow(page.getEndRow());
		result.setTotalCount(page.getTotal());
		result.setTotalPage(page.getPages());
		result.setOrderBy(page.getOrderBy());
	}
}
