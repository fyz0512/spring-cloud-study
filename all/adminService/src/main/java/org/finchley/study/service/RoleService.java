package org.finchley.study.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finchley.study.annotation.Log;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dao.MenuDao;
import org.finchley.study.dao.RoleDao;
import org.finchley.study.dto.MenuTreeDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.dto.RoleDO;
import org.finchley.study.dto.TableDTO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	MenuDao menuDao;
	
	@Autowired
	RoleDao roleDao;
	
	private static Logger LOG = LoggerFactory.getLogger(RoleService.class);
	
	
	public PageDTO list(Query q){
		
		PageDTO p = new PageDTO();
		p.setPage(q.getPage());
		p.setPageSize(q.getLimit());
		
		
		TableDTO t = new TableDTO();
		t.setRowItems(roleDao.list(q));
		p.setData(t);
		
		return p;
	}
	
	public RoleDO getRole(String roleId) {
		
		Query q = new Query();
		q.put("role_id", roleId);
		
		return roleDao.getRole(q);
		
	}
	
	public int getCount(Query q) {
		
		return roleDao.getCount(q);
	}
	
	public List<MenuTreeDTO> getRoleMenu(String roleId){
		
		Query q = new Query();
		q.addParam("status", 1);
		q.addParam("types", Arrays.asList(new Integer[] {0,1,2}));
		q.addParam("role_id", roleId);
		
		return roleDao.getRoleMenu(q);
		
	}
	
	@Log("修改角色菜单")
	public ResponseData setRoleMenu(String roleId,String[] menuIds) {
		
		if(menuIds!=null && menuIds.length>0) {
			
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("role_id", roleId);
			m.put("list", Arrays.asList(menuIds));
			int count = roleDao.setRoleMenu(m);
			if(count>=0) {
				return ResponseData.ok("成功修改角色菜单。");
			}else
				return ResponseData.ok("修改角色菜单失败。");
		}
		return ResponseData.requestError();
	}
	
	
	public ResponseData add() {
		
		return ResponseData.ok();
	}
	
	@Log("创建角色")
	public ResponseData createRole(RoleDO role) {
		
		if(roleDao.createRole(role)>0){
			return ResponseData.ok("角色创建成功");
		}else
			return ResponseData.ok("角色创建失败");
				
	}
	
	
	@Log("更新角色")
	public ResponseData updateRole(RoleDO role) {
		
		if(role==null) return ResponseData.error(201, "角色数据为空。");
		if(role.getRole_id()==null) return ResponseData.error(201, "角色数据无法定位。");
				
		role.setGmt_modified(new Date());
		role.setUser_id_modify(Long.parseLong(SessionContext.getUserID()));
		
		if( roleDao.updateRole(role)>0) {
			return ResponseData.ok("角色更新成功。");
		}else
			return ResponseData.ok("角色更新失败。");
		
	}
	
	@Log("删除角色")
	public ResponseData delete(String[] roleIds) {
		if(roleIds!=null && roleIds.length>0) {
			try {
				int retcount = roleDao.delete(Arrays.asList(roleIds));
				return ResponseData.ok("成功删除"+retcount+"个角色");
			}catch(Exception e) {
				LOG.error("删除角色异常", e);
			}
		}
		return ResponseData.ok("没有角色需要删除。");
	}
	
	public ResponseData uniqName(String field,String name,String comment,String roleId) {
		if(name!=null) {
			Query q = new Query();
			q.put(field, name);
			q.put("not_role_id", roleId);
			RoleDO role = roleDao.getRole(q);
			if(role==null) {
				return ResponseData.ok(comment+"可用");
			}else
				return ResponseData.error(201, comment+"已被使用！");
		}
		return ResponseData.ok(comment+"为空。");
	}
}
