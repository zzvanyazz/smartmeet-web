package com.lemonado.smartmeet.web.rest.services;

import com.lemonado.smartmeet.core.data.exceptions.LoginFailedException;
import com.lemonado.smartmeet.core.services.impl.users.UserServiceImpl;
import com.lemonado.smartmeet.web.rest.models.auth.InvalidTokenException;
import com.lemonado.smartmeet.web.rest.models.auth.TokenBlockedException;
import com.lemonado.smartmeet.web.rest.models.requests.auth.AuthRequest;
import com.lemonado.smartmeet.web.rest.models.requests.auth.RefreshTokenRequest;
import com.lemonado.smartmeet.web.rest.models.responses.AuthResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;


@Service
public class AuthorizeService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TokenService tokenService;


    public AuthResponseData authenticate(AuthRequest request)
            throws LoginFailedException {
        var user = userService.login(request.getUsername(), request.getPassword());

        return tokenService.createAuthData(user);
    }

    public AuthResponseData refreshToken(RefreshTokenRequest request)
            throws InvalidTokenException, AuthenticationFailedException, TokenBlockedException {
        var token = tokenService.parseRefreshToken(request.token());
        var user = userService.findActiveUser(token.userId());

        if (tokenService.isTokenBlocked(user, token)) {
            throw new TokenBlockedException();
        }

        return tokenService.createAuthData(user);
    }

}



