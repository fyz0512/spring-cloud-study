package org.finchley.study.utils;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpContextUtils {
	
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 检查Request中是否存在不能为null的field
	 * @param fields
	 * @return
	 */
	public static boolean checkField(List<String> fields) {
		
		HttpServletRequest request = getHttpServletRequest();
		
		if(fields!=null)
			for(String f :fields) {
				if(request.getParameter(f)==null) {
					Assert.notNull(request.getParameter(f),f+" must not null.");
					return false;
				}
			
			}
		return true;
	}
	
}
