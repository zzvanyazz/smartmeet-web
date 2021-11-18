package com.lemonado.smartmeet.web.rest.models.requests.timeline;

import com.lemonado.smartmeet.core.data.models.timeline.TimeLineType;
import lombok.NonNull;

import java.time.LocalDateTime;

public record CreateTimeLineRequest(
        @NonNull
        LocalDateTime startDate,
        @NonNull
        LocalDateTime endDate,
        @NonNull
        TimeLineType timeLineType) {
}
