package org.finchley.study.dto;

import java.util.Date;

import org.finchley.study.dto.MenuDTO;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MenuDO extends MenuDTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5259378109490109215L;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Date gmt_create;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Date gmt_modified;
	
	Long user_id_create;
	Long user_id_modify;
	
	String parentN;
	String typeN;
	
	
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Date getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public Long getUser_id_create() {
		return user_id_create;
	}
	public void setUser_id_create(Long user_id_create) {
		this.user_id_create = user_id_create;
	}
	public Long getUser_id_modify() {
		return user_id_modify;
	}
	public void setUser_id_modify(Long user_id_modify) {
		this.user_id_modify = user_id_modify;
	}
	public String getParentN() {
		return parentN;
	}
	public void setParentN(String parentN) {
		this.parentN = parentN;
	}
	public String getTypeN() {
		return typeN;
	}
	public void setTypeN(String typeN) {
		this.typeN = typeN;
	}
	
}
