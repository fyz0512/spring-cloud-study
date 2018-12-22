package org.finchley.zuul.service;

import org.finchley.study.intercepter.FeignIntercepter;
import org.finchley.study.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import feign.Headers;

/**
 * 使用Feign访问service-admin服务验证访许可。
 * @author Administrator
 *
 */
@Headers("Content-Type:application/json")
@FeignClient(name = "service-admin", configuration = FeignIntercepter.class)
public interface MenuService {
	
	/**
	 * 检查URI是否获得许可。
	 * @param uri
	 * @return
	 */
	@GetMapping("/checkPermit")
	ResponseData checkPermit(String uri);
	
}
