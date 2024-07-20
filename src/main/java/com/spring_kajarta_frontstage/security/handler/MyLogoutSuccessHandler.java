package com.spring_kajarta_frontstage.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajarta.demo.domian.ResultNew;
import com.kajarta.demo.utils.ResultUtilNew;
import com.spring_kajarta_frontstage.security.provider.UsernamePasswordToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        // 用戶名
        String principal = null;
        // 登入信息
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;
        if (token != null) {
            principal = (String) token.getPrincipal();
        }

        // 清除身份認證信息
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();

        request.getSession().invalidate();

//        CookieUtils.deleteCookie("AUTH-TOKEN", response);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        ResultNew<Object> resultNew = new ResultNew<>();
        resultNew.setData("ok");
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtilNew.success(resultNew)));

        log.info("[{}]退出登入", principal);
    }

}
