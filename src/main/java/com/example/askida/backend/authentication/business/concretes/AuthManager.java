package com.example.askida.backend.authentication.business.concretes;
import com.example.askida.backend.authentication.business.abstracts.AuthenticationService;
import com.example.askida.backend.authentication.business.abstracts.JwtService;
import com.example.askida.backend.authentication.business.requests.SignInRequest;
import com.example.askida.backend.authentication.business.responses.AuthenticationResponse;
import com.example.askida.backend.authentication.business.requests.SignUpRequest;
import com.example.askida.backend.authentication.dataAccess.abstracts.UserRepository;
import com.example.askida.backend.authentication.entities.concretes.Role;
import com.example.askida.backend.authentication.entities.concretes.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        var user = User.builder().
                firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        var user = repository.findByEmail(signInRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
