package com.lemonado.smartmeet.web.rest.models.requests.auth;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class AuthRequest {
    @NotBlank
    @Size(max = 128)
    private String username;
    @NotEmpty
    private String password;

}
