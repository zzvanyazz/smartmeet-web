package com.lemonado.smartmeet.web.rest.models.mappings;

import com.lemonado.smartmeet.core.data.models.users.UserModel;
import com.lemonado.smartmeet.core.data.models.users.builders.UserModelBuilder;
import com.lemonado.smartmeet.web.rest.models.dto.UserDto;
import com.lemonado.smartmeet.web.rest.models.dto.builders.UserDtoBuilder;

public class UserMapper {

    public static UserDto toDto(UserModel userModel) {
        return new UserDtoBuilder()
                .withId(userModel.id())
                .withUsername(userModel.username())
                .withEmail(userModel.email())
                .withDeleteTimestamp(userModel.deleteTimestamp())
                .withValidTokenTimestamp(userModel.validTokenTimestamp())
                .build();
    }

    public static UserModel toEntity(UserDto userDto) {
        return new UserModelBuilder()
                .withId(userDto.id())
                .withUsername(userDto.username())
                .withEmail(userDto.email())
                .withDeleteTimestamp(userDto.deleteTimestamp())
                .withValidTokenTimestamp(userDto.validTokenTimestamp())
                .build();

    }

}
