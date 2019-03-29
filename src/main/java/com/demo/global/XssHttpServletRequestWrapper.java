package com.demo.global;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * 防止XSS攻击过滤器
 * @author hzhigg
 * 2019年3月29日
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	HttpServletRequest orgRequest = null;
	 private HttpServletRequest request;
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request=request;
	}
	
    /**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter ( String name ) {
		/*String value = super.getParameter(xssEncode(name));
		if (StringUtils.isNoneBlank(value)) {
			value = xssEncode(value);
		}
		return value;*/
		String value = request.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
	}

	@Override
	public String[] getParameterValues ( String name ) {
		/*String[] value = super.getParameterValues(name);
		if (!ObjectUtils.isEmpty(value)) {
			for (int i = 0; i < value.length; i++) {
				value[i] = xssEncode(value[i]);
			}
		}
		return value;
		*/
		String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String value = parameterValues[i];
            parameterValues[i] = StringEscapeUtils.escapeHtml4(value);
        }
        return parameterValues;
	}

	@Override
	public Map<String, String[]> getParameterMap () {
		return super.getParameterMap();
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
	 * getHeaderNames 也可能需要覆盖
	 * 这一段代码在一开始没有注释掉导致出现406错误，原因是406错误是HTTP协议状态码的一种，
	 * 表示无法使用请求的内容特性来响应请求的网页。一般是指客户端浏览器不接受所请求页面的 MIME 类型。
	 */
	@Override
	public String getHeader ( String name ) {
		String value = super.getHeader(xssEncode(name));
		if (StringUtils.isNotBlank(value)) {
			//value = xssEncode(value);
			value=StringEscapeUtils.escapeHtml4(value);
		}
		return value;
	}

	/**
	 * 
	 * @title: xssEncode
	 * @description: 将容易引起xss漏洞的半角字符直接替换成全角字符，在保证不删除数据的情况下保存
	 *
	 * @param value
	 * @return 过滤后的值
	 * @date 2018年9月14日 下午4:12:48
	 */
	private static String xssEncode ( String value ) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
		value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
		value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
		value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");

		return value;
	}

}
