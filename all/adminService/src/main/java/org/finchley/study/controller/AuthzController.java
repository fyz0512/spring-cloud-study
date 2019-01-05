package org.finchley.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.finchley.study.annotation.Log;
import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.LoginDTO;
import org.finchley.study.dto.MenuDTO;
import org.finchley.study.dto.UserDO;
import org.finchley.study.dto.UserToken;
import org.finchley.study.response.ResponseData;
import org.finchley.study.service.AuthzService;
import org.finchley.study.service.CacheService;
import org.finchley.study.service.MenuService;
import org.finchley.study.utils.HttpContextUtils;
import org.finchley.study.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthzController {
	
	@Autowired
	AuthzService authzService;
	
	@Autowired
	CacheService cacheService;

	@Autowired
	MenuService menuService;
	
	@Log("登录")
	@RequestMapping("/login")
	public ResponseData login(@Valid LoginDTO loginInfo) {
		
		
		ResponseData resp = authzService.login(loginInfo);
		
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
		
		return authzService.logout();
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
