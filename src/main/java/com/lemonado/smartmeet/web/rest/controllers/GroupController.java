package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.core.data.exceptions.CanNotCreateUserException;
import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.data.exceptions.group.InvalidGroupException;
import com.lemonado.smartmeet.core.data.exceptions.group.UnsupportedGroupException;
import com.lemonado.smartmeet.core.services.impl.groups.GroupServiceImpl;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.GroupMapper;
import com.lemonado.smartmeet.web.rest.models.requests.groups.CreateGroupRequest;
import com.lemonado.smartmeet.web.rest.models.requests.groups.UpdateGroupNameRequest;
import com.lemonado.smartmeet.web.rest.services.CurrentUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController("/v1/groups")
public class GroupController {


    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation("Create new group")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest groupRequest)
            throws UserNotFoundException, CanNotCreateUserException {
        var user = currentUserService.getId();
        var groupName = groupRequest.getName();
        var groupModel = groupService.createGroup(user, groupName);
        var groupDto = GroupMapper.toDto(groupModel);
        return ResponseEntity.ok(groupDto);
    }

    @ApiOperation("Update group name")
    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroupName(@PathVariable long groupId,
                                             @RequestBody UpdateGroupNameRequest groupRequest)
            throws InvalidGroupException, UnsupportedGroupException, UserNotFoundException {
        var userId = currentUserService.getId();
        groupService.assertCreator(groupId, userId);

        var groupName = groupRequest.getName();
        var groupModel = groupService.updateGroupName(groupId, groupName);
        var groupDto = GroupMapper.toDto(groupModel);
        return ResponseEntity.ok(groupDto);
    }

    @ApiOperation("Update group code")
    @PostMapping("/{groupId}")
    public ResponseEntity<?> updateGroupCode(@PathVariable long groupId)
            throws InvalidGroupException, UnsupportedGroupException, UserNotFoundException, CanNotCreateUserException {
        var userId = currentUserService.getId();
        groupService.assertCreator(groupId, userId);

        var groupModel = groupService.updateGroupCode(groupId);
        var groupDto = GroupMapper.toDto(groupModel);
        return ResponseEntity.ok(groupDto);
    }


}
