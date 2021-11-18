package com.lemonado.smartmeet.web.rest.models.requests.users;

import javax.validation.constraints.NotNull;

public record AssignUserRoleRequest(
        @NotNull
        Long roleId) {

}
