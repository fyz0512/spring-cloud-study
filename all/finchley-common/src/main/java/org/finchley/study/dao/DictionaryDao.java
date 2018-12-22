package org.finchley.study.dao;

import java.util.List;

import org.finchley.study.dto.DictionaryDTO;
import org.finchley.study.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 字典表的DAO
 * @author John Fang
 *
 */
@Repository
public interface DictionaryDao {

	public List<DictionaryDTO> getDict(Query q); 
	
}
