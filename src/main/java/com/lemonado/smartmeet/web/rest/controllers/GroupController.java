package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.core.services.groups.GroupService;
import com.lemonado.smartmeet.core.services.users.UserService;
import com.lemonado.smartmeet.web.rest.models.requests.groups.CreateGroupRequest;
import com.lemonado.smartmeet.web.rest.models.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/groups")
public class GroupController {


    @Autowired
    private GroupService groupService;

    @Autowired
    ;

    @PostMapping
    public Response<?> createGroup(@RequestBody CreateGroupRequest createGroupRequest) {

    }

}
