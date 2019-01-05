package org.finchley.study.dto;

import java.io.Serializable;

/**
 * 字典表数据传输对象
 * @author John Fang
 *
 */
public class DictionaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1694819577706588893L;

	
	String name;
	String value;
	String type;
	Integer sort;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}

}
