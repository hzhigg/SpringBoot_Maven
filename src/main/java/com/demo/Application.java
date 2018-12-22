package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.demo.*.mapper")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println();
		SpringApplication.run(Application.class, args);
	}
}
