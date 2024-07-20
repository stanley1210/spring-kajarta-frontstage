package com.spring_kajarta_frontstage.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.utils.ResultUtilNew;
import com.spring_kajarta_frontstage.security.exception.UserLoginException;
import com.spring_kajarta_frontstage.security.provider.UsernamePasswordToken;
import com.spring_kajarta_frontstage.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MyLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginUserHandler loginUserHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        if (!(authentication instanceof UsernamePasswordToken)) {
            throw new UserLoginException("登入token類型錯誤");
        }

        // Token
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;

        // 正常是要設計這些欄位去更新登入信息
        Customer customer = customerService.findById(Integer.parseInt(token.getCustomerId().toString()));
//        if (admin != null) {
//            Admin mod = new Admin();
//            mod.setId(admin.getId());
//            mod.setLoginIp(token.getSource());
//            mod.setLoginTime(new Date());
//            mod.setLoginSucceed(admin.getLoginSucceed() + 1);
//            mod.setLoginError(0);
//            try {
//                adminService.update(mod);
//            } catch (Exception e) {
//                log.error("更新用戶信息錯誤,{}", mod, e);
//            }
//        }

        JSONObject data = new JSONObject();
        data.put("username", token.getPrincipal());
//        data.put("google", ((SecurityUser) token.getDetails()).isGoogleEnable());
//        data.put("menus", token.getMenus());
        data.put("auths", token.getAuthorities());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtilNew.success(data, "登入成功")));

        loginUserHandler.addLoginUserSessionId(Long.parseLong(customer.getId().toString()), request.getSession().getId());

        log.info("[{}]登入成功 - {}", token.getPrincipal(), request.getSession().getId());
    }

}
