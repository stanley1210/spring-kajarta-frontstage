package com.spring_kajarta_frontstage.security;

import com.spring_kajarta_frontstage.security.provider.UsernamePasswordToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


/**
 * 使用者安全工具類
 *
 * <p>此類別主要結合SpringSecurity框架，提供：
 * <ul>
 * <li>檢查是否含有權限</li>
 * <li>取得登入名稱</li>
 * <li>取得登入令牌</li>
 * <li>取得登入來源</li>
 * <li>取得登入類型</li>
 * </ul>
 * </p>
 */
public class SecurityUtils {

    /**
     * 用戶是否已經認證
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordToken) {
            return authentication.isAuthenticated();
        }
        return false;
    }

    /**
     * 是否有權限
     *
     * @param auth 權限名稱
     */
    public static boolean hasAuth(String auth) {
        if (!StringUtils.hasText(auth)) {
            return false;
        }

        UsernamePasswordToken token = getToken();
        if (token != null && !CollectionUtils.isEmpty(token.getAuthorities())) {
            for (GrantedAuthority granted : token.getAuthorities()) {
                if (auth.equals(granted.getAuthority())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 取得登入使用者名稱
     */
    public static String getPrincipal() {
        UsernamePasswordToken token = getToken();
        return token != null ? (String) token.getPrincipal() : null;
    }

    /**
     * 獲取Long 型別 token
     */
    public static Long getAdminId() {
        UsernamePasswordToken token = getToken();
        return token != null ? token.getCustomerId() : null;
    }


    /**
     * 獲取菜單
     */
//    public static Collection<? extends SecurityUserMenu> getMenus() {
//        UsernamePasswordToken token = getToken();
//        return token != null ? token.getMenus() : null;
//    }

    /**
     * 取得登入Token對象
     */
    public static UsernamePasswordToken getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordToken) {
            return (UsernamePasswordToken) authentication;
        }
        return null;
    }

}
