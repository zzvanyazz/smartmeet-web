package com.lemonado.smartmeet.web.rest.models.requests;

import javax.validation.constraints.NotNull;

public record AssignUserRoleRequest(
        @NotNull
        Long roleId) {

}
