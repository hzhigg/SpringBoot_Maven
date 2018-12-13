package com.demo.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Long id);
	
	/**
	 * 根据id删除(物理删除)
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);
	
	/**
	 * 新增
	 * @param record
	 * @return
	 */
	int insertSelective(T record);
	
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);
	
	/**
	 * 带参数查询列表
	 * @param record
	 * @return
	 */
	List<T> findList(T record);
	
	/**
	 * 插入集合
	 * @param record
	 * @return
	 */
	int insertBatchSelective(List<T> record);
	
	/**
	 * 集合更新
	 * @param record
	 * @return
	 */
	int updateBatchByPrimaryKeySelective(List<T> record);
	
	/**
	 * 删除集合(物理删除)
	 * @param record
	 * @return
	 */
	int deleteList(List<Long> idS);
	
	/**
	 * 批量更新启用状态(软删除)
	 * @param record
	 * @param isValid 是否启用，0:启用     1:不启用
	 * @return
	 */
	int updateIsValidList(@Param("list") List<Long> idS,@Param("isValid") Integer isValid);
	
	/**
	 * 单个更新启用状态(软删除)
	 * @param id 
	 * @param isValid 是否启用，0:启用     1:不启用
	 * @return
	 */
	int updateIsValid(@Param("id") Long id,@Param("isValid") Integer isValid);
	
	/**
	 * 版本号自增+1
	 * @param id
	 * @return
	 */
	int updateVersionAutoIncrease(Long id);
}
