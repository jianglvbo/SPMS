package com.jlb.model.util;


import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class TypeToken extends UsernamePasswordToken {
    private Boolean type;
    public TypeToken() {
        super();
    }

    public TypeToken(String username, String password, Boolean type, boolean rememberMe,String host) {
        super(username, password, rememberMe, host);
        this.type = type;
    }
    /**免密登录*/
    public TypeToken(String username) {
        super(username, "", false, null);
        this.type = true;
    }
    /**账号密码登录*/
    public TypeToken(String username, String password) {
        super(username, password, false, null);
        this.type = false;
    }

}
