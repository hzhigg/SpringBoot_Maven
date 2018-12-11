package com.demo.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo.base.utils.DateUtil;

import lombok.Data;
import lombok.ToString;

@ToString(exclude={"isValid","version"})//排除"isValid","version"不显示
@Data
public class BaseEntity<T> implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7436637996797291664L;

	private Long id;

	@JSONField(format=DateUtil.D_DEFAULT)
	private Date createTime; //创建时间

	private Long createUser; //创建用户

	private Integer isValid;//是否启用，0:启用     1:不启用

	@JSONField(format=DateUtil.D_DEFAULT)
	private Date updateTime;//更新时间

	private Long updateUser;//更新用户

	private Integer version;//版本号

}
