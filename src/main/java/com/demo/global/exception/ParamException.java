package com.demo.global.exception;

/**
 * 参数异常
 * 
 * @author zhang
 *
 */
public class ParamException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7860164699524675140L;

	public ParamException() {
		super();
	}

	public ParamException(String msg) {
		super(msg);
		this.errorMsg = msg;
	}

	public ParamException(String msg, Throwable cause) {
		super(msg, cause);
	}

	@Override
	protected void initDefaultCode() {
		this.errorCode = 4001;
	}

	@Override
	public String toString() {
		return "参数验证错误：错误码：" + errorCode + "；错误信息：" + errorMsg;
	}

}
