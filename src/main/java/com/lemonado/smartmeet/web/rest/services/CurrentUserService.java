package com.lemonado.smartmeet.web.rest.services;

import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.data.models.users.UserModel;
import com.lemonado.smartmeet.core.services.impl.users.UserServiceImpl;
import com.lemonado.smartmeet.web.rest.models.auth.AuthorityRole;
import com.lemonado.smartmeet.web.rest.models.auth.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public final class CurrentUserService {

    @Autowired
    private UserServiceImpl userService;

    public UserModel getUser() throws UserNotFoundException {
        return userService.getUser(getId());
    }

    public long getId() {
        return getPrincipal().getId();
    }

    public boolean hasAuthority(String... authorities) {
        if (authorities == null) {
            return false;
        }

        return getPrincipal()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(x -> Arrays.asList(authorities).contains(x));
    }

    public boolean isNotGranted() {
        if (!(getPrincipalObject() instanceof Principal))
            return true;

        return !hasAuthority(AuthorityRole.ADMIN, AuthorityRole.MANAGER);
    }

    public Principal getPrincipal() {
        return (Principal) getPrincipalObject();
    }

    private Object getPrincipalObject() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
