package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.core.data.exceptions.ActionOnAdminRoleException;
import com.lemonado.smartmeet.core.data.exceptions.RoleNotFoundException;
import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.services.impl.users.UserRolesServiceImpl;
import com.lemonado.smartmeet.core.services.impl.users.UserServiceImpl;
import com.lemonado.smartmeet.web.rest.models.auth.AuthorityRole;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.RoleMapper;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.UserMapper;
import com.lemonado.smartmeet.web.rest.models.requests.AssignUserRoleRequest;
import com.lemonado.smartmeet.web.rest.models.responses.ResponseEntityFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRolesServiceImpl userRolesService;


    @ApiOperation("Get user info.")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable long userId) throws Exception {
        var userModel = userService.getUser(userId);
        var userDto = UserMapper.toDto(userModel);
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation("Get users info.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        var userModels = userService.getUsers();
        var userDto = userModels.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation("Get user roles")
    @GetMapping(value = "/{userId}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoles(@PathVariable long userId) throws UserNotFoundException, RoleNotFoundException {
        var roles = userRolesService.getRoles(userId);
        var rolesDtos = roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
        return ResponseEntityFactory.createOk(rolesDtos);
    }

    @ApiOperation("Assign user role")
    @PostMapping(value = "/{userId}/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> assignRole(
            @PathVariable long userId,
            @RequestBody @Validated AssignUserRoleRequest request,
            BindingResult bindingResult)
            throws UserNotFoundException, RoleNotFoundException, ActionOnAdminRoleException {
        if (bindingResult.hasErrors()) {
            return ResponseEntityFactory.createBadRequest(bindingResult);
        }
        userRolesService.assignUserRole(userId, request.roleId());
        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Remove assign user role")
    @PostMapping(value = "/{userId}/remove-assign", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> removeAssignRole(
            @PathVariable long userId,
            @RequestBody @Validated AssignUserRoleRequest request,
            BindingResult bindingResult)
            throws UserNotFoundException, RoleNotFoundException, ActionOnAdminRoleException {
        if (bindingResult.hasErrors()) {
            return ResponseEntityFactory.createBadRequest(bindingResult);
        }
        userRolesService.removeAssignUserRole(userId, request.roleId());
        return ResponseEntityFactory.createOk();
    }

}
