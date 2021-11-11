package com.lemonado.smartmeet.web.rest.models.responses;

import java.time.OffsetDateTime;

public record AuthResponseData(
        long userId,
        String accessToken,
        String refreshToken,
        OffsetDateTime expiration) {

    public static AuthResponseDataBuilder builder() {
        return new AuthResponseDataBuilder();
    }

    public static final class AuthResponseDataBuilder {
        private long userId;
        private String accessToken;
        private String refreshToken;
        private OffsetDateTime expiration;

        private AuthResponseDataBuilder() {
        }

        public static AuthResponseDataBuilder anAuthResponseData() {
            return new AuthResponseDataBuilder();
        }

        public AuthResponseDataBuilder withUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public AuthResponseDataBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AuthResponseDataBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AuthResponseDataBuilder withExpiration(OffsetDateTime expiration) {
            this.expiration = expiration;
            return this;
        }

        public AuthResponseData build() {
            return new AuthResponseData(userId, accessToken, refreshToken, expiration);
        }
    }
}
