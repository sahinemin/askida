package com.example.askida.backend.authentication.business.abstracts;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public interface JwtService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);
    boolean isTokenExpired(String token);

    Date extractExpiration(String token);
    Claims extractAllClaims(String token);
    SecretKey getSignInKey();

}
