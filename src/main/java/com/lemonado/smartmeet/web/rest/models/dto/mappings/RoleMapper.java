package com.lemonado.smartmeet.web.rest.models.dto.mappings;

import com.lemonado.smartmeet.core.data.models.roles.RoleModel;
import com.lemonado.smartmeet.web.rest.models.dto.RoleDto;

public class RoleMapper {

    public static RoleDto toDto(RoleModel model) {
        return RoleDto.builder()
                .withId(model.id())
                .withName(model.name())
                .withDescription(model.description())
                .withCreateTimestamp(model.createTimestamp())
                .withUpdateTimestamp(model.updateTimestamp())
                .build();
    }

}
