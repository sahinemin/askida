package com.example.askida.backend.core;
import com.example.askida.backend.config.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final Optional<String> authenticationHeader = Optional.ofNullable(request.getHeader("Authorization"));
        final String jwt;
        final Optional<String> userEmail;
        if (authenticationHeader.isEmpty() || !authenticationHeader.get().startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authenticationHeader.get().substring(7);
        userEmail = Optional.ofNullable(jwtService.extractUserName(jwt));
        if (userEmail.isPresent() && getAuthenticationStatus()){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail.get());
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean getAuthenticationStatus(){
        return SecurityContextHolder.getContext().getAuthentication()==null;
    }
}
