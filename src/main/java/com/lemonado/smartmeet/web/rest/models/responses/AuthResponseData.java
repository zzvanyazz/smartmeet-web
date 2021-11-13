package com.lemonado.smartmeet.web.rest.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(setterPrefix = "with")
public class AuthResponseData {
    private long userId;
    private String accessToken;
    private String refreshToken;
    private OffsetDateTime expiration;

}
