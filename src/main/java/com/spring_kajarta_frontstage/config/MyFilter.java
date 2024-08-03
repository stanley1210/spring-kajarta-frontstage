package com.spring_kajarta_frontstage.config;

import com.alibaba.fastjson.JSONObject;
import com.kajarta.demo.utils.ResultUtilNew;
import com.spring_kajarta_frontstage.security.SecurityUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 自定義過濾器
 */
@Slf4j
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 排除的uri(security中也要放開此路徑權限)
        String uri = req.getServletPath();
        if (excludedUri(uri)) {
            log.info("排除的uri = {}", uri);
            chain.doFilter(request, response);
            return;
        }

        // 判断是否登录
        if (SecurityUtils.getAdminId() == null) {
            res.setStatus(HttpStatus.OK.value());
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(JSONObject.toJSONString(ResultUtilNew.error(401, "登入過期")));
            return;
        }

        // String customerId = ((HttpServletRequest) request).getHeader("token");
        // if (customerId != null) {
        // String token = getTokenFromRedis(Long.parseLong(customerId));
        // if (token != null) {
        // // Set the token in SecurityContext or other context
        // SecurityContextHolder.getContext().setAuthentication(new
        // UsernamePasswordAuthenticationToken(token, null, new ArrayList<>()));
        // }
        // }

        chain.doFilter(request, response);
    }

    /**
     * 是否排除的url
     */
    private boolean excludedUri(String uri) {
        List<String> allows = Arrays.asList(
                "/**",
                "/ping",
                "/login",
                "/code/image",
                "/etfConfig",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/v2/**");
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String exclude : allows) {
            if (antPathMatcher.matchStart(exclude, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }

}
