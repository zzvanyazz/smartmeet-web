package com.lemonado.smartmeet.web.rest.models.requests.timeline.mapping;


import com.lemonado.smartmeet.core.data.models.group.builder.GroupModelBuilder;
import com.lemonado.smartmeet.core.data.models.timeline.TimeLineModel;
import com.lemonado.smartmeet.core.data.models.timeline.bilders.TimeLineBuilder;
import com.lemonado.smartmeet.core.data.models.users.builders.UserModelBuilder;
import com.lemonado.smartmeet.web.rest.models.requests.timeline.CreateTimeLineRequest;

public class TimeLineRequestMapper {

    public static TimeLineModel toModel(CreateTimeLineRequest request, long groupId, long userId) {
        return TimeLineBuilder.builder()
                .withoutId()
                .withStartDate(request.startDate())
                .withEndDate(request.endDate())
                .withGroupModel(GroupModelBuilder.builder().withId(groupId).build())
                .withUser(UserModelBuilder.builder().withId(userId).build())
                .withTimeLineType(request.timeLineType())
                .build();
    }


}
