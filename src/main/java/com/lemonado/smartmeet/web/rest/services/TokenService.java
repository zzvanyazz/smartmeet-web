package com.lemonado.smartmeet.web.rest.services;

import com.lemonado.smartmeet.core.data.models.users.UserModel;
import com.lemonado.smartmeet.web.rest.models.auth.InvalidTokenException;
import com.lemonado.smartmeet.web.rest.models.auth.Token;
import com.lemonado.smartmeet.web.rest.models.responses.AuthResponseData;
import com.lemonado.smartmeet.web.rest.settings.TokenOptions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenOptions tokenOptions;


    public Token parseRefreshToken(String jwtToken) throws InvalidTokenException {
        try {
            return parseToken(jwtToken, tokenOptions.getRefreshTokenSigningKey());
        } catch (Exception ex) {
            log.error("Failed to parse refresh token.", ex);

            throw new InvalidTokenException();
        }
    }

    public Token parseAccessToken(String jwtToken) throws InvalidTokenException {
        try {
            return parseToken(jwtToken, tokenOptions.getAccessTokenSigningKey());
        } catch (Exception ex) {
            log.error("Failed to parse access token.", ex);

            throw new InvalidTokenException();
        }
    }

    private Token parseToken(String jwtToken, byte[] signingKey) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .setAllowedClockSkewSeconds(tokenOptions.getAllowedClockSkewSeconds())
                .requireIssuer(tokenOptions.getIssuer())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        var id = Long.parseLong(claims.getSubject());
        var issuedAt = claims.getIssuedAt().toInstant().atOffset(ZoneOffset.UTC);

        return new Token(id, issuedAt);
    }

    public boolean isTokenBlocked(UserModel user, Token token) {
        return user.validTokenTimestamp() != null &&
                token != null &&
                user.validTokenTimestamp()
                        .minusSeconds(tokenOptions.getAllowedClockSkewSeconds())
                        .isAfter(token.issuedAt());
    }

    public AuthResponseData createAuthData(UserModel user) {
        try {
            var accessTokenExpiration = calculateExpirationDate(tokenOptions.getAccessTokenExpirationPeriod());
            var accessToken = createAccessToken(user, accessTokenExpiration);
            var refreshTokenExpiration = calculateExpirationDate(tokenOptions.getRefreshTokenExpirationPeriod());
            var refreshToken = createRefreshToken(user, refreshTokenExpiration);

            return AuthResponseData.builder()
                    .withUserId(user.id())
                    .withAccessToken(accessToken)
                    .withRefreshToken(refreshToken)
                    .withExpiration(accessTokenExpiration)
                    .build();
        } catch (Exception ex) {
            log.error("Failed to create token.", ex);
            throw ex;
        }
    }

    private String createAccessToken(UserModel user, OffsetDateTime expiration) {
        return createToken(user, expiration, tokenOptions.getAccessTokenSigningKey());
    }

    private String createRefreshToken(UserModel user, OffsetDateTime expiration) {
        return createToken(user, expiration, tokenOptions.getRefreshTokenSigningKey());
    }

    private String createToken(UserModel user, OffsetDateTime expiration, byte[] bytesKey) {
        var key = Keys.hmacShaKeyFor(bytesKey);

        return Jwts.builder()
                .setSubject(String.valueOf(user.id()))
                .setExpiration(Date.from(expiration.toInstant()))
                .setIssuer(tokenOptions.getIssuer())
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    private OffsetDateTime calculateExpirationDate(Period period) {
        return OffsetDateTime.now(ZoneOffset.UTC).plus(period);
    }

}
