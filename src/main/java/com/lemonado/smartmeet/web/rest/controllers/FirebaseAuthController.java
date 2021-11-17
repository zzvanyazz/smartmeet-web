package com.lemonado.smartmeet.web.rest.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.lemonado.smartmeet.core.data.exceptions.CanNotCreateUserException;
import com.lemonado.smartmeet.core.data.exceptions.LoginFailedException;
import com.lemonado.smartmeet.web.rest.models.requests.auth.FirebaseLoginRequest;
import com.lemonado.smartmeet.web.rest.services.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/auth/firebase")
public class FirebaseAuthController {

    @Autowired
    private FirebaseAuthService authService;

    @PostMapping
    public ResponseEntity<?> loginByFirebase(@RequestBody FirebaseLoginRequest request)
            throws CanNotCreateUserException, LoginFailedException, FirebaseAuthException {
        var authResponse = authService.sigUp(request.token(), request.password());
        return ResponseEntity.ok(authResponse);
    }

}
