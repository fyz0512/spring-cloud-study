package org.finchley.study.dao;

import java.util.List;
import java.util.Map;

import org.finchley.study.query.Query;
import org.finchley.study.dto.RoleDO;
import org.finchley.study.dto.UserDO;
import org.springframework.stereotype.Repository;

/**
 * 用户信息
 * @author Jhon Fang
 *
 */
@Repository
public interface UserDao {

	
	/**
	 * 获取用户信息
	 * @param query
	 * @return
	 */
	public UserDO getUser(Query query) ;
	
	/**
	 * 获取用户列表
	 * @param query
	 * @return
	 */
	public List<UserDO> list(Query query);
	
	public Integer createUser(UserDO user);
	
	public Integer updateUser(UserDO user);
	
	public Integer getCount(Query query);
	
	public Integer setUserRole(Map<String, Object> m);
	
	public List<RoleDO> getUserRole(Query query);
	
	public Integer delete(List<String> userIds);
	
	public Integer updateState(Map<String,Object> datas);
	
	public Integer resetPassword(List<String> userIds);
}
