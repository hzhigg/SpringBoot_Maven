package com.demo.util;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * 编码生成工具
 * @author hzhigg
 * 2018年12月14日
 */
@Component
public class NumberBuilder {
	// 记录下单渠道

	public static final int PC = 1;
	/** � 大数�  */
	public static final long NUMBER_MAX_COUNT = 9999L;

	/** 订单号生成计数器 ,后续有可能放到redis�  */
	private long counter = 0L;

	/**
	 * 
	 * @param id
	 * @param platform
	 * @return
	 * @throws IOException
	 */
	public synchronized String toBuild(int channel) {
		StringBuffer number = new StringBuffer();
		// 平台来源� 1� 
		number.append(channel);
		// 时间�  � 8� , 100秒不重复
		Long currentTime = System.currentTimeMillis() / (1000 * 100);
		number.append(currentTime);
		// 随机数，3� 
		number.append(RandomStringUtils.randomNumeric(3));

		// 计数器到� 大� �归� 
		if (counter > NUMBER_MAX_COUNT) {
			counter = 0L;
		}
		// 订单计数器，4� 
		number.append(getFixedLengthCounter(counter));
		counter++;

		return number.toString();
	}

	/**
	 * 固定长度的计数器� 4� 
	 * 
	 * @param count
	 * @return
	 */
	public static String getFixedLengthCounter(Long count) {
		String counter = String.valueOf(count);
		String result = "";
		if (counter.length() < 4) {
			result = String.format("%04d", Integer.parseInt(counter));
		} else {
			result = counter.substring(counter.length() - 4, counter.length());
		}
		return result;
	}
	
	public static void main(String[] args) {
		NumberBuilder n=new NumberBuilder();
		System.out.println(n.toBuild(NumberBuilder.PC));
	}
}
