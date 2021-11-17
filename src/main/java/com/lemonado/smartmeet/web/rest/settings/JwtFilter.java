package com.lemonado.smartmeet.web.rest.settings;

import com.lemonado.smartmeet.web.rest.models.auth.Principal;
import com.lemonado.smartmeet.web.rest.models.auth.exception.InvalidTokenException;
import com.lemonado.smartmeet.web.rest.services.FirebaseAuthService;
import com.lemonado.smartmeet.web.rest.services.VerificationAuthorizationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public final class JwtFilter extends GenericFilterBean {
    private final static String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final static String BEARER_AUTHORIZATION_PREFIX = "Bearer ";

    @Autowired
    private VerificationAuthorizationService authService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) {

        authenticate((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private void authenticate(HttpServletRequest request) {
        extractToken(request)
                .flatMap(this::toPrincipal)
                .ifPresent(principal -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                            principal,
                            "",
                            principal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
    }

    private Optional<Principal> toPrincipal(String token) {
        var principal = authService.authenticate(token);
        if (principal.isPresent())
            return principal;
        return firebaseAuthService.authenticate(token);
    }


    private Optional<String> extractToken(HttpServletRequest request) {
        var token = extractTokenFromHeaders(request).orElse("").trim();

        return token.length() > 0
                ? Optional.of(token)
                : Optional.empty();
    }

    private Optional<String> extractTokenFromHeaders(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith(BEARER_AUTHORIZATION_PREFIX)) {
            String token = authHeader.substring(BEARER_AUTHORIZATION_PREFIX.length());

            return Optional.of(token);
        }

        return Optional.empty();
    }

}
