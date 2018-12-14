package com.demo.global.exception;

/**
 * 异常抽象类
 * @author hzhigg
 * 2018年12月14日
 */
public abstract class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int errorCode;
	protected String errorMsg;

	public BaseException() {
		initDefaultCode();
	}

	public BaseException(int code) {
		this.errorCode = code;
	}

	public BaseException(String msg) {
		this.errorMsg = msg;
		initDefaultCode();
	}

	public BaseException(int code, String msg) {
		this.errorCode = code;
		this.errorMsg = msg;
	}

	public BaseException(Throwable cause) {
		initDefaultCode();
	}

	public BaseException(int code, Throwable cause) {
		this.errorCode = code;
	}

	public BaseException(String msg, Throwable cause) {
		this.errorMsg = msg;
		initDefaultCode();
	}

	public BaseException(int code, String msg, Throwable cause) {
		this.errorCode = code;
		this.errorMsg = msg;
	}

	/**
	 * 初始化默认错误码
	 */
	protected void initDefaultCode() {
		this.errorCode = -1;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String toString() {
		return "TFP Expection，error code：" + errorCode + "；error info：" + errorMsg;
	}
}
