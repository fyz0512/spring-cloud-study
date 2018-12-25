package org.finchley.study.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.dto.RoleDO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.service.RoleService;
import org.finchley.study.utils.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	
	@RequestMapping("add")
	public ResponseData addRole(HttpServletRequest request){
		
		if(request.getParameter("save")==null) {
			//
			return roleService.add();
		}else if(HttpContextUtils.checkField(Arrays.asList(new String[] {"role_name","role_sign"}))) {
		
			RoleDO role = new RoleDO();
			role.setRole_name(request.getParameter("role_name"));
			role.setRole_sign(request.getParameter("role_sign"));
			role.setRemark(request.getParameter("remark"));
			
			return roleService.createRole(role);
			
		}
		return ResponseData.error(400, "非法请求。");
	}
	
	
	@RequestMapping("list")
	public PageDTO query(HttpServletRequest request){
		
		
		int pageNum = 1;
		if(request.getParameter("page")!=null) {
			pageNum = Integer.parseInt(request.getParameter("page"));
		}
		
		PageDTO page = new PageDTO();
		
		Query q = new Query();
		q.addParam("page", pageNum);
		q.addParam("offset", (pageNum-1) * CommonConstants.PAGE_SIZE);
		q.addParam("limit", CommonConstants.PAGE_SIZE);
		
		if(request.getParameter("role_name")!=null && request.getParameter("role_name").length()>0) {
			q.addParam("role_nameL", request.getParameter("role_name"));
		}
		
		if(request.getParameter("role_sign")!=null && request.getParameter("role_sign").length()>0) {
			q.addParam("role_signL", request.getParameter("role_sign"));
		}
		
		page = roleService.list(q);
		
		page.setTotal(roleService.getCount(q));
		page.setPage(pageNum);
		page.setPageSize(q.getLimit());
		
		return page;
	}
	
	@RequestMapping("get")
	public ResponseData getRole(HttpServletRequest request) {
		
		String roleid = request.getParameter("role_id");
		
		if(roleid!=null) {
			
			return ResponseData.data(roleService.getRole(roleid));
			
		}
		return ResponseData.error(400, "非法请求。");
	}
	
	@RequestMapping("edit")
	public ResponseData editRole(HttpServletRequest request) {
		
		String roleid = request.getParameter("role_id");
		
		if(request.getParameter("save")==null) {
			
			return ResponseData.data(roleService.getRole(roleid));
			
		}else if(HttpContextUtils.checkField(Arrays.asList(new String[] {"role_id","role_name","role_sign"}))) {
			
			RoleDO role = new RoleDO();
			role.setRole_id(Long.parseLong(request.getParameter("role_id")));
			role.setRole_name(request.getParameter("role_name"));
			role.setRole_sign(request.getParameter("role_sign"));
			role.setRemark(request.getParameter("remark"));
			
			return roleService.updateRole(role);
			
		}
		
		return ResponseData.error(400, "非法请求。");
	}
	
	@RequestMapping("setMenu")
	public ResponseData setRoleMenu(@RequestParam("role_id") String roleId,
			@RequestParam(name="menu_id",required=false) String[] menuIds) {
		
		if(menuIds!=null && menuIds.length>0) {
			return roleService.setRoleMenu(roleId,menuIds);
		}else {
			List<MenuTreeDTO> l = roleService.getRoleMenu(roleId);
			//生成ztree菜单树的格式数据。
			if (l!=null && l.size()>0) {
				return ResponseData.data(l);
			}
			
			return ResponseData.requestError();
		}
		
		
	}
	
	@RequestMapping("del")
	public ResponseData delete(@RequestParam("role_id") String[] roleIds) {
		
		if(roleIds!=null && roleIds.length>0) {
			
			return roleService.delete(roleIds);
		}
		return ResponseData.error(400, "非法请求。");
	}
	
	@RequestMapping("uniqName")
	public ResponseData uniquenceCheck(@RequestParam(name="role_name",required=false) String role_name,
			@RequestParam(name="role_sign",required=false) String role_sign,
			@RequestParam(name="role_id",required=false) String roleId) {
		if(role_name!=null) {
			return roleService.uniqName("role_name",role_name,"角色名称",roleId);
		}else if(role_sign !=null) {
			return roleService.uniqName("role_sign",role_sign,"角色标识",roleId);
		} 
		return ResponseData.error(400, "无效请求。");
	}
}
