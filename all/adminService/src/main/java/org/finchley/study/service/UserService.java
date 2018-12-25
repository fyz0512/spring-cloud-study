package org.finchley.study.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finchley.study.annotation.Log;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dao.DictionaryDao;
import org.finchley.study.dao.RoleDao;
import org.finchley.study.dao.UserDao;
import org.finchley.study.dto.DictionaryDTO;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.dto.RoleDO;
import org.finchley.study.dto.TableDTO;
import org.finchley.study.dto.UserDO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	DictionaryDao dictionaryDao;

	private static Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	
	
	
	private UserDO getUser(String userId) {
		Query query = new Query();
		query.put("userId", userId);
		return userDao.getUser(query);
	}
	

	public PageDTO list(Query q) {
		
		
		PageDTO p = new PageDTO();
		p.setPage(q.getPage());
		p.setPageSize(q.getLimit());
		
		
		TableDTO t = new TableDTO();
		t.setRowItems(userDao.list(q));
		p.setData(t);
		
		q = new Query();
		q.addParam("parentId", 2);
		List<DictionaryDTO> status = dictionaryDao.getDict(q);
		Map<String,List<DictionaryDTO>> l = new HashMap<String,List<DictionaryDTO>>();
		l.put("status", status);
		p.setSearchs(l);
		
		return p;
	}
	
	public int getCount(Query q) {
		
		return userDao.getCount(q);
	}
	
	public ResponseData add() {

		ResponseData rd =ResponseData.ok();
		Query q = new Query();
		q.addParam("parentId", 1);
		List<DictionaryDTO> sexs = dictionaryDao.getDict(q);
		rd.put("sex", sexs);
		
		return rd;
	}
	
	@Log("创建用户")
	public ResponseData createUser(UserDO user) {
		ResponseData rd =ResponseData.ok();
		if(user.getUsername()== null || user.getName()== null || user.getPassword()==null) {
			rd.put(ResponseData.CODE_TAG, "201");
			rd.put(ResponseData.MSG_TAG, "必填字段有空缺，新增失败。");
		}else {
			
			if(user.getGmt_create()==null) user.setGmt_create(new Date());
			if(user.getUser_id_create()==null) user.setUser_id_create(Long.parseLong(SessionContext.getUserID()));
			try {
				if(userDao.createUser(user) == 1) {
					rd.put(ResponseData.MSG_TAG, "用户添加成功！");
				}else {
					rd.put(ResponseData.CODE_TAG, "201");
					rd.put(ResponseData.MSG_TAG, "用户添加失败。");
				}
			}catch(Exception e) {
				LOG.error("创建用户异常", e);
			}
		}
		
		return rd;
	}
	
	/**
	 * 用户修改密码
	 * @param oldPass 
	 * @param newPass 
	 * @param userId 
	 * @return
	 */
	@Log("用户修改自己的密码")
	public ResponseData changePassword(String oldPass ,String newPass, String userId) {
		
		;
		UserDO user = getUser(userId);
		
		if(user!=null && oldPass.equals(user.getPassword())) {
			
			UserDO newOne = new UserDO();
			
			newOne.setUser_id(user.getUser_id());
			newOne.setPassword(MD5Utils.string2MD5(newPass));
			try {
				ResponseData rd = updateUser(newOne);
				if(Integer.parseInt(rd.get(ResponseData.CODE_TAG).toString())==200) {
					return rd.put(ResponseData.MSG_TAG, "密码更新成功，请记住新密码。");
				}
			}catch(Exception e) {
				LOG.error("修改用户密码异常", e);
			}
		}else {
			
			return ResponseData.error(400, "旧密码输入错误");
		}
		
		return ResponseData.error( "修改密码失败。");

	}
	
	/**
	 * 修改用户信息
	 * @param userId
	 * @return
	 */
	public ResponseData edit(String userId) {

		ResponseData rd =ResponseData.ok();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		UserDO user = userDao.getUser(new Query(params));
		if(user!=null) {
			//清除用户密码
			user.setPassword(null);
			rd.put("data",user);
			
			Query q = new Query();
			q.addParam("parentId", 1);
			List<DictionaryDTO> sexs = dictionaryDao.getDict(q);
			rd.put("sex", sexs);
			
			q = new Query();
			q.addParam("parentId", 2);
			List<DictionaryDTO> status = dictionaryDao.getDict(q);
			rd.put("status", status);
			
			return rd;
		}
		return ResponseData.error("找不到可以编辑的用户。");
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@Log("更新用户")
	public ResponseData updateUser(UserDO user) {
		
		if(user.getUser_id()!=null) {
			try {
				int count =userDao.updateUser(user);
				if(count==1) 
					return ResponseData.ok("用户更新成功");
			}catch(Exception e) {
				LOG.error("更新用户异常", e);
			}
			
		}
		
		return ResponseData.error("更新用户失败。");
	}
	
	/**
	 * 获取到所有的角色
	 * @return
	 */
	public ResponseData getUserRoles(String userId) {
		
		Query q = new Query();
		List<RoleDO> l = roleDao.list(q);
		ResponseData rd = ResponseData.data(l);
		q.addParam("user_id", userId);
		List<RoleDO> ul = userDao.getUserRole(q);
		rd.put("checked", ul);
		
		return rd;
	}
	
	/**
	 * 设置用户角色
	 * @param m
	 * @return
	 */
	@Log("设置用户角色")
	public ResponseData setUserRoles(String userId,String[] roleIds) {
		if(roleIds!=null && roleIds.length>0) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("user_id",userId);
			m.put("list", Arrays.asList(roleIds));
			try {
				int count = userDao.setUserRole(m);
				if(count>=0) {
					return ResponseData.ok("成功设置用户角色");
				}
			}catch(Exception e) {
				LOG.error("设置用户角色异常", e);
			}
		}
		return ResponseData.ok("设置用户角色失败");
	}
	
	/**
	 * 重置用户密码
	 * @param userIds
	 * @return
	 */
	@Log("重置用户密码")
	public ResponseData resetUserPass(String[] userIds) {
		
		if(userIds!=null && userIds.length>0) {
		
			String defaultPwd = "888888";
			//取默认密码
			Query q = new Query();
			q.put("type", "defaultPwd");
			List<DictionaryDTO> dict = dictionaryDao.getDict(q);
			if(dict.size()>0) defaultPwd = dict.get(0).getValue();
			
			StringBuilder ret = new StringBuilder();
			
			for(String userId : userIds) {
				UserDO user = getUser(userId);
				if(user!=null) {
					user.setPassword(MD5Utils.string2MD5(defaultPwd));
					user.setGmt_modified(new Date());
					if(userDao.updateUser(user)==1) {
						ret.append(user.getName()+"(id="+userId+")成功;");
					}else
						ret.append(user.getName()+"(id="+userId+")失败;");
				}else
					ret.append("(id="+userId+")用户不存在;");
			}
			return ResponseData.ok("重置密码结果："+ret.toString());
		}
		
		return ResponseData.ok("没有用户需要重置密码。");
	}

	/**
	 * 启用用户
	 * @param userIds
	 * @return
	 */
	@Log("启用用户")
	public ResponseData enableUser(String[] userIds) {

		if(userIds!=null && userIds.length>0) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("status", 1);
			m.put("list", Arrays.asList(userIds));
			try {
				int ret = userDao.updateState(m);
				if(ret>0)
					return ResponseData.ok("启用操作结果：成功启用"+ret+"位用户。");
			}catch(Exception e) {
				LOG.error("启用用户异常", e);
			}
		}
		
		return ResponseData.ok("没有用户需要重新启用。");
	}

	/**
	 * 禁用用户
	 * @param userIds
	 * @return
	 */
	@Log("禁用用户")
	public ResponseData forbiddenUser(String[] userIds) {

		if(userIds!=null && userIds.length>0) {
			
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("status", 0);
			m.put("list", Arrays.asList(userIds));
			try {
				int ret = userDao.updateState(m);
				if(ret>0)
					return ResponseData.ok("禁用操作结果：成功禁用"+ret+"位用户。");
			}catch(Exception e) {
				LOG.error("禁用用户异常", e);
			}
		}
		
		return ResponseData.ok("没有用户需要禁用。");
	}

	/**
	 * 删除用户
	 * @param userIds
	 * @return
	 */
	@Log("删除用户")
	public ResponseData delete(String[] userIds) {
		
		if(userIds!=null && userIds.length>0) {
			try {
				int retcount = userDao.delete(Arrays.asList(userIds));
				return ResponseData.ok("成功删除"+retcount+"位用户");
			}catch(Exception e) {
				LOG.error("删除用户异常", e);
			}
		}
		return ResponseData.ok("没有用户需要删除。");
	}

	/**
	 * 检查是否唯一
	 * @param field
	 * @param username
	 * @param comment
	 * @return
	 */
	public ResponseData uniqName(String field, String username,String comment,String userId) {
		if(username!=null) {
			Query q = new Query();
			q.put(field, username);
			q.put("not_user_id", userId);
			UserDO user = userDao.getUser(q);
			if(user==null) {
				return ResponseData.ok(comment+"可用");
			}else
				return ResponseData.error(201, comment+"已被使用！");
		}
		return ResponseData.ok(comment+"为空。");
	}
}
