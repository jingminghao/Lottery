package com.lottery.common.base;

public enum Status {

	SUCCESS("SUCCESS", "操作成功"), 
	ARGS_FAIL("ARGS_FAIL", "参数异常"), 
	SERVICE_FAIL("SERVICE_FAIL","业务处理失败"),
	NO_DATA("NO_DATA","未找到数据"),
	ERROR("OTHER_ERROR", "未知错误");

	private String code;
	private String msg;

	private Status(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
