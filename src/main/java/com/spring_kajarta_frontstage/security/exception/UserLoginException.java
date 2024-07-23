package com.spring_kajarta_frontstage.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用戶異常登入
 */
public class UserLoginException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    private String message;

    public UserLoginException(String msg, Throwable t) {
        super(msg, t);
        this.message = msg;
    }

    public UserLoginException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
