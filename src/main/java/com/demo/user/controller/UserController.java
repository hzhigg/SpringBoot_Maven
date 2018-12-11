package com.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.base.controller.BaseController;
import com.demo.global.RtnResult;
import com.demo.global.enums.RtnResultCode;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="用户服务",tags={"用户服务"})
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	
	@ApiOperation("根据id查询用户")
	@GetMapping("/getById")
	public RtnResult getById(Long id){
		User user=userService.selectByPrimaryKey(id);
		return RtnResult.Success(user);
	}
}
