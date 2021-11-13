package com.lemonado.smartmeet.web.rest.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder(setterPrefix = "with")
public class UserDto {

    private long id;
    private String username;
    private String email;
    private OffsetDateTime deleteTimestamp;
    private OffsetDateTime validTokenTimestamp;

}
