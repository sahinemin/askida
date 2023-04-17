package com.example.askida.backend.authentication.webApi.controllers;

import com.example.askida.backend.authentication.business.abstracts.AuthenticationService;
import com.example.askida.backend.authentication.business.requests.SignInRequest;
import com.example.askida.backend.authentication.business.requests.SignUpRequest;
import com.example.askida.backend.authentication.business.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign-up")
    public ResponseEntity <AuthenticationResponse> signUp(
            @RequestBody SignUpRequest signUpRequest
    ){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }
    @PostMapping("/sign-in")
    public ResponseEntity <AuthenticationResponse> signIn(
            @RequestBody SignInRequest signInRequest
    ){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }


}
