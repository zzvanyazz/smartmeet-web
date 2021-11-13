package com.lemonado.smartmeet.web.rest.models.requests.groups;

import lombok.Data;

@Data
public class UpdateGroupRequest {

    long id;
    String name;
    String code;

}
