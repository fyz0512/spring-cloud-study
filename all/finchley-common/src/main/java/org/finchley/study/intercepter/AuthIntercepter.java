package org.finchley.study.intercepter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.UserToken;
import org.finchley.study.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 用户安全验证解释器
 * @author John Fang
 *
 */
public class AuthIntercepter extends HandlerInterceptorAdapter {
	
    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
    	logger.info("visit url-->"+request.getRequestURI());
    	
    	String token = request.getHeader(CommonConstants.CONTEXT_TOKEN);
        UserToken userToken = JwtUtils.getInfoFromToken(token);
        SessionContext.setToken(token);
        logger.info("------设置token"+Thread.currentThread().getId());
        SessionContext.setUsername(userToken.getUsername());
        SessionContext.setName(userToken.getName());
        SessionContext.setUserID(userToken.getUserId());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionContext.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
