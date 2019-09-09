package com.demo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class TaskMq {

	@Autowired
	private MsgProducer msgProducer;
	
	@Scheduled(cron="00 */1 * * * *")
	public void test(){
		for(int i=1;i<=1000;i++){
			msgProducer.sendMsg(String.valueOf(i));
		}
	}
}
