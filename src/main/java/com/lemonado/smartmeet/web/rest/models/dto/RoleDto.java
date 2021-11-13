package com.lemonado.smartmeet.web.rest.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;


@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class RoleDto {

    private long id;
    private String name;
    private String description;
    private OffsetDateTime createTimestamp;
    private OffsetDateTime updateTimestamp;

}
