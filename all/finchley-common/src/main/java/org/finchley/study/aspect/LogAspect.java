package org.finchley.study.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.finchley.study.annotation.Log;
import org.finchley.study.context.SessionContext;
import org.finchley.study.dto.LogDO;
import org.finchley.study.service.LogSaveService;
import org.finchley.study.utils.HttpContextUtils;
import org.finchley.study.utils.IPUtils;
import org.finchley.study.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志前面组件，对使用了org.finchely.study.annotation.Log注解的方法进行环绕切面，并将请求和响应都记入日志中。
 * @author John Fang
 *
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    LogSaveService logService;


    @Pointcut("@annotation(org.finchley.study.annotation.Log)")
    public void logPointCut() {
    	
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time,result);
        return result;
    }

    /**
     * 保存日志
     * @param joinPoint 切点入口
     * @param time 执行时常
     * @param result 执行结果
     * @throws InterruptedException
     */
    void saveLog(ProceedingJoinPoint joinPoint, long time,Object result) throws InterruptedException {
        
    	MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        
        LogDO log = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            log.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = sign.getName();
        log.setMethod(className + "." + methodName + "()");
        // 请求的参数
        
        Object[] args = joinPoint.getArgs();
        Parameter[] ps= method.getParameters();
        Map<String,Object> params = new HashMap<String,Object>();
        for(int i = 0;i<ps.length;i++) {
        	String pn = ps.getClass().getName()+"-"+ps[i].getName();
        	if(args.length>i)
        		try {
        			if(args[i] instanceof HttpServletRequest){
        				//Request请求对象只取其中的请求参数即可。
        				params.put(pn,((HttpServletRequest) args[i]).getParameterMap());
		        		
        			}else 
        				params.put(pn, args[i]);
		        	
        		} catch (Exception e) {
        			logger.error("转换操作参数异常。", e);
        		}
        	else
        		params.put(pn, null);
        }
        if(!params.isEmpty())
        	log.setParams(JSONUtils.beanToJson(params));
        if(result!=null) {
        		String r = JSONUtils.beanToJson(result);
        		if(r.length()>5000) r = r.substring(0,4999);
        		log.setResult(r);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        log.setIp(IPUtils.getIpAddr(request));
        // 用户名

        log.setUserId(Long.parseLong(SessionContext.getUserID() == null ? "0" : SessionContext.getUserID()));
        log.setUsername(SessionContext.getUsername() == null ? "" : SessionContext.getUsername());
        log.setTime(time);
        // 系统当前时间
        Date date = new Date();
        log.setGmtCreate(date);
        // 保存系统日志
        logService.save(log);
    }
}
