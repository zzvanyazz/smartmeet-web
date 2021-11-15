package com.lemonado.smartmeet.web.rest.models.dto.mappings;

import com.lemonado.smartmeet.core.data.models.group.GroupUserModel;
import com.lemonado.smartmeet.core.data.models.group.builder.GroupUserBuilder;
import com.lemonado.smartmeet.web.rest.models.dto.GroupUserDto;

public class GroupUserMapper {

    public static GroupUserModel toModel(GroupUserDto groupUserDto) {
        return GroupUserBuilder.builder()
                .withGroupModel(groupUserDto.getGroupModel())
                .withUser(groupUserDto.getUser())
                .withStatus(groupUserDto.getStatus())
                .withInviteTime(groupUserDto.getInviteTime())
                .build();
    }

    public static GroupUserDto toDto(GroupUserModel groupUserModel) {
        GroupUserDto groupUserDto = new GroupUserDto();
        groupUserDto.setGroupModel(groupUserModel.groupModel());
        groupUserDto.setUser(groupUserModel.user());
        groupUserDto.setStatus(groupUserModel.status());
        groupUserDto.setInviteTime(groupUserModel.inviteTime());
        return groupUserDto;
    }

}
