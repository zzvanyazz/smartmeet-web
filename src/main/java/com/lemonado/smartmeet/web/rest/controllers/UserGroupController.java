package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.data.exceptions.group.InvalidGroupException;
import com.lemonado.smartmeet.core.data.exceptions.group.UnsupportedGroupException;
import com.lemonado.smartmeet.core.services.impl.groups.GroupServiceImpl;
import com.lemonado.smartmeet.core.services.impl.groups.GroupUsersServiceImpl;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.GroupMapper;
import com.lemonado.smartmeet.web.rest.services.CurrentUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController("/v1/groups")
public class UserGroupController {

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private GroupUsersServiceImpl groupUsersService;

    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation("Delete users")
    @DeleteMapping("/{groupId}/users")
    public ResponseEntity<?> removeUsers(@PathVariable long groupId,
                                         @RequestParam Set<Long> userIds)
            throws UserNotFoundException, InvalidGroupException, UnsupportedGroupException {

        var userId = currentUserService.getId();
        groupService.assertCreator(groupId, userId);
        groupUsersService.removeUsers(groupId, userIds);
        var group = groupService.getGroup(userId);
        var groupDto = GroupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @ApiOperation("Delete users")
    @PostMapping("/{groupId}/users")
    public ResponseEntity<?> renewUsers(@PathVariable long groupId,
                                        @RequestParam Set<Long> userIds)
            throws UserNotFoundException, InvalidGroupException, UnsupportedGroupException {

        var userId = currentUserService.getId();
        groupService.assertCreator(groupId, userId);
        groupUsersService.renewUsers(groupId, userIds);
        var group = groupService.getGroup(userId);
        var groupDto = GroupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @ApiOperation("Register by code")
    @PostMapping("/users")
    public ResponseEntity<?> registerByCode(@RequestParam String code)
            throws UserNotFoundException, InvalidGroupException {
        var userId = currentUserService.getId();
        var group = groupUsersService.registerUserToGroup(userId, code);
        return ResponseEntity.ok(group);
    }

}
