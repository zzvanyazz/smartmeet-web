package com.lemonado.smartmeet.web.rest.models.dto.mappings;

import com.lemonado.smartmeet.core.data.models.group.GroupModel;
import com.lemonado.smartmeet.web.rest.models.dto.GroupDto;

public class GroupMapper {

    public static GroupDto toDto(GroupModel groupModel) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupModel.id());
        groupDto.setName(groupModel.name());
        groupDto.setCreator(groupModel.creator());
        groupDto.setCode(groupModel.code());
        groupDto.setUsers(groupModel.users());
        return groupDto;
    }


}
