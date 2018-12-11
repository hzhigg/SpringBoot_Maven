package com.demo.global;

import com.demo.global.enums.RtnResultCode;

import lombok.Getter;
import lombok.Setter;

/**
 * API统一返回对象
 * @author hzhigg
 * 2018年12月11日
 */
@Getter
@Setter
public class RtnResult {

	private Integer code; //状态
	private String msg; //消息
	private Object data; //返回数据
	
	
	
	private static RtnResult Default(){
		return new RtnResult();
	}
	
	public static RtnResult Success(){
		return Default().setResultCode(RtnResultCode.SUCCESS);
	}
	
	public static RtnResult Success(Object data){
		return Default().setResultCode(RtnResultCode.SUCCESS,data);
	}
	
	
	public static RtnResult Fail(){
		return Default().setResultCode(RtnResultCode.FAIL);
	}
	
	public static RtnResult Fail(RtnResultCode code){
		return Default().setResultCode(code);
	}
	
	
	public RtnResult setResultCode(RtnResultCode code,Object data){
		this.code=code.getCode();
		this.msg=code.getMsgCN();
		this.data=data;
		return this;
	}
	
	public RtnResult setResultCode(RtnResultCode code){
		this.code=code.getCode();
		this.msg=code.getMsgCN();
		return this;
	}

	public RtnResult() {
		super();
	}
	
	
}
