package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.data.exceptions.group.InvalidGroupException;
import com.lemonado.smartmeet.core.data.exceptions.group.UnsupportedGroupException;
import com.lemonado.smartmeet.core.data.exceptions.timeline.InvalidTimeLineException;
import com.lemonado.smartmeet.core.services.base.timeline.TimeLineService;
import com.lemonado.smartmeet.web.rest.models.requests.timeline.CreateTimeLineRequest;
import com.lemonado.smartmeet.web.rest.models.requests.timeline.mapping.TimeLineRequestMapper;
import com.lemonado.smartmeet.web.rest.services.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/groups")
public class TimeLineController {

    @Autowired
    private TimeLineService timeLineService;

    @Autowired
    private CurrentUserService currentUserService;



    @PostMapping("/{groupId}/users/time-lines")
    public ResponseEntity<?> createNewTimeLine(@PathVariable long groupId,
                                               @RequestBody CreateTimeLineRequest request)
            throws UserNotFoundException, InvalidGroupException, UnsupportedGroupException, InvalidTimeLineException {
        var userId = currentUserService.getId();
        var model = TimeLineRequestMapper.toModel(request, groupId, userId);
        model = timeLineService.addNewTimeLine(model);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{groupId}/users/time-lines")
    public ResponseEntity<?> getTimeLines(@PathVariable long groupId)
            throws UserNotFoundException, InvalidGroupException, UnsupportedGroupException {
        var userId = currentUserService.getId();
        var model = timeLineService.getTimeLines(groupId, userId);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{groupId}/time-lines")
    public ResponseEntity<?> getGroupTimeLines(@PathVariable long groupId) throws InvalidGroupException {
        var model = timeLineService.getTimeLines(groupId);
        return ResponseEntity.ok(model);
    }


}
