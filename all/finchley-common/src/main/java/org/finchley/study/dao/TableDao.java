package org.finchley.study.dao;

import java.util.List;

import org.finchley.study.dto.DictionaryDTO;
import org.finchley.study.dto.TableColumnDO;
import org.finchley.study.query.Query;

/**
 * 表结构的DAO,通过SQL获取一个表的表结构。
 * @author John Fang 
 *
 */
public interface TableDao {

	/**
	 * 获取表头
	 * @param q
	 * @return
	 */
	public List<TableColumnDO> getHead(Query q);
	
	/**
	 * 取字典表数据
	 * @param q
	 * @return
	 */
	public List<DictionaryDTO> getDic(Query q);
}
