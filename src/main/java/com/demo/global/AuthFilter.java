package com.demo.global;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.demo.user.entity.User;


@Component
public class AuthFilter  implements Filter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("=======初始化过滤器=========");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletResponse resp = (HttpServletResponse) response;
		long start = System.currentTimeMillis();
		if(this.beforeDone(request, resp)) {
			filterChain.doFilter(request, response);
			this.afterDone(request, response);
		}
		System.out.println("filter 耗时：" + (System.currentTimeMillis() - start));
	}

	@Override
	public void destroy() {
		System.out.println("=======销毁过滤器=========");
	}

	// 配置系统级别无需校验的URI列表
	static List<String> excludeUri = null;
	static String loginRUI = "/user/login";
	static {
		if (excludeUri == null) {
			excludeUri = new ArrayList<>();
			excludeUri.add(loginRUI);
			excludeUri.add("/user/not-login");
			excludeUri.add("/swagger-resources/configuration/security");
			excludeUri.add("/swagger-resources/configuration/ui");
			excludeUri.add("/swagger-resources");
			excludeUri.add("/swagger-ui.html");
			excludeUri.add("/v2/api-docs");
			excludeUri.add("/webjars/springfox-swagger-ui/");
			excludeUri.add("/favicon.ico");
			excludeUri.add("/exception");
			excludeUri.add("/error");
			excludeUri.add(".html");
			excludeUri.add(".js");
			excludeUri.add(".css");
			excludeUri.add(".png");
			excludeUri.add(".jpg");
			excludeUri.add(".jpeg");
			excludeUri.add(".pdf");
			excludeUri.add(".PDF");
			
		}
	}
	Boolean checkIgnore(String url) {
		boolean flag = false;
		for (String exclude : excludeUri) {
			if (url.contains(exclude)) {
				flag = true;
				break;
			}
		}
		System.out.println(flag);
		return flag;
	}
	/*
	 * 获取会话数据 1.Session方式 2.Token方式 3.其他
	 */
	Object getSession(HttpServletRequest request) {

		//String token = request.getHeader("token");
		User user=(User) request.getSession().getAttribute("user");
		if(!ObjectUtils.isEmpty(user)){
			return user;
		}
		
		return null;
	}
	Boolean checkPermission(String url, Object obj) {

		return true;
	}

	/*
	 * Access Control 1.Encode 2.Login 3.Route access control
	 */
	Boolean beforeDone(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
 		Boolean flag = true;

		if("OPTIONS".equals(req.getMethod())) {
			return flag;
		}

		String method = req.getMethod();
		String url = req.getRequestURI(); // 当前请求的url

		logger.debug("--------------> request method : " + method);
		logger.debug("--------------> request url : " + url);

		if (!checkIgnore(url)) {

			Object user = getSession(req);
			if (user == null) {

				//这里填写你允许进行跨域的主机ip
				resp.setHeader("Access-Control-Allow-Origin", "*");
				//允许的访问方法
				resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
				//Access-Control-Max-Age 用于 CORS 相关配置的缓存
				//resp.setHeader("Access-Control-Max-Age", "3600");
				resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

				String notLogin = "/user/not-login";
				//resp.setStatus(700);
				request.getRequestDispatcher(notLogin).forward(request, response);

				flag = false;
			} 
		}
		return flag;
	}

	/*
	 * Output Control 1.Wrap Result
	 */
	void afterDone(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		//
	}

	public void outputMsgByOutputStream(HttpServletResponse response,String msg) throws IOException {
		OutputStream outputStream = response.getOutputStream();// 获取OutputStream输出流
		response.setHeader("content-type", "text/html;charset=UTF-8");// 通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
		byte[] dataByteArr = msg.getBytes("UTF-8");// 将字符转换成字节数组，指定以UTF-8编码进行转换
		outputStream.write(dataByteArr);// 使用OutputStream流向客户端输出字节数组
	}


	
	
}

