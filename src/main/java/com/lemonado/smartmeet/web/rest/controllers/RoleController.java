package com.lemonado.smartmeet.web.rest.controllers;


import com.lemonado.smartmeet.core.services.impl.users.RoleServiceImpl;
import com.lemonado.smartmeet.web.rest.models.dto.mappings.RoleMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {


    @Autowired
    private RoleServiceImpl roleService;


    @ApiOperation("List roles.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listRoles() {
        var roles = roleService.getAll();
        var roleDtos = roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(roleDtos);
    }

}
