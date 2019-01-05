package org.finchley.study.exception;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.finchley.study.constants.CommonConstants;
import org.finchley.study.response.ResponseData;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author John Fang
 *
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    /**
     * 系统运行错误的返回处理
     * @param e
     * @return
     */
	@ExceptionHandler(Exception.class)
    ResponseData exception(Exception e) {
		//如果是数据绑定错误，返回错误提示信息
		if(e instanceof BindException) {
			String estr = ((BindException) e).getBindingResult().getAllErrors()
					.stream().map(oe -> oe.getDefaultMessage()).reduce("", String::concat);

			return ResponseData.error(CommonConstants.RESP_ERROR,estr);
		}
		
        return ResponseData.error(CommonConstants.RESP_ERROR, "系统运行错误。");
    }
    
	

}
