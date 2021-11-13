package com.lemonado.smartmeet.web.rest.models.requests.groups;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateGroupRequest {

    @NotBlank
    private String name;

}
