package com.demo.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.user.vo.UserExcel;
import com.demo.util.ExportExcelUtil;
import com.demo.util.ExportExcelWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.base.controller.BaseController;
import com.demo.global.LogicGroup;
import com.demo.global.RtnResult;
import com.demo.global.enums.RtnResultCode;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;
import com.demo.user.vo.UserVo;
import com.demo.util.page.ApiPageResult;
import com.demo.util.page.PgeHeplerUtils;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="用户服务",tags={"用户服务"})
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

	@Value(value="${isPro}")
	private String isPro;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@ApiOperation("未登入")
	@RequestMapping("/not-login")//未登入接口必须设置可以接受所有请求方式
	public RtnResult notLogin(){
		logger.info("============={}==============",isPro);
		return RtnResult.Fail(RtnResultCode.NOT_LOGIN);
	}
	
	@ApiOperation("模拟登入接口")
	@GetMapping("/login")
	public RtnResult login(HttpServletRequest request,HttpServletResponse response){
		User user=userService.selectByPrimaryKey(1L);
		if(ObjectUtils.isEmpty(user)){
			return RtnResult.Fail(RtnResultCode.USERNAME_OR_PASS_ERROE);
		}
		String token="token_"+System.currentTimeMillis();
		redisTemplate.opsForValue().set(token, user, 30, TimeUnit.SECONDS);
		request.getSession().setAttribute("user", user);
		Cookie cookie=new Cookie("token", token);
		//cookie.setMaxAge(30 * 60);
		cookie.setMaxAge(30);
		response.addCookie(cookie);
		return RtnResult.Success(user);
	}
	
	
	
	@ApiOperation("插入用户")
	@PostMapping("/insertSelective")
	public RtnResult insertSelective(@RequestBody @Validated({LogicGroup.addGroup.class}) User user){
		int count=userService.insertSelective(user);
		return RtnResult.Success(count);
	}
	
	
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
	
	@ApiOperation("分页查询")
	@GetMapping("/getPage")
	public RtnResult getPage(UserVo vo){
		 Page<User> data = userService.getPage(vo);
	     ApiPageResult<User> result = new ApiPageResult<>();
	     PgeHeplerUtils.toApiPageResult(result, data);
	     return RtnResult.Success(result);
	}

	@ApiOperation("导出excel例子")
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response){
		ExportExcelWrapper<UserExcel> util=new ExportExcelWrapper<UserExcel>();
		List<UserExcel> users=new ArrayList<>();
		for(int i=0;i<10;i++){
			UserExcel u=new UserExcel();
			u.setName("姓名"+i);
			u.setAge(i);
			users.add(u);
		}

		String []columnNames={"姓名","年龄"};//因为用的是反射的方式对应列头,所以这里定义的列名需要与实体名字顺序一致
		util.exportExcel("文件名","文件名",columnNames,users,response,ExportExcelUtil.EXCEl_FILE_2007);


	}
	
}
