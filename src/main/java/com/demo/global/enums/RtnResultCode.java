package com.demo.global.enums;


public enum RtnResultCode {
	//=========通用状态码=================
	FAIL(0,"Fail","失败"),
	SUCCESS(1,"success","成功"),
	NOT_LOGIN(2,"not login","请登入"),
	PARAMTER_IS_NULL(3,"Parameter is null","参数为空"),
	PARAMTER_IS_ERROR(4,"Parameter is error","参数错误"),
	SYSTEM_EXCEPTION(5,"System exception","系统异常"),
	WARN_DOWN_FILE_TOO_LARGE(6,"Download file is too large","下载文件太大"),
	WARN_FILE_NOT_FOUND(7, "file does not exist","文件不存在"),
	//=========用户模块状态码=================
	//USER_NOT_EXITS(100,"","用户不存在");
	USERNAME_OR_PASS_ERROE(100,"user accoun or password error","用户名或者密码错误"),
	
	
	END(999999,"","");
	
	private Integer code; //状态码
	private String msgEN; //英文消息
	private String msgCN;//中文消息
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsgEN() {
		return msgEN;
	}
	public void setMsgEN(String msgEN) {
		this.msgEN = msgEN;
	}
	public String getMsgCN() {
		return msgCN;
	}
	public void setMsgCN(String msgCN) {
		this.msgCN = msgCN;
	}
	private RtnResultCode(Integer code, String msgEN, String msgCN) {
		this.code = code;
		this.msgEN = msgEN;
		this.msgCN = msgCN;
	} 
	
	
}
