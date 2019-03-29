package com.demo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * 封装部分properties配置文件
 * @author huangzhi
 * properties文件必须是api开头的才能注入
 * 2018年10月16日
 */
@Data
@Component
@ConfigurationProperties(prefix="config")
public class ConfigProperties {

	private String fileLocation;//文件根目录
	
}
