package com.example.askida.backend.authentication.business.abstracts;

import com.example.askida.backend.authentication.business.requests.SignInRequest;
import com.example.askida.backend.authentication.business.requests.SignUpRequest;
import com.example.askida.backend.authentication.business.responses.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
     AuthenticationResponse signUp(SignUpRequest signUpRequest);
     AuthenticationResponse signIn(SignInRequest signInRequest);

}
