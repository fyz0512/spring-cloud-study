package org.finchley.zuul.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.UserToken;
import org.finchley.study.response.ResponseData;
import org.finchley.study.utils.HttpRequestDeviceUtils;
import org.finchley.study.utils.IPUtils;
import org.finchley.study.utils.JSONUtils;
import org.finchley.study.utils.JwtUtils;
import org.finchley.zuul.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 网关控制，对所有提交微服务的请求进行许可验证
 * @author Administrator
 *
 */
public class AccessFilter extends ZuulFilter {
    
	static Logger LOG = LoggerFactory.getLogger(AccessFilter.class);
	
	@Autowired
    MenuService menuService;


    private String openURIs = "/service-admin/login,/service-admin/extcode,/service-admin/resetpass";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        LOG.info("visit tartet url="+requestUri+" request client:"+IPUtils.getIpAddr(request));
        if (isOpenPermit(requestUri)) {
            return null;
        }
        String accessToken = request.getHeader(CommonConstants.CONTEXT_TOKEN);
        if(null == accessToken || accessToken == ""){
            accessToken = request.getParameter(CommonConstants.TOKEN);
        }
        if (null == accessToken) {
            setFailedRequest(ResponseData.error401(), 401);
            return null;
        }
        if(HttpRequestDeviceUtils.isMobileDevice(request)) {
        	LOG.info("mobile device visit:"+requestUri);
        }else {
        	LOG.info("pc device visit:"+requestUri);
        }
        
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(accessToken);
            //许可是否已经超期
            if(userToken.getExpire()>0 && userToken.getExpire() < System.currentTimeMillis()) {
            	setFailedRequest(ResponseData.errorExpire(),400);
            	return null;
            }
            
    
        } catch (Exception e) {
            setFailedRequest(ResponseData.error401(), 401);
            return null;
        }
        
        SessionContext.setToken(accessToken);
        if(!havePermission(request.getRequestURI())){
            setFailedRequest(ResponseData.error403(), 403);
            return null;
        }
        return null;

    }

    private void setFailedRequest(Object body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        HttpServletResponse response = ctx.getResponse();
        PrintWriter out = null;
        try{
            out = response.getWriter();
            out.write(JSONUtils.beanToJson(body));
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
        ctx.setSendZuulResponse(false);
    }

    /**
     * 检查请求是否获得授权
     * @param request
     * @return
     */
	private boolean havePermission(String currentURL){
        
        ResponseData rd = menuService.checkPermit(currentURL);
        
        return (rd.get("code")!=null && CommonConstants.RESP_SUCCESS == Integer.parseInt(rd.get("code").toString()));
        
    }

	/**
	 * 判断是否是无需验证学的URI
	 * @param requestUri
	 * @return
	 */
    private boolean isOpenPermit(String requestUri) {
        boolean flag = false;
        for (String s : openURIs.split(",")) {

            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}
