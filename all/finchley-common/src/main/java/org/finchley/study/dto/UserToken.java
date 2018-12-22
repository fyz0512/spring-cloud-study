package org.finchley.study.dto;

import java.io.Serializable;

/**
 * 用户登录凭据，用户登录系统后获得的登录凭据，保存部分的用户信息。
 * @author John Fang
 *
 */
public class UserToken implements Serializable{
    private static final long serialVersionUID = 1L;

    public UserToken(String username, String userId, String name) {
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户登录名
     */
    private String username;
    /**
     * 用户真实姓名
     */
    private String name;
    /**
     * 到期时间（相对时间）毫秒数，-1为永不过期。
     */
    private Long expire = -1L;
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	@Override
    public String toString() {
        return "UserToken{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", expire='" + expire + '\'' +
                '}';
    }
}
