package org.finchley.study.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 表格结构容器，保存需要展现的列表结构数据。
 * @author Jhon Fang
 *
 */
public class TableDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7725282776488696297L;
	
	private TableColumnDO primaryKey;
	
	/**
	 * 表头
	 */
	private List<TableColumnDO> head;
	/**
	 * 行数据
	 */
	private List<?> rowItems;

	public TableColumnDO getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(TableColumnDO primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<TableColumnDO> getHead() {
		return head;
	}

	public void setHead(List<TableColumnDO> head) {
		for(TableColumnDO tc :head) {
			if("PRI".equals(tc.getColumnKey())) setPrimaryKey(tc);
		}
		this.head = head;
	}

	public List<?> getRowItems() {
		return rowItems;
	}

	public void setRowItems(List<?> rowItems) {
		this.rowItems = rowItems;
	}

	
	
	
}
