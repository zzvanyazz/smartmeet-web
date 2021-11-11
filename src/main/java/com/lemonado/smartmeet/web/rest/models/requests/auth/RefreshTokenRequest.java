package com.lemonado.smartmeet.web.rest.models.requests.auth;

import javax.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank
        String token) {

}