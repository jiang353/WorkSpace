package com.qingniao.common;

import java.io.Serializable;

/*
 * 青鸟商城返回结构体
 */
public class QingNiaoResult implements Serializable{

	private Integer status;

	private String msg;

	private Object data;

	public static QingNiaoResult ok(Object data) {
		return new QingNiaoResult(data);
	}

	public static QingNiaoResult ok() {
		return new QingNiaoResult(null);
	}

	public static QingNiaoResult build(Integer status, String msg, Object data) {
		return new QingNiaoResult(status, msg, data);
	}

	public static QingNiaoResult build(Integer status, String msg) {
		return new QingNiaoResult(status, msg, null);
	}
	
	

	public QingNiaoResult() {
		super();
	}

	public QingNiaoResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public QingNiaoResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
