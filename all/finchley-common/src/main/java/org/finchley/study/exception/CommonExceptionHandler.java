package org.finchley.study.exception;


import org.finchley.study.constants.CommonConstants;
import org.finchley.study.response.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author John Fang
 *
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseData exception(Exception e) {
        return ResponseData.error(CommonConstants.RESP_ERROR, "系统运行错误。");
    }
}
