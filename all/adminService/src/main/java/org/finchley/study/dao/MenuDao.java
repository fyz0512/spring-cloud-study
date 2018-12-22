package org.finchley.study.dao;

import java.util.List;

import org.finchley.study.dto.MenuDO;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 系统菜单
 * @author John Fang
 *
 */
@Repository
public interface MenuDao {

	/**
	 * 获取用户菜单
	 * @param q
	 * @return
	 */
	public List<MenuDTO> getUserMenus(Query q);
	
	/**
	 * 获取系统固定功能菜单，即任何用户只要登录后都会有的功能菜单。
	 * @return
	 */
	public List<MenuDTO> getFixedMenus();
	
	/**
	 * 管理菜单，获取菜单列表
	 */
	public List<MenuDO> list(Query q);
	/**
	 * 获取菜单项
	 * @param q
	 * @return
	 */
	public MenuDO getMenu(Query q);
	/**
	 * 创建菜单
	 * @param menu
	 * @return
	 */
	public Integer createMenu(MenuDO menu);
	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	public Integer updateMenu(MenuDO menu);
	/**
	 * 获取菜单数量
	 * @param q
	 * @return
	 */
	public Integer getCount(Query q);
	/**
	 * 删除菜单
	 * @param menuIds
	 * @return
	 */
	public Integer delete(List<String> menuIds);
	/**
	 * 获取菜单树
	 * @param q
	 * @return
	 */
	public List<MenuTreeDTO> getMenuTree(Query q);
}
