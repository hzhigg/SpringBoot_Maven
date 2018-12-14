package com.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
 
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);    //日志记录
    public final static MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
	public static final OkHttpClient client;

	static {
		OkHttpClient.Builder builder = new OkHttpClient.Builder() //
				.connectTimeout(15, TimeUnit.SECONDS) //
				.writeTimeout(20, TimeUnit.SECONDS) //
				.readTimeout(20, TimeUnit.SECONDS); //
		client = builder.build();
	}
    
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }
 
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse) {
   		RequestBody requestBody = RequestBody.create(mediaType, jsonParam.toJSONString());
   		Request request = new Request.Builder() //
   				.url(url) //
   				.post(requestBody) //
   				.build(); //
   		Response response = null;
   		JSONObject jsonResult = null;
   		Map<String, String> map = new HashMap<>();
   		try {
   			response = client.newCall(request).execute();
   			if (!response.isSuccessful())
   				throw new IOException("Unexpected code " + response);
   			jsonResult = JSONObject.parseObject(response.body().string());
   			logger.info("send post method request success, url:{}", url);
   		} catch (Exception e) {
   			logger.error("send post method request error, url:{}", url, e);
   		} finally {
   			Util.closeQuietly(response);
   		}
   		return noNeedResponse?null:jsonResult;
   	}
    
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */

    public static JSONObject httpGet(String url) {
   		Request request = new Request.Builder() //
   				.url(url) //
   				.build(); //
   		Response response = null;
   		JSONObject jsonResult = null;
   		try {
   			response = client.newCall(request).execute();
   			if (!response.isSuccessful())
   				throw new IOException("Unexpected code " + response);
   			jsonResult = JSONObject.parseObject(response.body().string());
   			logger.info("send get method request success, url:{}", url);
   		} catch (Exception e) {
   			logger.error("send get method request error, url:{}", url, e);
   		} finally {
   			Util.closeQuietly(response);
   		}
    	return jsonResult;
    }
    
    /**
   	 * post请求(默认为utf-8编码)
   	 * 
   	 * @param url
   	 * @param msg
   	 * @param contentType
   	 * @return
   	 * @since 0512
   	 */
   	public static Map<String, String> apiPost(String url, String msg, String contentType) {
   		MediaType mediaType = MediaType.parse(contentType);
   		RequestBody requestBody = RequestBody.create(mediaType, msg);
   		Request request = new Request.Builder() //
   				.url(url) //
   				.post(requestBody) //
   				.build(); //
   		Response response = null;
   		String result = null;
   		Map<String, String> map = new HashMap<>();
   		try {
   			response = client.newCall(request).execute();
   			map.put("response", response.code()+"");
   			if (!response.isSuccessful())
   				throw new IOException("Unexpected code " + response);
   			result = response.body().string();
   			map.put("body", result);
   			logger.info("send post method request success, url:{}", url);
   		} catch (Exception e) {
   			logger.error("send post method request error, url:{}", url, e);
   			map.put("body", e.getMessage());
   		} finally {
   			Util.closeQuietly(response);
   		}
   		return map;
   	}
   	
	/**
	 * 
	 * @title: get
	 * @description: GET请求, 返回字节数组(文件下载专用)
	 *
	 * @param url
	 * @return byte[]
	 * @date 2018年11月9日 上午11:09:51
	 */
	public static byte[] getBytes ( String url ) {
		Request request = new Request.Builder() //
				.url(url) //
				.build(); //
		byte[] bfile = null;
		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				logger.error("send get method request error, url:{},e:{}", url, response);
			}
			bfile = response.body().bytes();
			logger.info("send get method request success, url:{}", url);
		} catch (Exception e) {
			logger.error("send get method request error, url:{}", url, e);
		} finally {
			Util.closeQuietly(response);
		}
		return bfile;
	}
   	
   	
   	
   	/**
	 * 获取响应结果
	 * @return
	 */
	public static String postResponse(Map<String, String> map){
		String body=map.get("body");
		if(body.indexOf("status")>=0){
			JSONObject b = JSON.parseObject(body);
			String status=b.getString("status");
			return status;
		}
		return "";
	}
	
	/**
	 * 获取响应结果
	 * @return
	 */
	public static String getResponse(Map<String, Object> map){
		if(map.containsKey("status")){
			JSONObject b = JSON.parseObject(map.toString());
			String status=b.getString("status");
			return status;
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> map = HttpClientUtil.httpGet("http://testdtcapi.g2l-service.com/common/v1/get/entityNo?prefix=CH");
		System.out.println(JSON.toJSONString(map));
	}
}