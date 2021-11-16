package com.lemonado.smartmeet.web.rest.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.tasks.Tasks;
import com.lemonado.smartmeet.core.data.exceptions.CanNotCreateUserException;
import com.lemonado.smartmeet.core.data.exceptions.LoginFailedException;
import com.lemonado.smartmeet.core.data.exceptions.UserAlreadyExistsException;
import com.lemonado.smartmeet.core.data.models.users.builders.UserModelBuilder;
import com.lemonado.smartmeet.core.services.base.users.PasswordEncoder;
import com.lemonado.smartmeet.core.services.base.users.UserService;
import com.lemonado.smartmeet.web.rest.models.auth.Principal;
import com.lemonado.smartmeet.web.rest.models.responses.AuthResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseAuthService {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorityService authorityService;

    public Optional<Principal> authenticate(String token) {
        try {
            var firebaseToken = Tasks.await(firebaseAuth.verifyIdToken(token));
            var user = userService.findActiveUser(firebaseToken.getEmail());

            if (firebaseToken.isEmailVerified()) {
                var authorities = authorityService.getAuthorities(user);
                var principal = Principal.fromUser(user);

                principal.setAuthorities(authorities);

                return Optional.of(principal);
            }
        } catch (Exception ignored) {
        }

        return Optional.empty();
    }

    public AuthResponseData sigUp(String token, String password)
            throws ExecutionException, InterruptedException, CanNotCreateUserException, LoginFailedException {
        var passwordHash = passwordEncoder.encode(password);
        var firebaseToken = Tasks.await(firebaseAuth.verifyIdToken(token));
        var userModel = UserModelBuilder.builder()
                .withUsername(firebaseToken.getName())
                .withEmail(firebaseToken.getEmail())
                .withPasswordHash(passwordHash)
                .build();

        try {
            userModel = userService.createNewUser(userModel);
        } catch (UserAlreadyExistsException e) {
            userModel = userService.login(userModel.email(), password);
        }
        return tokenService.createAuthData(userModel);
    }



}
