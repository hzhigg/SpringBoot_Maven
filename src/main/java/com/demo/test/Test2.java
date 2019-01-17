package com.demo.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableAutoConfiguration
public class Test2 {

	@Autowired
	private UserService userService;
	
	@org.junit.Test
	public void test(){
		User user=userService.selectByPrimaryKey(1L);
		System.out.println(JSON.toJSONString(user));
	}
}
