package com.spring_kajarta_frontstage.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerDetailsService {

    /**
     * 加載用戶詳情
     *
     * @param username 登入用戶名
     * @return 用戶詳情
     */
    UserDetails loadUserByUsername(String username);

}
