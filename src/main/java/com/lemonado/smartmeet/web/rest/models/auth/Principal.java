package com.lemonado.smartmeet.web.rest.models.auth;

import com.lemonado.smartmeet.core.data.models.users.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class Principal implements UserDetails {
    private long id;
    private String username;
    private List<GrantedAuthority> authorities;

    public static Principal fromUser(UserModel entity) {
        var principal = new Principal();
        principal.id = entity.id();
        principal.username = entity.username();

        return principal;
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
