package org.finchley.study.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.MenuDO;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.service.MenuService;
import org.finchley.study.utils.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	
	@Autowired
	MenuService menuService;
	
	
	
	@RequestMapping("userMenus")
	public List<MenuDTO> userMenu(){
		
		
		return menuService.getUserMenus(SessionContext.getUserID());
	       
	}
	
	/**
	 * 分页列表
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public PageDTO query(HttpServletRequest request) {
		
		int pageNum = 1;
		if(request.getParameter("page")!=null) {
			pageNum = Integer.parseInt(request.getParameter("page"));
		}
		
		PageDTO page = new PageDTO();
		
		Query q = new Query();
		q.addParam("page", pageNum);
		q.addParam("offset", (pageNum-1) * CommonConstants.PAGE_SIZE);
		q.addParam("limit", CommonConstants.PAGE_SIZE);
		
		if(request.getParameter("name")!=null && request.getParameter("name").length()>0) {
			q.addParam("nameL", request.getParameter("name"));
		}
		
		if(request.getParameter("parentId")!=null && request.getParameter("parentId").length()>0) {
			q.addParam("parentId", request.getParameter("parentId"));
		}
		
		if(request.getParameter("type")!=null && request.getParameter("type").length()>0) {
			q.addParam("type", request.getParameter("type"));
		}
		
		page = menuService.list(q);
		
		page.setTotal(menuService.getCount(q));
		page.setPage(pageNum);
		page.setPageSize(q.getLimit());
		
		return page;
	}
	
	@RequestMapping("add")
	public ResponseData addMenu(HttpServletRequest request) {
		
		if(request.getParameter("save")==null) {
			
			return menuService.add();
		}else if(HttpContextUtils.checkField(Arrays.asList(new String[] {"name","permit"}))) {
			
			MenuDO menu = new MenuDO();
			menu.setComponent(request.getParameter("component"));
			menu.setIcon(request.getParameter("icon"));
			menu.setName(request.getParameter("name"));
			if(request.getParameter("parentId")!=null)
				menu.setParentId(Long.parseLong(request.getParameter("parentId")));
			else
				menu.setParentId(0L);
			if(request.getParameter("order_num")!=null)
				menu.setOrder_num(Integer.parseInt(request.getParameter("order_num")));
			else
				menu.setOrder_num(0);
			menu.setUrl(request.getParameter("url"));
			menu.setPermit(request.getParameter("permit"));
			menu.setType(Integer.parseInt(request.getParameter("type")));
			
			return menuService.createMenu(menu);
		}
		return ResponseData.requestError();
	}
	
	@RequestMapping("get")
	public ResponseData getMenu(@RequestParam("menu_id") String menuId){
		
		return menuService.getMenu(menuId);
	}
	@RequestMapping("edit")
	public ResponseData updateMenu(HttpServletRequest request) {
		String menuId= request.getParameter("menuId");
		if(menuId!=null) {
			if(request.getParameter("save")==null) {
				//
				return menuService.getMenu(menuId);
			}else {
				MenuDO menu = new MenuDO();
				menu.setMenuId(Long.parseLong(menuId));
				if(request.getParameter("parentId")!=null)
					menu.setParentId(Long.parseLong(request.getParameter("parentId")));
				else
					menu.setParentId(0L);
				if(request.getParameter("order_num")!=null)
					menu.setOrder_num(Integer.parseInt(request.getParameter("order_num")));
				else
					menu.setOrder_num(0);
				menu.setUrl(request.getParameter("url"));
				menu.setPermit(request.getParameter("permit"));
				menu.setType(Integer.parseInt(request.getParameter("type")));
				menu.setComponent(request.getParameter("component"));
				menu.setIcon(request.getParameter("icon"));
				menu.setName(request.getParameter("name"));
				
				return menuService.updateMenu(menu);
			}
		}
		return ResponseData.requestError();
	}
	
	@RequestMapping("del")
	public ResponseData delete(@RequestParam("menuId") String[] menuIds) {
		
		if(menuIds!=null && menuIds.length>0) {
			
			return menuService.delete(menuIds);
		}
		return ResponseData.requestError();
	}
	
	@RequestMapping("menuTree")
	public ResponseData menuTree(@RequestParam(name="parent",required=false) String parent) {
		
		List<MenuTreeDTO> l = menuService.getMenuTree(parent!=null);
		if(l.size()>0) 
			return ResponseData.data(l);
		
		return ResponseData.requestError();
	}
	
	@RequestMapping("uniqName")
	public ResponseData uniquenceCheck(@RequestParam(name="name",required=false) String name,
			@RequestParam(name="parentId",required=false) String parentId,
			@RequestParam(name="permit",required=false) String permit,
			@RequestParam(name="menuId",required=false) String menuId) {
		
		return menuService.uniqName(name,parentId,permit,menuId);
	}
}
