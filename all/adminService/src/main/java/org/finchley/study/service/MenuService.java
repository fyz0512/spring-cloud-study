package org.finchley.study.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finchley.study.annotation.Log;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dao.DictionaryDao;
import org.finchley.study.dao.MenuDao;
import org.finchley.study.dto.DictionaryDTO;
import org.finchley.study.dto.MenuDO;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.dto.TableDTO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

	static Logger LOG = LoggerFactory.getLogger(MenuService.class);
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
		
		q = new Query();
		q.addParam("parentId", 8);
		List<DictionaryDTO> types = dictionaryDao.getDict(q);
		Map<String,List<DictionaryDTO>> searchs = new HashMap<String,List<DictionaryDTO>>();
		searchs.put("type", types);
		p.setSearchs(searchs);
		
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
	
	@Log("新增菜单")
	public ResponseData createMenu(MenuDO menu) {
		
		if(menu.getName()!=null && menu.getPermit()!=null ) {
			menu.setUser_id_create(Long.parseLong(SessionContext.getUserID()));
			if(menuDao.createMenu(menu)>0) {
				return ResponseData.ok("菜单新增成功。");
			}else 
				return ResponseData.error("菜单新增失败。");
		}
		
		return ResponseData.requestError();
	}



	public ResponseData getMenu(String menuId) {
		
		
		Query q = new Query();
		q.addParam("menuId", menuId);
		MenuDO menu = menuDao.getMenu(q);
		
		if(menu!=null) {
			q = new Query();
			q.addParam("parentId", 8);
			List<DictionaryDTO> types = dictionaryDao.getDict(q);
			ResponseData rd = ResponseData.data(menu);
			rd.put("type", types);
			return rd;
		}
		
		return ResponseData.requestError();
	}


	@Log("更新菜单")
	public ResponseData updateMenu(MenuDO menu) {
		
		if(menu.getMenuId()!=null) {
			menu.setUser_id_modify(Long.parseLong(SessionContext.getUserID()));
			if(menuDao.updateMenu(menu)==1) {
				return ResponseData.ok("菜单更新成功。");
			}else {
				return ResponseData.error("菜单更新失败。");
			}
		}
		
		return ResponseData.requestError();
	}
	
	@Log("删除菜单")
	public ResponseData delete(String[] menuIds) {
		if(menuIds!=null && menuIds.length>0) {
		
			try {
				int retcount = menuDao.delete(Arrays.asList(menuIds));
				return ResponseData.ok("成功删除"+retcount+"个菜单");
			}catch(Exception e) {
				LOG.error("删除菜单异常", e);
			}
				
		}
		
		return ResponseData.requestError();
	}


	/**
	 * 生成菜单树
	 * @param superTree 是否是上级树
	 * @return
	 */
	public List<MenuTreeDTO> getMenuTree(boolean superTree) {
		
		List<Integer> l = Arrays.asList(new Integer[] {0,1,2});
		if(!superTree) l.add(3);
		Query q  = new Query();
		q.addParam("types", l);
		List<MenuTreeDTO> ret = menuDao.getMenuTree(q);
		MenuTreeDTO root = new MenuTreeDTO();
		root.setName("<i class='fa fa-tree'></i>菜单树");
		root.setPid("");
		root.setTid("0");
		root.setTitle("全部菜单");
		ret.add(0, root);
		
		return ret;
	}
	
	public ResponseData uniqName(String name,String parentId,String permit,String menuId) {
		if(name!=null ) {
			Query q = new Query();
			q.put("name", name);
			if(parentId==null) parentId="0";
			q.put("parentId", parentId);
			if(menuId!=null) q.put("NmenuId",menuId);
			if(menuDao.getCount(q)==0) {
				return ResponseData.ok("菜单名称可用");
			}else
				return ResponseData.error("菜单名称已被使用！");
		}else if(permit!=null) {
			Query q = new Query();
			q.addParam("permit", permit);
			if(menuId!=null) q.put("NmenuId",menuId);
			if(menuDao.getCount(q)==0) {
				return ResponseData.ok("菜单许可可用");
			}else
				return ResponseData.error("菜单许可已被使用！");
		}
		
		return ResponseData.requestError();
	}
}
