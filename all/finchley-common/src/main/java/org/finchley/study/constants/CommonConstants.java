package org.finchley.study.constants;

/**
 * 系统通用常量。
 * @author John Fang
 *
 */
public class CommonConstants {
	public final static String CONTEXT_TOKEN="AUZ";
    public final static String CONTEXT_USERNAME="CUN";
    public final static String CONTEXT_USER_ID="CUI";
    public final static String CONTEXT_NAME="CNM";
    
    public final static String JWT_PRIVATE_KEY ="orgfinchelykey";
    public final static String RENEWAL_TIME =  "renewalTime";
    public final static String TOKEN = "token";
    
    /**
     * 登录token有效期
     */
    public final static long TOKEN_EXPIRE = 24 * 3600 *1000; //1天有效期。
    
    /**
     * 列表页面记录数
     */
    public final static int PAGE_SIZE = 10;
    
    
    /*
     * 正常响应
     */
    public final static int RESP_SUCCESS = 200;
    /**
     * 异常响应
     */
    public final static int RESP_ERROR = 201;
    /**
     * 未登录或登录凭证过期
     */
    public final static int RESP_NOTLOGIN = 401;
    /**
     * 错误的数据请求
     */
    public final static int RESP_BADREQ = 400;
    /**
     * 没有权限
     */
    public final static int RESP_NOPERMIT = 403;
    
}
