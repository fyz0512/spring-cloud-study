package org.finchley.study.dto;

import java.io.Serializable;

public class MenuTreeDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -921594640604678962L;
	
	String tid;
	String pid;
	String title;
	String name;
	
	boolean checked = false;
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
