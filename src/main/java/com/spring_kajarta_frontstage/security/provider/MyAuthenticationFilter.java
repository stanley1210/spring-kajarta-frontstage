package com.spring_kajarta_frontstage.security.provider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * <pre>
 * override UsernamePasswordAuthenticationFilter的attemptAuthentication,
 * obtainUsername,obtainPassword方法(完善邏輯)
 * </pre>
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // post請求
    private boolean postOnly = true;

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


    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}

