package com.spring_kajarta_frontstage.security.provider;

import com.kajarta.demo.utils.JwtTokenUtil;
//import com.spring_kajarta_frontstage.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * <pre>
 * override UsernamePasswordAuthenticationFilter的attemptAuthentication,
 * obtainUsername,obtainPassword方法(完善邏輯)
 * </pre>
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // post請求
    private boolean postOnly = true;

//    private final JwtTokenUtil jwtTokenUtil;
//    private final TokenService tokenService;
//
//    public MyAuthenticationFilter(JwtTokenUtil jwtTokenUtil, TokenService tokenService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.tokenService = tokenService;
//    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 判斷是不是post請求
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取Username和Password
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        // UsernamePasswordToken實現Authentication校驗
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, "localhost", "127.0.0.1");

        // 允許子類設定詳細屬性
        setDetails(request, token);

        // 運行UserDetailsService的loadUserByUsername 再次封裝Authentication
        return this.getAuthenticationManager().authenticate(token);
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
//        UsernamePasswordToken tokenAuth = (UsernamePasswordToken) authentication;
//        String username = tokenAuth.getPrincipal().toString();
//        String token = jwtTokenUtil.generateToken(username);
//        tokenService.storeToken(username, token);
//        response.setHeader("Authorization", "Bearer：" + token);
//    }

    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}

