package com.spring_kajarta_frontstage.security;

import java.util.Arrays;
import java.util.List;

/**
 * security常量
 */

public class SecurityConst {

    /**
     * 放行url,放行的url不再被安全框架攔截
     */
    public static List<String> allows;

    static {
        allows = Arrays.asList(
                "/**",
                "/ping",
                "/login",
                "/code/image",
                "/google/getToken",
                "/google/checkCode",
                "/google/checkCode",
                "/etfConfig",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v2/**");
    }

}
