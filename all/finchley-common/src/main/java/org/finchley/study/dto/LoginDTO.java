package org.finchley.study.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 登录数据对象
 * @author John Fang
 *
 */
public class LoginDTO {
    @NotNull(message="用户账号为空。")
    private String username;
    @NotNull
    @Length(min=32,max=32,message="密码输入错误。")
    private String password;
    
    private String extcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getExtcode() {
		return extcode;
	}

	public void setExtcode(String extcode) {
		this.extcode = extcode;
	}
}
