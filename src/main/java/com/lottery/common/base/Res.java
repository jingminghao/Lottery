package com.lottery.common.base;

import com.lottery.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;


public class Res<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Status status = Status.SUCCESS; // 状态码
	private Integer code = 1;
	private String msg = "操作成功！";
	private T data;

	private String requestId; // 请求Id
	private String receivetime; // 请求时间
	private String backtime; // 响应时间

	private String syscode; // 调用系统
	private String itfcode; // 接口编码

	public Res() {
		this.backtime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public Res(T data) {
		super();
		this.data = data;
		this.backtime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public Res(Status status, String msg) {
		super();
		this.status = status;
		if ((Status.SUCCESS).equals(this.status)) {
			this.code = 1;
		}
		if ((Status.ARGS_FAIL).equals(this.status)) {
			this.code = 2;
		}
		if ((Status.SERVICE_FAIL).equals(this.status)) {
			this.code = 5;
		}
		this.msg = msg;
		this.backtime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public Res(Status status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
		this.backtime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public Res(Status status, Integer code, String msg, T data) {
		super();
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.backtime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public Status getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getBacktime() {
		return backtime;
	}

	public void setBacktime(String backtime) {
		this.backtime = backtime;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getItfcode() {
		return itfcode;
	}

	public void setItfcode(String itfcode) {
		this.itfcode = itfcode;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(T data) {
		this.data = data;
	}

}

