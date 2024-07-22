package com.spring_kajarta_frontstage.security.provider;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 登入token
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class UsernamePasswordToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    // customer id
    private Long customerId;
    // domain
    private String domain;
    // 登入來源，ip
    private String source;

    public UsernamePasswordToken(Object principal, Object credentials, String domain, String source) {
        super(principal, credentials);
        this.domain = domain;
        this.source = source;
    }

    public UsernamePasswordToken(Object principal, Object credentials,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
