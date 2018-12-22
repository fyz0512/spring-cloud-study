package org.finchley.study.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RoleDO {
	
	
	
	Long role_id;
	String role_name;
	String role_sign;
	String remark;
	Long user_id_create;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Date gmt_create;
	Long user_id_modify;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Date gmt_modified;
	
	
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_sign() {
		return role_sign;
	}
	public void setRole_sign(String role_sign) {
		this.role_sign = role_sign;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUser_id_create() {
		return user_id_create;
	}
	public void setUser_id_create(Long user_id_create) {
		this.user_id_create = user_id_create;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Long getUser_id_modify() {
		return user_id_modify;
	}
	public void setUser_id_modify(Long user_id_modify) {
		this.user_id_modify = user_id_modify;
	}
	public Date getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	
	
	
}
