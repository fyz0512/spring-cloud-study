package org.finchley.study.dto;

import javax.validation.constraints.NotNull;

/**
 * 登录数据容器
 * @author John Fang
 *
 */
public class LoginDTO {
    @NotNull
    private String username;
    @NotNull
    private String pwd;
    
    private String extcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String password) {
        this.pwd = password;
    }

	public String getExtcode() {
		return extcode;
	}

	public void setExtcode(String extcode) {
		this.extcode = extcode;
	}
}
