package com.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.base.service.BaseServiceImpl;
import com.demo.user.entity.User;
import com.demo.user.mapper.UserMapper;
import com.demo.user.vo.UserVo;
import com.demo.util.DozerUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("userService")
public class UserService extends BaseServiceImpl<UserMapper, User>{

	@Autowired
	private UserMapper userMapper;

	public Page<User> getPage(UserVo vo) {
		PageHelper.startPage(vo.getCurrentPage(),vo.getPageSize());
		User user=DozerUtils.map(vo, User.class);
		return userMapper.findListPage(user);
	}
	
	
}
