package com.demo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.base.controller.BaseController;
import com.demo.global.RtnResult;
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
	
	@ApiOperation("根据条件查询用户")
	@GetMapping("/findList")
	public RtnResult findList(User user){
		List<User> users=userService.findList(user);
		return RtnResult.Success(users);
	}
	
	@ApiOperation("批量删除")
	@DeleteMapping("/deleteList")
	public RtnResult deleteList(@RequestBody List<Long> ids){
		int count=userService.deleteList(ids);
		return RtnResult.Success(count);
	}
	
	@ApiOperation("批量更新启动状态")
	@PutMapping("/updateIsValidList")
	public RtnResult updateIsValidList(@RequestBody List<Long> idS,@RequestParam Integer isValid ){
		int count=userService.updateIsValidList(idS, isValid);
		return RtnResult.Success(count);
	}
	
	@ApiOperation("更新启动状态")
	@PutMapping("/updateIsValid")
	public RtnResult updateIsValid(@RequestParam Long id,@RequestParam Integer isValid ){
		int count=userService.updateIsValid(id, isValid);
		return RtnResult.Success(count);
	}
	
	@ApiOperation("更新版本号")
	@PutMapping("/updateVersionAutoIncrease")
	public RtnResult updateVersionAutoIncrease(@RequestParam Long id){
		int count=userService.updateVersionAutoIncrease(id);
		return RtnResult.Success(count);
	}
	
}
