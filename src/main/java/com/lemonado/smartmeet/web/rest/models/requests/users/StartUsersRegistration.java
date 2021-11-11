package com.lemonado.smartmeet.web.rest.models.requests.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record StartUsersRegistration(
        @NotBlank
        String roleName,
        @NotEmpty
        List<String> usersEmails) {

}
