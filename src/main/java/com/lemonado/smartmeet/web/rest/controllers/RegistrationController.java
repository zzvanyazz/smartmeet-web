package com.lemonado.smartmeet.web.rest.controllers;


import com.lemonado.smartmeet.core.data.exceptions.*;
import com.lemonado.smartmeet.core.data.models.registration.RegistrationModel;
import com.lemonado.smartmeet.core.services.impl.users.RegistrationServiceImpl;
import com.lemonado.smartmeet.web.rest.models.auth.AuthorityRole;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.UserMapper;
import com.lemonado.smartmeet.web.rest.models.requests.users.StartUsersRegistration;
import com.lemonado.smartmeet.web.rest.models.responses.ResponseEntityFactory;
import com.lemonado.smartmeet.web.rest.models.responses.mappings.RegistrationMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @ApiOperation("Start userId registration.")
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> startUsersRegistration(@RequestBody StartUsersRegistration registration)
            throws RoleNotFoundException, ActionOnAdminRoleException {
        var roleName = registration.roleName();
        var userEmails = registration.usersEmails();
        try {
            registrationService.startUserRegistration(roleName, userEmails);
            return ResponseEntityFactory.createOk();
        } catch (RegistrationProblemsException e) {
            var response = RegistrationMapper.toResponse(e);
            return ResponseEntity.status(207).body(response);
        }
    }

    @ApiOperation("Register.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegistrationModel registration)
            throws RegistrationCodeNotFoundException, UserAlreadyExistsException, CanNotCreateUserException {
        var userModel = registrationService.register(registration);
        var dto = UserMapper.toDto(userModel);
        return ResponseEntityFactory.createCreated(dto);
    }
}
