package org.finchley.study.controller;

import java.sql.Date;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.finchley.study.annotation.Log;
import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.PageDTO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.utils.HttpContextUtils;
import org.finchley.study.utils.MD5Utils;
import org.finchley.study.dto.UserDO;
import org.finchley.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	UserService userService;
	
	
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
		
		if(request.getParameter("name")!=null && request.getParameter("name").length()>0) {
			q.addParam("nameL", request.getParameter("name"));
		}
		
		if(request.getParameter("mobile")!=null && request.getParameter("mobile").length()>0) {
			q.addParam("mobileL", request.getParameter("mobile"));
		}
		if(request.getParameter("email")!=null && request.getParameter("email").length()>0) {
			q.addParam("emailL", request.getParameter("email"));
		}
		if(request.getParameter("status")!=null && request.getParameter("status").length()>0) {
			q.addParam("status", request.getParameter("status"));
		}
		page = userService.list(q);
		
		page.setTotal(userService.getCount(q));
		page.setPage(pageNum);
		page.setPageSize(q.getLimit());
		
		return page;
	}
	
	@Log("修改密码")
	@RequestMapping("/modipass")
	public ResponseData modiPassword(HttpServletRequest request) {
	
		
		String oldPass = request.getParameter("oldpass");
		String newPass = request.getParameter("newpass");
		String confirmPass = request.getParameter("confirmpass");
		
		Assert.notNull(oldPass,"old password must not null.");
		Assert.notNull(newPass,"new password must not null.");
		
		if(newPass.equals(confirmPass)) {
			
			return ResponseData.error("新密码两次输入不一致");
			
		}
		
		return userService.changePassword(oldPass, newPass,SessionContext.getUserID());
	}
	
	
	@RequestMapping("add")
	public ResponseData addUser(HttpServletRequest request){
		
		if(request.getParameter("save")==null) {
			//
			return userService.add();
		}else {
			
			if(HttpContextUtils.checkField(Arrays.asList(new String[] {"name","username","password"}))) {
				
				UserDO user = new UserDO();
						
				user.setName(request.getParameter("name"));
				user.setUsername(request.getParameter("username"));
				user.setEmail(request.getParameter("email"));
				user.setPassword(MD5Utils.string2MD5(request.getParameter("password")));
				user.setMobile(request.getParameter("mobile"));
				if(request.getParameter("sex")!=null && !"".equals(request.getParameter("sex")))
					try{user.setSex(Integer.parseInt(request.getParameter("sex")));}catch(Exception e) {}
				user.setHobby(request.getParameter("hobby"));
				user.setLive_address(request.getParameter("live_address"));
				if(request.getParameter("birth")!=null && !"".equals(request.getParameter("birth")))
					try{user.setBirth(Date.valueOf(request.getParameter("birth")));}catch(Exception e) {}
				
				return userService.createUser(user);
			}
			
			
		}
		
		
		return ResponseData.error(400, "无效请求。");
	}
	
	@RequestMapping("get")
	public ResponseData getUser(HttpServletRequest request) {
		
		String userId= request.getParameter("user_id");
		if(userId!=null) {
			return userService.edit(userId);
		}
		return ResponseData.error(400, "无效请求。");
	}
	@RequestMapping("edit")
	public ResponseData editUser(HttpServletRequest request) {
		
		String userId= request.getParameter("user_id");
		if(userId!=null) {
			if(request.getParameter("save")==null) {
				//
				return userService.edit(userId);
			}else {
				UserDO user = new UserDO();
				user.setUser_id(Long.parseLong(request.getParameter("user_id")));
				if(request.getParameter("birth")!=null)
					user.setBirth(Date.valueOf(request.getParameter("birth")));
				user.setEmail(request.getParameter("email"));
				user.setMobile(request.getParameter("mobile"));
				if(request.getParameter("sex")!=null)
				user.setSex(Integer.parseInt(request.getParameter("sex")));
				user.setHobby(request.getParameter("hobby"));
				user.setLive_address(request.getParameter("live_address"));
				
				return userService.updateUser(user);
			}
		}
		return ResponseData.requestError();
	}
	
	@Log("重置用户密码")
	@RequestMapping("resetPwd")
	public ResponseData resetPassword(@RequestParam("user_id") String[] userIds) {
		
		if(userIds!=null && userIds.length>0) {
			return userService.resetUserPass(userIds);
		}
		return ResponseData.error(400, "无效请求。");
	}
	
	@Log("启用用户")
	@RequestMapping("enable")
	public ResponseData enableUser(@RequestParam("user_id") String[] userIds) {
		
		if(userIds!=null && userIds.length>0) {
		
			return userService.enableUser(userIds);
		}
		
		return ResponseData.error(400, "无效请求。");
	}
	
	@Log("禁用用户")
	@RequestMapping("forbidden")
	public ResponseData forbbidenUser(@RequestParam("user_id") String[] userIds) {
		
		if(userIds!=null && userIds.length>0) {
		
			return userService.forbiddenUser(userIds);
		}
		
		return ResponseData.error(400, "无效请求。");
	}
	
	@Log("删除用户")
	@RequestMapping("del")
	public ResponseData delete(@RequestParam("user_id") String[] userIds) {
		
		
		if(userIds!=null && userIds.length>0) {
		
			
			return userService.delete(userIds);
		}
		
		return ResponseData.error(400, "无效请求。");
	}
	
	@RequestMapping("uniqName")
	public ResponseData uniquenceCheck(@RequestParam(name="username",required=false) String username,
			@RequestParam(name="email",required=false) String email,
			@RequestParam(name="mobile",required=false) String mobile,
			@RequestParam(name="user_id",required=false) String userId) {
		if(username!=null) {
			return userService.uniqName("username",username,"用户账号",userId);
		}else if(email!=null) {
			return userService.uniqName("email",email,"电子邮箱",userId);
		}else if(mobile!=null) {
			return userService.uniqName("mobile",mobile,"手机号码",userId);
		} 
		return ResponseData.error(400, "无效请求。");
	}
	
	
	@RequestMapping("setRole")
	public ResponseData setUserRole(@RequestParam(name="role_id",required=false) String[] roleIds,
			@RequestParam("user_id") String userId) {
		
		if(roleIds!=null && roleIds.length>0) {
			 
			
			return userService.setUserRoles(userId,roleIds);
		}else {
			return userService.getUserRoles(userId); //如果没有传入角色数据返回获取该用户的角色数据。
		}
		
	}
}
