package com.lemonado.smartmeet.web.rest.models.dto;

import com.lemonado.smartmeet.core.data.models.timeline.TimeLineType;

import java.time.LocalDateTime;


public record TimeLineDto(
        long id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        GroupDto group,
        UserDto user,
        TimeLineType timeLineType) {
}
