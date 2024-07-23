package com.spring_kajarta_frontstage.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.utils.ResultUtilNew;
import com.spring_kajarta_frontstage.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // 失敗原因
        String reason = exception.getMessage();
        // 登入帳號
        String username = request.getParameter("username");
        // 登入密碼
        String password = request.getParameter("password");

        if (StringUtils.isBlank(username) || exception instanceof UsernameNotFoundException) {
            log.info("非用戶[{}]嘗試登入: password={}", username, password);
        } else {
            // 正常是要設計這些欄位去更新登入信息
            Customer customer = customerService.getByUsername(username);
//            if (admin != null && AdminStatusEnum.NORMAL.getCode() == admin.getStatus()) {
//                Admin mod = new Admin();
//                mod.setId(admin.getId());
//                mod.setLoginError(admin.getLoginError() + 1);
//                mod.setLoginFail(admin.getLoginFail() + 1);
//                mod.setLoginTime(new Date());
//                mod.setLoginIp(IpTools.getIpAddr(request));
//                if (mod.getLoginError() >= 5) {
//                    mod.setStatus(AdminStatusEnum.FROZEN.getCode());
//                }

                // 更新用戶信息
//                try {
//                    adminService.update(mod);
//                } catch (Exception e) {
//                    log.error("[{}]登入失敗，更新用戶信息出錯", username, e);
//                }

                log.info("[{}]登录失败 - {}", customer, reason);
            }
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtilNew.error(reason)));
    }
}
