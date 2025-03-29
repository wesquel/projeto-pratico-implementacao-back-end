package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.User;
import br.com.addson.projetopraticoimplementacaobackend.services.AuthenticationService;
import br.com.addson.projetopraticoimplementacaobackend.services.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignupRequest input) {
        User registeredUser  = authenticationService.signup(input);
        return ResponseEntity.ok(registeredUser );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest input) {
        User authenticatedUser = authenticationService.authenticate(input);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser );
        LoginResponse loginResponse = new LoginResponse(jwtToken, refreshToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            String newJwtToken = jwtService.generateToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(newJwtToken, newRefreshToken, jwtService.getExpirationTime()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

