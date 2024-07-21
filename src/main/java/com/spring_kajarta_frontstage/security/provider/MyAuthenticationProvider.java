package com.spring_kajarta_frontstage.security.provider;

import com.spring_kajarta_frontstage.security.SecurityUser;
import com.spring_kajarta_frontstage.security.exception.UserLoginException;
import com.spring_kajarta_frontstage.security.service.impl.CustomerDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登入校驗邏輯
 */
@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    private CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    public void setUserDetailsService(CustomerDetailsServiceImpl customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof UsernamePasswordToken)) {
            throw new UserLoginException("登录令牌类型错误");
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;
        log.info("==>2 Token: " + token);

        String username = token.getPrincipal().toString();
        String password = token.getCredentials().toString();
//        String domain = token.getDomain();
//        String source = token.getSource();

        // 如果userDetails不為空重新建置UsernamePasswordToken（已認證）
        SecurityUser userDetails;

        // 调用自定义的userDetailsService认证
        userDetails = customerDetailsService.loadUserByUsername(username);

        log.info("[{}]登入中....", username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("登入失敗:" + username);
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new UserLoginException("登入中..."); // 帳號已凍結
        } else if (!userDetails.isEnabled()) {
            throw new UserLoginException("登入中..."); // 帳號已註銷
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new UserLoginException("用戶密碼已過期");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new UserLoginException("用戶帳戶已過期");
        } else {
            // 加密
//            if (!PasswordUtil.matchesA(password, userDetails.getPassword())) {
//                throw new UserLoginException("使用者名稱或密碼錯誤");
//            }
            // 不加密
            if (!password.equals(userDetails.getPassword())) {
                throw new UserLoginException("使用者名稱或密碼錯誤");
            }
        }

        // google驗證
//        if (userDetails.isGoogleEnable()) {
//            if (StringUtils.isBlank(googleToken)) {
//                throw new UserLoginException("Google驗證碼不能為空");
//            }
//            // 验证
//            GoogleAuthenticator ga = new GoogleAuthenticator();
//            ga.setWindowSize(5);
//            boolean check = ga.checkCode(userDetails.getGoogleSecret(), googleToken);
//            if (!check) {
//                throw new UserLoginException("Google驗證碼錯誤或已過期");
//            }
//        }

        // 如果userDetails不為空，重新建置UsernamePasswordToken（已認證）
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userDetails.getUsername(),
                                                                                "[protected]",
                                                                                userDetails.getAuthorities());
        usernamePasswordToken.setDetails(userDetails);
        usernamePasswordToken.setCustomerId(userDetails.getCustomerId());
        return usernamePasswordToken;
    }

    /**
     * 只有Authentication為UsernamePasswordToken使用此Provider認證
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordToken.class.isAssignableFrom(authentication);
    }

}
