package com.examination.api.model.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class LoginUser extends AbstractAuthenticationToken {

    private final String principal;
    private final Object credentials;

    public LoginUser(String principal, String credentials, List<GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public String getPrincipal() { return principal; }
    @Override
    public Object getCredentials() { return credentials; }
}
