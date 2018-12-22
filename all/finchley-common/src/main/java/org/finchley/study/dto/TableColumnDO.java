package org.finchley.study.dto;

import java.io.Serializable;

/**
 * 表格的列数据，
 * @author Administrator
 *
 */
public class TableColumnDO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6094601589702923806L;
	/**
	 * 列数据字段
	 */
	String columnName,
	/**
	 * 列数据类型
	 */
	dataType, 
	/**
	 * 列类型数据类型+长度
	 */
	columnType,
	/**
	 * 键类型
	 */
	columnKey, 
	/**
	 * 列注释
	 */
	columnComment;
	/**
	 * 列的意义 注释中的空格前文本或整个注释。
	 */
	String fieldName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
		if(columnComment.indexOf(' ')>0) {
			setFieldName(columnComment.substring(0,columnComment.indexOf(' ')));
		}else
			setFieldName(columnComment);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	
}
