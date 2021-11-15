package com.lemonado.smartmeet.web.rest.models.dto;

import com.lemonado.smartmeet.core.data.models.users.UserModel;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class GroupDto {

    private long id;
    private String name;
    private UserModel creator;
    private String code;
    private Set<GroupUserDto> users;

}
