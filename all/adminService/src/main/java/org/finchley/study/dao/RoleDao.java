package org.finchley.study.dao;

import java.util.List;
import java.util.Map;

import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.RoleDO;
import org.finchley.study.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 角色信息
 * @author John Fang
 *
 */

@Repository
public interface RoleDao {

	/**
	 * 角色列表
	 * @param q
	 * @return
	 */
	public List<RoleDO> list(Query q);
	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
	public Integer createRole(RoleDO role);
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	public Integer updateRole(RoleDO role);
	/**
	 * 获取角色数量
	 * @param q
	 * @return
	 */
	public Integer getCount(Query q);
	/**
	 * 获取角色信息
	 * @param q
	 * @return
	 */
	public RoleDO getRole(Query q);
	/**
	 * 获取角色拥有的菜单列表
	 * @param q
	 * @return
	 */
	public List<MenuTreeDTO> getRoleMenu(Query q);
	/**
	 * 设置角色菜单信息
	 * @param roleMenu
	 * @return
	 */
	public Integer setRoleMenu(Map<String,Object> roleMenu);
	
	/**
	 * 删除角色
	 * @param roleIds
	 * @return
	 */
	public int delete(List<String> roleIds);
	
}
