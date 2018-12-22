package org.finchley.study.dto;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 日志记录
 * @author John Fang
 *
 */
@Document(collection="logs")
public class LogDO {

	@Field("_id")
	private String id;
	@Field("userId")
	private Long userId;
	@Field("userName")
	private String username;
	@Field("operation")
	private String operation;
	@Field("time")
	private Long time;
	@Field("method")
	private String method;
	@Field("params")
	private String params;
	@Field("ip")
	private String ip;
	@Field("result")
	private String result;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Field("recTime")
	private Date gmtCreate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation == null ? null : operation.trim();
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params == null ? null : params.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "LogDO{" +
				"id=" + id +
				", userId=" + userId +
				", username='" + username + '\'' +
				", operation='" + operation + '\'' +
				", time=" + time +
				", method='" + method + '\'' +
				", params='" + params + '\'' +
				", ip='" + ip + '\'' +
				", result='" + result + '\'' +
				", gmtCreate=" + gmtCreate +
				'}';
	}
}
