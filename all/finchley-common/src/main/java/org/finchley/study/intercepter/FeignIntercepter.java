package org.finchley.study.intercepter;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 使用Feign客户端是的解释器。
 * @author Administrator
 *
 */
public class FeignIntercepter implements RequestInterceptor {
    
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("------feign设置token" + Thread.currentThread().getId());
        requestTemplate.header(CommonConstants.CONTEXT_TOKEN, SessionContext.getToken());
        
    }
}