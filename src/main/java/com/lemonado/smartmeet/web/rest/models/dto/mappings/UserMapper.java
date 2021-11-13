package com.lemonado.smartmeet.web.rest.models.dto.mappings;

import com.lemonado.smartmeet.core.data.models.users.UserModel;
import com.lemonado.smartmeet.core.data.models.users.builders.UserModelBuilder;
import com.lemonado.smartmeet.web.rest.models.dto.UserDto;

public class UserMapper {

    public static UserDto toDto(UserModel userModel) {
        return UserDto.builder()
                .withId(userModel.id())
                .withUsername(userModel.username())
                .withEmail(userModel.email())
                .withDeleteTimestamp(userModel.deleteTimestamp())
                .withValidTokenTimestamp(userModel.validTokenTimestamp())
                .build();
    }

    public static UserModel toModel(UserDto userDto) {
        return new UserModelBuilder()
                .withId(userDto.getId())
                .withUsername(userDto.getUsername())
                .withEmail(userDto.getEmail())
                .withDeleteTimestamp(userDto.getDeleteTimestamp())
                .withValidTokenTimestamp(userDto.getValidTokenTimestamp())
                .build();

    }

}
