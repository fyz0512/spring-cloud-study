package org.finchley.study.service;

import java.util.concurrent.TimeUnit;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.dto.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	
	
	public void saveUserToken(String userId,UserToken userToken) {
		
		redisTemplate.opsForValue().set(CommonConstants.CONTEXT_USER_ID+"_"+userId, userToken,CommonConstants.TOKEN_EXPIRE,TimeUnit.MILLISECONDS);
		
	}
	
	
	public UserToken getUserTokenInCache(String userId) {
		
		Object rt = redisTemplate.opsForValue().get(CommonConstants.CONTEXT_USER_ID+"_"+userId);
		if(rt!=null && rt instanceof UserToken)
			return (UserToken) rt;
		else
			return null;
		
	}
	
	/**
	 * 删除用户登录TOKEN
	 * @param userId
	 */
	public void removeUserToken(String userId) {
		
		redisTemplate.delete(CommonConstants.CONTEXT_USER_ID+"_"+userId);
		
	}
	
}
