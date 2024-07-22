package com.spring_kajarta_frontstage.security.handler.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserSession implements Serializable {

    private Long adminId;
    private String sessionId;
    private long loginTime;

    public LoginUserSession(Long adminId, String sessionId, long loginTime) {
        this.adminId = adminId;
        this.sessionId = sessionId;
        this.loginTime = loginTime;
    }
}
