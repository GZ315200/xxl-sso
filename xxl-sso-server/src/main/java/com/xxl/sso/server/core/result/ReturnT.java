package com.xxl.sso.server.core.result;

import java.io.Serializable;

/**
 * common return
 *
 * @author gygeszean
 * @param <T>
 */
public class ReturnT<T> implements Serializable {

	private static final long serialVersionUID = -4536162499506341012L;
	public static final int SUCCESS_CODE = 0;
	public static final int FAIL_CODE = 500;

	private int code;
	private String msg;
	private T data;

	public ReturnT(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	private ReturnT(T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}
	private ReturnT(String message, T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
		this.msg = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public static<T> ReturnT<T> success() {
		return new ReturnT<>(null);
	}
	public static<T> ReturnT<T> success(String message, T data) {
		return new ReturnT<>(message, data);
	}
	public static<T> ReturnT<T> fail(String message) {
		return new ReturnT<>(FAIL_CODE, message);
	}

	public static<T> ReturnT<T> fail() {
		return new ReturnT<>(null);
	}
}
