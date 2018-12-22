package org.finchley.study.service;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.finchley.study.dao.DictionaryDao;
import org.finchley.study.dto.DictionaryDTO;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.dto.TableDTO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.dao.MenuDao;
import org.finchley.study.dto.MenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

	@Autowired
	MenuDao menuDao;
	
	@Autowired
	DictionaryDao dictionaryDao;
	
	/**
	 * 根据用户ID根据权限获得的菜单
	 * @param userID
	 * @return
	 */
	public List<MenuDTO> getUserMenus(String userID) {
		
		Query q = new Query();
		q.addParam("userId", userID);
		
		return menuDao.getUserMenus(q);
	}
	
	
	
	/**
	 * 获取固定权限菜单
	 * @return
	 */
	public List<MenuDTO> getFixedMenus() {
		
		return menuDao.getFixedMenus();
	}


	/**
	 * 获取符合条件记录数
	 * @param q 查询条件
	 * @return
	 */
	public int getCount(Query q) {
		
		return menuDao.getCount(q);
	}


	/**
	 * 列表
	 * @param q 查询条件
	 * @return
	 */
	public PageDTO list(Query q) {
		
		PageDTO p = new PageDTO();
		p.setPage(q.getPage());
		p.setPageSize(q.getLimit());
		
		
		TableDTO t = new TableDTO();
		
		t.setRowItems(menuDao.list(q));
				
		p.setData(t);
		
		
		return p;
		
	}
	
	public ResponseData add() {
		
		Query q = new Query();
		q.addParam("parentId", 8);
		List<DictionaryDTO> types = dictionaryDao.getDict(q);
		
		ResponseData rd = ResponseData.ok();
		rd.put("types", types);
		
		return rd;
	}
	
	
	public ResponseData createMenu(MenuDTO menu) {
		
		
		
		return ResponseData.requestError();
	}



	public ResponseData getMenu(String menuId) {
		
		
		Query q = new Query();
		q.addParam("menuId", menuId);
		MenuDO menu = menuDao.getMenu(q);
		
		if(menu!=null) 
			return ResponseData.data(menu);
		
		return ResponseData.requestError();
	}



	public ResponseData updateMenu(MenuDO menu) {
		
		
		return null;
	}


	/**
	 * 生成菜单树
	 * @param superTree 是否是上级树
	 * @return
	 */
	public List<MenuTreeDTO> getMenuTree(boolean superTree) {
		
		List<Object> l = Arrays.asList(new Integer[] {0,1,2});
		if(!superTree) l.add(3);
		Query q  = new Query();
		q.addParam("types", l);
		return menuDao.getMenuTree(q);
	}
}
