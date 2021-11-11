package com.lemonado.smartmeet.web.rest.controllers;

import com.lemonado.smartmeet.web.rest.models.auth.InvalidTokenException;
import com.lemonado.smartmeet.web.rest.models.auth.TokenBlockedException;
import com.lemonado.smartmeet.web.rest.models.requests.auth.AuthRequest;
import com.lemonado.smartmeet.web.rest.models.requests.auth.RefreshTokenRequest;
import com.lemonado.smartmeet.web.rest.models.responses.ResponseEntityFactory;
import com.lemonado.smartmeet.web.rest.services.AuthorizeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.AuthenticationFailedException;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthorizeService authService;


    @ApiOperation("Authenticate user.")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @Validated @RequestBody AuthRequest request,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntityFactory.createBadRequest(bindingResult);
        }

        var data = authService.authenticate(request);

        return ResponseEntity.ok(data);
    }

    @ApiOperation("Refresh access token.")
    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(
            @Validated @RequestBody RefreshTokenRequest request,
            BindingResult bindingResult
    ) throws AuthenticationFailedException, InvalidTokenException, TokenBlockedException {
        if (bindingResult.hasErrors()) {
            return ResponseEntityFactory.createBadRequest(bindingResult);
        }

        var data = authService.refreshToken(request);

        return ResponseEntity.ok(data);
    }

}
