package com.lemonado.smartmeet.web.rest.models.requests.auth;


public record FirebaseLoginRequest(
        String token,
        String password) {
}
