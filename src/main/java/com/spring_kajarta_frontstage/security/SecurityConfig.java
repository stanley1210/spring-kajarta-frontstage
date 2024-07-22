package com.spring_kajarta_frontstage.security;

import com.kajarta.demo.utils.JwtTokenUtil;
import com.spring_kajarta_frontstage.security.endpoint.UnauthorizedEntryPoint;
import com.spring_kajarta_frontstage.security.handler.MyAccessDeniedHandler;
import com.spring_kajarta_frontstage.security.handler.MyLoginFailureHandler;
import com.spring_kajarta_frontstage.security.handler.MyLoginSuccessHandler;
import com.spring_kajarta_frontstage.security.handler.MyLogoutSuccessHandler;
import com.spring_kajarta_frontstage.security.provider.MyAuthenticationFilter;
import com.spring_kajarta_frontstage.security.provider.MyAuthenticationProvider;
//import com.spring_kajarta_frontstage.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private MyAuthenticationProvider authProvider;

    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;

    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private SessionRegistry sessionRegistry;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    private TokenService tokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 設定明確儲存的需求
                .securityContext(securityContext -> securityContext
                        .requireExplicitSave(false)
                )
                // 解決同源問題 X-Frame-Options to allow any request from same domain
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                // 關閉csrf
                .csrf(csrf -> csrf.disable())
                // session管理策略
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionAuthenticationStrategy(new SessionFixationProtectionStrategy())
                )
                // 登入認證過濾器
                .addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(SecurityConst.allows.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/login")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(myLogoutSuccessHandler)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(myAccessDeniedHandler)
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //解決靜態資源被攔截的問題
        return (web) -> web
                .ignoring()
                .requestMatchers(HttpMethod.GET,
                        "/**",
                        "/favicon.ico",
                        "/*.html",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/*/*.map",
                        "/*/*.png",
                        "/*/*.jpg");
    }


    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    /**
     * 自定義登入過濾器
     */
    @Bean
    public UsernamePasswordAuthenticationFilter myAuthenticationFilter() {
        // token再用
//        MyAuthenticationFilter filter = new MyAuthenticationFilter(jwtTokenUtil, tokenService);
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        filter.setPostOnly(true);
        filter.setAuthenticationManager(authenticationManager());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        filter.setAuthenticationSuccessHandler(myLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(myLoginFailureHandler);
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authProvider));
    }

    // Implement the AuthenticationManager as a bean
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider)
                .build();
    }
}
