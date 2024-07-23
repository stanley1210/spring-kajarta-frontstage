package com.spring_kajarta_frontstage.controller;

import com.spring_kajarta_frontstage.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {

    /**
     * 獲取用戶id
     */
    protected Long getAdminId() {
        return SecurityUtils.getAdminId();
    }

    /**
     * 獲取用戶名
     */
    protected String getAdmin() {
        return SecurityUtils.getPrincipal();
    }
}
