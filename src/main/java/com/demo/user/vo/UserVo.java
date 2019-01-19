package com.demo.user.vo;

import javax.validation.constraints.NotBlank;

import com.demo.base.entity.BaseParams;
import com.demo.global.LogicGroup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo extends BaseParams{
   

	/**
     * 邮箱
     * 表字段 : user.email
     */
	 @NotBlank(groups={LogicGroup.addGroup.class},message="邮箱为空")
    private String email;

    /**
     * 昵称
     * 表字段 : user.nick_name
     */
    private String nickName;

    /**
     * 密码
     * 表字段 : user.password
     */
    private String password;

    /**
     * 用户名
     * 表字段 : user.user_name
     */
    private String userName;
    

   
}