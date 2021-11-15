package com.lemonado.smartmeet.web.rest.models.dto;

import com.lemonado.smartmeet.core.data.models.group.AddedUserStatus;
import com.lemonado.smartmeet.core.data.models.group.GroupModel;
import com.lemonado.smartmeet.core.data.models.users.UserModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupUserDto {

    private GroupModel groupModel;
    private UserModel user;
    private AddedUserStatus status;
    private LocalDateTime inviteTime;

}
