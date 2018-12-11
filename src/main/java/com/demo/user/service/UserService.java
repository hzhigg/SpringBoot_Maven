package com.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.base.service.BaseServiceImpl;
import com.demo.user.entity.User;
import com.demo.user.mapper.UserMapper;

@Service("userService")
public class UserService extends BaseServiceImpl<UserMapper, User>{

	@Autowired
	private UserMapper userMapper;
	
	
}
