package com.lottery.common.base;

import java.util.Date;

public class MmoException extends Exception {

	private static final long serialVersionUID = -2717692824157525511L;
	
	protected Status status; // 状态码 
	protected String msg;
	private Date requestTime;
	private Object data;
	
	
	public String getMsg() {
		return msg;
	}
	
	public MmoException(Status status, String msg,Date requestTime) {
		super(msg);
		this.status = status;
		this.msg = msg;
		this.requestTime = requestTime;
	}
	
	public MmoException(Status status, String msg, String message,Date requestTime) {
		super(message);
		this.status = status;
		this.msg = msg;
		this.requestTime = requestTime;
	}
	
	public MmoException(Status status, String msg) {
		super(msg);
		this.status = status;
		this.msg = msg;
	}
	
	public MmoException(Status status, String msg, String message) {
		super(message);
		this.status = status;
		this.msg = msg;
	}

	public MmoException(Status status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	
	public MmoException(Status status, String message, Throwable cause,Object data) {
		super(message, cause);
		this.status = status;
		this.data = data;
	}
	
	public MmoException(Status status, String message,Object data) {
		super(message);
		this.status = status;
		this.data = data;
	}

	public MmoException(String msg, String message, Throwable cause) {
		super(message, cause);
		this.msg = msg;
	}

	public MmoException(Status status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	protected MmoException(Status status, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
	}

	protected MmoException(Status status, String msg, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
		this.msg = msg;
	}

	public Status getStatus() {
		return status;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}




	public Object getData() {
		return data;
	}




	public void setData(Object data) {
		this.data = data;
	}

	
}
