package com.demo.seckill;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ExecutorService {

	@Async("asyncServiceExecutor")
	public void testExecutor(){
		System.out.println("线程:"+Thread.currentThread().getName()+"正在执行任务");
	}
}
