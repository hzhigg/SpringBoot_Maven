package com.demo.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.demo.base.entity.BaseEntity;
import com.demo.base.mapper.BaseMapper;

@Transactional(readOnly=true)
public abstract class BaseServiceImpl<D extends BaseMapper<T>,T extends BaseEntity<T>> extends BaseService<T>{

	@Autowired
	D dao;
	
	@Override
	public T selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	@Transactional(readOnly=false)
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly=false)
	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	@Transactional(readOnly=false)
	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<T> findList(T record) {
		// TODO Auto-generated method stub
		return dao.findList(record);
	}
	
	@Transactional(readOnly=false)
	@Override
	public int insertBatchSelective(List<T> record) {
		// TODO Auto-generated method stub
		return dao.insertBatchSelective(record);
	}
	
	@Override
	public int updateBatchByPrimaryKeySelective(List<T> record) {
		// TODO Auto-generated method stub
		return dao.updateBatchByPrimaryKeySelective(record);
	}

	@Transactional(readOnly=false)
	@Override
	public int deleteList(List<Long> idS) {
		// TODO Auto-generated method stub
		return dao.deleteList(idS);
	}

	@Transactional(readOnly=false)
	@Override
	public int updateIsValidList(List<Long> idS, Integer isValid) {
		// TODO Auto-generated method stub
		return dao.updateIsValidList(idS, isValid);
	}

	@Transactional(readOnly=false)
	@Override
	public int updateIsValid(Long id, Integer isValid) {
		// TODO Auto-generated method stub
		return dao.updateIsValid(id, isValid);
	}

	@Transactional(readOnly=false)
	@Override
	public int updateVersionAutoIncrease(Long id) {
		// TODO Auto-generated method stub
		return dao.updateVersionAutoIncrease(id);
	}

	

	
}
