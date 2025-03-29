package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.login.RefreshRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupResponse;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.models.User;
import br.com.addson.projetopraticoimplementacaobackend.services.AuthenticationService;
import br.com.addson.projetopraticoimplementacaobackend.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtService jwtService, AuthenticationService
            authenticationService, @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest input) {
        try {
            SignupResponse response = authenticationService.signup(input);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest input) {
        try {
            User authenticatedUser  = authenticationService.authenticate(input);
            String jwtToken = jwtService.generateToken(authenticatedUser );
            String refreshToken = jwtService.generateRefreshToken(authenticatedUser );
            LoginResponse loginResponse = LoginResponse.fromUserAndTokens(
                    authenticatedUser , jwtToken, refreshToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseException("Credenciais inválidas."));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest request) {
        String refreshToken = request.refreshToken();

        String username;
        try {
            username = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseException("Refresh token inválido."));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            String newJwtToken = jwtService.generateToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);
            LoginResponse loginResponse = LoginResponse.fromUserAndTokens(
                    userDetails, newJwtToken, newRefreshToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseException("Refresh token inválido ou expirado."));
        }
    }
}

