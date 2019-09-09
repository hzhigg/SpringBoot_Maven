package com.demo.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {
	/**
	 * 以静态变量保存ApplicationContext,可在任意代码中取出ApplicaitonContext.
	 */
	private static ApplicationContext context;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) {
		SpringContextHolder.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}
}
