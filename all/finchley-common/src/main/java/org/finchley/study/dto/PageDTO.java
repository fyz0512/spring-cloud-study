package org.finchley.study.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页数据容器
 * @author John Fang
 *
 */

public class PageDTO implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5411190705941521040L;
	
	
	private int page;
	private int pageSize;
	private int total;
	private TableDTO data;

	private Map<String,List<DictionaryDTO>> searhs;
	
	public PageDTO() {

	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public TableDTO getData() {
		return data;
	}

	public void setData(TableDTO data) {
		this.data = data;
	}

	public Map<String,List<DictionaryDTO>> getSearchs() {
		return searhs;
	}

	public void setSearchs(Map<String,List<DictionaryDTO>> searhs) {
		this.searhs = searhs;
	}

	

}
