package org.finchley.study.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.finchley.study.context.SessionContext;
import org.finchley.study.dao.UserDao;
import org.finchley.study.dto.UserDO;
import org.finchley.study.query.Query;
import org.finchley.study.response.ResponseData;
import org.finchley.study.utils.JwtUtils;
import org.finchley.study.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthzService {

	static Logger LOG = LoggerFactory.getLogger(AuthzService.class);
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CacheService cacheService;

	
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param extcode
	 * @return
	 */
	public ResponseData login(String userName,String password,String extcode) {
		
		String name = "username";
		
		String emailReg = "^[a-zA-Z0-9][\\w\\.\\-_]+@[\\w][\\w\\.\\-_]+[a-zA-Z]$";
		String mobileReg = "\\d{13}";
		
		if(Pattern.matches(emailReg, userName)) {
			name = "email";
		}else if(Pattern.matches(mobileReg, userName)) {
			name = "mobile";
		}
		
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(name, userName);
		params.put("status", 1);	//用户状态标志 1：有效。
		
		Query query = new Query(params);
		
		
		
		try {
			UserDO user =  userDao.getUser(query);
			//验证密码是否有效
			
			String upass = user.getPassword();
			String salt = "";
			if(extcode!=null) salt=JwtUtils.getPasswordSalt(extcode); //获得加密的附加码
			
			upass = MD5Utils.string2MD5(upass+(salt.indexOf('.')>0 ? salt.substring(0,salt.indexOf('.')):salt));
			
			if(!upass.equals(password)) {
				//密码不一致
				return ResponseData.error(401, "用户名或密码错误。");
				
			}else
				return ResponseData.data(user);
		}catch(Exception e) {
			return ResponseData.error(401, "不存在的用户");
		}
	}
	
	/**
	 * 登出
	 * @return
	 */
	public ResponseData logout() {
		
		//清空Token缓存
		cacheService.removeUserToken(SessionContext.getUserID());
		
		return ResponseData.ok( "已成功登出系统。");
	}
}
