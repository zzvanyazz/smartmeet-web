package com.lemonado.smartmeet.web.rest.models.dto.mappings;

import com.lemonado.smartmeet.core.data.models.group.GroupModel;
import com.lemonado.smartmeet.web.rest.models.dto.GroupDto;

import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupDto toDto(GroupModel groupModel) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupModel.id());
        groupDto.setName(groupModel.name());
        groupDto.setCreator(UserMapper.toDto(groupModel.creator()));
        groupDto.setCode(groupModel.code());
        var users = groupModel.users();
        if (users != null){
            groupDto.setUsers(users
                    .stream()
                    .map(GroupUserMapper::toDto)
                    .collect(Collectors.toSet()));
        }
        return groupDto;
    }


}
