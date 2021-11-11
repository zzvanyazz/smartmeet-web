package com.lemonado.smartmeet.web.rest.models.requests.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record AuthRequest(
        @NotBlank
        @Size(max = 128)
        String username,
        @NotEmpty
        String password) {

}
