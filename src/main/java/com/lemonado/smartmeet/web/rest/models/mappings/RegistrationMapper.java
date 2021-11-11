package com.lemonado.smartmeet.web.rest.models.mappings;

import com.lemonado.smartmeet.core.data.exceptions.RegistrationProblemsException;
import com.lemonado.smartmeet.web.rest.models.responses.LetterNotSentResponse;
import com.lemonado.smartmeet.web.rest.models.responses.NotToAllUsersLetterSentResponse;

import java.util.stream.Collectors;

public class RegistrationMapper {

    public static NotToAllUsersLetterSentResponse toResponse(RegistrationProblemsException exception) {
        var response = new NotToAllUsersLetterSentResponse();
        var list = exception.getExceptions()
                .stream()
                .map(RegistrationMapper::toResponse)
                .collect(Collectors.toList());
        response.setLetterNotSentResponses(list);
        return response;
    }

    public static LetterNotSentResponse toResponse(Exception exception) {
        return new LetterNotSentResponse(exception.getMessage());
    }

}
