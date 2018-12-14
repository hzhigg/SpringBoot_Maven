package com.demo.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * IP地址操作类
 * 
 * @author zouwei
 * @since 2016年12月20日
 */
public class IpUtils {

	public static String clientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	
	/**
	 * 带http的
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
			url = url + ":" + request.getServerPort();
		}
		return url;
	}
	
}
