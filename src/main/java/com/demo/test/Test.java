package com.demo.test;

import com.demo.user.entity.User;

public class Test {

	public static void main(String[] args) {
		User u=new User();
		u.setId(1L);
		u.setUserName("123");
		User u2=new User();
		u2.setId(1L);
		u2.setUserName("123");
		System.out.println(u.equals(u2));
		System.out.println(u.hashCode());
		System.out.println(u2.hashCode());
		System.out.println(u.toString());
	}

}
