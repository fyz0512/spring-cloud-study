package org.finchley.study.utils;


import java.util.Date;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.dto.UserToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author bootdo 1992lcg@163.com
 * @version V1.0
 */
public class JwtUtils {
	
	/**
	 * 生成登录后token
	 * @param userToken
	 * @param expire
	 * @return
	 * @throws Exception
	 */
    public static String generateToken(UserToken userToken, long expire) throws Exception {
        String token = Jwts.builder()
                .setSubject(userToken.getUsername())
                .claim(CommonConstants.CONTEXT_USER_ID, userToken.getUserId())
                .claim(CommonConstants.CONTEXT_NAME, userToken.getName())
                .claim(CommonConstants.RENEWAL_TIME,new Date(System.currentTimeMillis()+expire/2))
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY)
                .compact();
        return token;
    }

    /**
     * 从token获取用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public static UserToken getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(token)
                .getBody();
        UserToken ut = new UserToken(claims.getSubject(), 
        		claims.get(CommonConstants.CONTEXT_USER_ID).toString(),
        		claims.get(CommonConstants.CONTEXT_NAME).toString());
        ut.setExpire(claims.getExpiration().getTime());
        
        return ut;
    }
    
    /**
     * 加密生成登录的密码盐
     * @param code
     * @return
     * @throws Exception
     */
    public static String generateSalt(String code)throws Exception {
    	return Jwts.builder().signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY).setSubject(code).compact();
    }
    
    /**
     * 解密获得密码盐
     * @param salt
     * @return
     * @throws Exception
     */
    public static String getPasswordSalt(String salt) throws Exception {
    	Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(salt)
                .getBody();
    	return claims.getSubject();
    }
}
