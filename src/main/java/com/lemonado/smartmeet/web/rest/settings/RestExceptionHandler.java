package com.lemonado.smartmeet.web.rest.settings;

import com.lemonado.smartmeet.core.data.exceptions.*;
import com.lemonado.smartmeet.core.data.exceptions.group.GroupNameAlreadyExists;
import com.lemonado.smartmeet.core.data.exceptions.timeline.InvalidTimeLineException;
import com.lemonado.smartmeet.web.rest.models.auth.exception.InvalidTokenException;
import com.lemonado.smartmeet.web.rest.models.auth.exception.TokenBlockedException;
import com.lemonado.smartmeet.web.rest.models.responses.Response;
import com.lemonado.smartmeet.web.rest.models.responses.ResponseFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;

@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            AccessDeniedException.class,
            AuthenticationFailedException.class,
            InvalidTokenException.class,
            LoginFailedException.class,
            TokenBlockedException.class,
    })
    protected ResponseEntity<?> handleForbidden(Exception exception, WebRequest request) {
        var response = ResponseFactory.createForbidden(exception);

        return handleException(exception, response, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {
            RoleNotFoundException.class,
            UserNotFoundException.class,
            RegistrationCodeNotFoundException.class,
    })
    protected ResponseEntity<?> handleNotFound(Exception exception, WebRequest request) {
        var response = ResponseFactory.createNotFound(exception);

        return handleException(exception, response, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            CanNotCreateUserException.class,
            CanNotSendMailException.class,
            UserAlreadyExistsException.class,
            UserIsBlockedException.class,
            ActionOnAdminRoleException.class,
            GroupNameAlreadyExists.class,
    })
    protected ResponseEntity<?> handleConflict(Exception exception, WebRequest request) {
        var response = ResponseFactory.createConflict(exception);

        return handleException(exception, response, HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler(value = {
            InvalidTimeLineException.class,
    })
    protected ResponseEntity<?> handleBadRequestException(Exception exception, WebRequest request) {
        var response = ResponseFactory.createConflict(exception);

        return handleException(exception, response, HttpStatus.BAD_REQUEST, request);
    }


    private ResponseEntity<?> handleException(Exception exception, Response<?> response, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = {
            CanNotCreateGroupException.class,
    })
    protected ResponseEntity<?> handleInternalException(Exception exception, WebRequest request) {
        var response = ResponseFactory.createConflict(exception);

        return handleException(exception, response, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
