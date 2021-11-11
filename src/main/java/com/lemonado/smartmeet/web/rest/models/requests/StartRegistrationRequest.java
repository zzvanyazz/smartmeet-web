package com.lemonado.smartmeet.web.rest.models.requests;

public record StartRegistrationRequest(
        String registrationCode,
        String userName,
        String normalisedUserName,
        String password) {
}
