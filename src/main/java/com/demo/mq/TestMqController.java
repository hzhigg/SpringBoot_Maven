package com.demo.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class TestMqController {

	@Autowired
	private MsgProducer msgProducer;

	@GetMapping(value = "/say")
	public String say(@RequestParam("msg") String msg) {
		msgProducer.sendMsg(msg);
		return "success";
	}
}
