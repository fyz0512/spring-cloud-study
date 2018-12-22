package org.finchley.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.finchley.study.annotation.Log;
import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.UserToken;
import org.finchley.study.response.ResponseData;
import org.finchley.study.service.CacheService;
import org.finchley.study.utils.HttpContextUtils;
import org.finchley.study.utils.JwtUtils;
import org.finchley.study.dto.UserDO;
import org.finchley.study.service.MenuService;
import org.finchley.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthzController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CacheService cacheService;

	@Autowired
	MenuService menuService;
	
	@Log("登录")
	@RequestMapping("/login")
	public ResponseData login(HttpServletRequest request) {
		
		 String userName = request.getParameter("username");
		 String password = request.getParameter("password");
		 String extcode = request.getParameter("extcode");
		
		Assert.notNull(userName,"user name must not null.");
		Assert.notNull(password,"password must not null.");
		
		ResponseData resp = userService.login(userName,password,extcode);
		
		if(resp.get(ResponseData.DATA_TAG)!=null && resp.get(ResponseData.DATA_TAG) instanceof UserDO) {
			
			UserDO user = (UserDO)resp.get(ResponseData.DATA_TAG);
			
			//jjwt生成Token
			UserToken utoken = new UserToken(user.getUsername(),user.getUser_id().toString(),user.getName());
			
			String tokenStr = "";
			try {
				tokenStr = JwtUtils.generateToken(utoken, CommonConstants.TOKEN_EXPIRE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Token保存如缓存
			try {
				cacheService.saveUserToken(user.getUser_id().toString(), JwtUtils.getInfoFromToken(tokenStr));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//jjwtToken加入响应头和Cookie。
			HttpContextUtils.getHttpServletResponse().setHeader(CommonConstants.CONTEXT_TOKEN, tokenStr);
			HttpContextUtils.getHttpServletResponse().addCookie(new Cookie(CommonConstants.CONTEXT_TOKEN, tokenStr));
			
			//取固定菜单
			Map<String,String> fixedMenus = new HashMap<String,String>();
			for(MenuDTO m : menuService.getFixedMenus()) {
				fixedMenus.put(m.getPermit().toUpperCase(), m.getUrl());
			}
			
			return ResponseData.data(fixedMenus);
		}else {
			return resp;
		}
		
	}
	
	
	@RequestMapping("/reload")
	public ResponseData reload() {
		
		Map<String,String> fixedMenus = new HashMap<String,String>();
		for(MenuDTO m : menuService.getFixedMenus()) {
			fixedMenus.put(m.getPermit().toUpperCase(), m.getUrl());
		}
		
		return ResponseData.data(fixedMenus);
	}
	
	
	
	@Log("登出")
	@RequestMapping("/logout")
	public ResponseData logout() {
		
		//清空Token缓存
		
		cacheService.removeUserToken(SessionContext.getUserID());
		
		return ResponseData.ok( "已成功登出系统。");
	}
	
	
	@RequestMapping("/checkToken")
	public ResponseData checkToken(UserToken userToken) {
		
		UserToken incache = cacheService.getUserTokenInCache(userToken.getUserId());
		
		if(incache.toString().equals(userToken.toString()))
			return ResponseData.ok();
		
		return ResponseData.error403();
		
	}
	
	@RequestMapping("/checkPermit")
	public ResponseData checkPermit(String uri) {
		
		if(uri==null) return  ResponseData.ok();
		boolean hasPermit = false;
		for(MenuDTO m : menuService.getFixedMenus()) {
			if(uri.startsWith(m.getUrl())){
				hasPermit = true;
				break;
			}
		}
		if(!hasPermit) {
			for(MenuDTO m : menuService.getUserMenus(SessionContext.getUserID())) {
				if(uri.startsWith(m.getUrl())){
					hasPermit = true;
					break;
				}
			}
		}
		if(hasPermit)
			return ResponseData.ok();
		else
			return ResponseData.error403();
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
	
	@RequestMapping("/extcode")
	public ResponseData extCode() {
		
		
		String msg = Double.toString(Math.random()*10000);
		Map<String,Object> msgs = new HashMap<String,Object>();
		msgs.put("extcode", msg.substring(0, msg.indexOf('.')));
		try {
			msgs.put("salt", JwtUtils.generateSalt(msg));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseData.error("获取扩展码失败。");
		}
		return ResponseData.ok(msgs);
		
	}
}
