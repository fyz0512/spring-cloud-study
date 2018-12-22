package org.finchley.study.response;

import java.util.HashMap;
import java.util.Map;

import org.finchley.study.constants.CommonConstants;

/**
 * Web响应数据体
 * @author John Fang
 *
 */
public class ResponseData extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    final public static String CODE_TAG = "code";
    final public static String DATA_TAG = "data";
    final public static String MSG_TAG = "msg";
    
    public ResponseData() {
        put(CODE_TAG, CommonConstants.RESP_SUCCESS);
        put(MSG_TAG, "操作成功");
    }

    public static ResponseData error() {
        return error(CommonConstants.RESP_ERROR, "操作失败");
    }

    public static ResponseData operate(boolean b){
        if(b){
            return ResponseData.ok();
        }
        return ResponseData.error();
    }

    public static ResponseData error(String msg) {
        return error(CommonConstants.RESP_ERROR, msg);
    }

    public static ResponseData error(int code, String msg) {
        ResponseData r = new ResponseData();
        r.put(CODE_TAG, code);
        r.put(MSG_TAG, msg);
        return r;
    }

    public static ResponseData ok(String msg) {
        ResponseData r = new ResponseData();
        r.put(MSG_TAG, msg);
        return r;
    }

    public static ResponseData ok(Map<String, Object> map) {
        ResponseData r = new ResponseData();
        r.putAll(map);
        return r;
    }

    public static ResponseData ok() {
        return new ResponseData();
    }
    
    public static ResponseData requestError() {
    	return error(CommonConstants.RESP_BADREQ, "请求数据有误");
    }
    
    public static ResponseData errorExpire() {
    	return error(CommonConstants.RESP_NOTLOGIN, "登录凭据过期");
    }

    public static ResponseData error401() {
        return error(CommonConstants.RESP_NOTLOGIN, "你还没有登录");
    }

    public static ResponseData error403() {
        return error(CommonConstants.RESP_NOPERMIT, "你没有访问权限");
    }

    public static ResponseData data(Object data){
        return ResponseData.ok().put(DATA_TAG,data);
    }

    public static ResponseData page(Object page){
        return ResponseData.ok().put("page",page);
    }

    @Override
    public ResponseData put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
