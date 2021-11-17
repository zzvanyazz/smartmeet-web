package com.lemonado.smartmeet.web.rest.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class FirebaseAuthService {

    private FirebaseAuth firebaseAuth;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorityService authorityService;

    @PostConstruct
    private void postConstruct() throws IOException {
        FileInputStream refreshToken = new FileInputStream("src/main/resources/smartmeet-f182a-firebase-adminsdk-536q4-2df091ced0.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        FirebaseApp.initializeApp(options);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Optional<Principal> authenticate(String token) {
        try {
            var firebaseToken = firebaseAuth.verifyIdToken(token);
            var user = userService.findActiveUserByEmail(firebaseToken.getEmail());

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
            throws CanNotCreateUserException, LoginFailedException, FirebaseAuthException {
        var passwordHash = passwordEncoder.encode(password);
        var firebaseToken = firebaseAuth.verifyIdToken(token);
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
