package com.spring_kajarta_frontstage.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * Security 安全校驗實體-用於封轉登入使用者的身份資訊和權限訊息
 * 實質上是對 org.springframework.security.core.userdetails.UserDetails 接口的實現
 * org.springframework.security.core.userdetails.User 提供了構造方法，便於我們業務使用者實體和Security 校驗身分的實體分離
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class SecurityUser extends User {

    private static final long serialVersionUID = 1L;

    private Long customerId;
    // google登入也可以加
    // 如果後台要搞權限的話menu設計
//    private Collection<? extends SecurityUserMenu> menus;

    public SecurityUser(Long customerId, String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.customerId = customerId;
    }

}
