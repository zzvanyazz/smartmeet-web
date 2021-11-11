package com.lemonado.smartmeet.web.rest.models.dto;

import java.time.OffsetDateTime;


public record RoleDto(
        long id,
        String name,
        String description,
        OffsetDateTime createTimestamp,
        OffsetDateTime updateTimestamp) {

}
