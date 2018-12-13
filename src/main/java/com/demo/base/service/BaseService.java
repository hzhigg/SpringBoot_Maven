package com.demo.base.service;

import java.util.List;

import com.demo.base.utils.DateUtil;

public abstract class BaseService<T> extends DateUtil{

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public abstract T selectByPrimaryKey(Long id);
	
	/**
	 * 根据id删除(物理删除)
	 * @param id
	 * @return
	 */
	public abstract int deleteByPrimaryKey(Long id);
	
	/**
	 * 新增
	 * @param record
	 * @return
	 */
	public abstract int insertSelective(T record);
	
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	public abstract int updateByPrimaryKeySelective(T record);
	
	/**
	 * 带参数查询列表
	 * @param record
	 * @return
	 */
	public abstract List<T> findList(T record);
	
	/**
	 * 插入集合
	 * @param record
	 * @return
	 */
	public abstract int insertBatchSelective(List<T> record);
	
	/**
	 * 集合更新
	 * @param record
	 * @return
	 */
	public abstract int updateBatchByPrimaryKeySelective(List<T> record);
	
	/**
	 * 删除集合(物理删除)
	 * @param record
	 * @return
	 */
	public abstract int deleteList(List<Long> idS);
	
	/**
	 * 批量更新启用状态(软删除)
	 * @param record
	 * @param isValid 是否启用，0:启用     1:不启用
	 * @return
	 */
	public abstract int updateIsValidList(List<Long> idS,Integer isValid);
	
	/**
	 * 单个更新启用状态(软删除)
	 * @param id 
	 * @param isValid 是否启用，0:启用     1:不启用
	 * @return
	 */
	public abstract int updateIsValid(Long id,Integer isValid);
	
	/**
	 * 版本号自增+1
	 * @param id
	 * @return
	 */
	public abstract int updateVersionAutoIncrease(Long id);
	
}
