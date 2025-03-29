package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.User;
import br.com.addson.projetopraticoimplementacaobackend.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(SignupRequest input) {
        User user = new User();
        user.setUsername(input.username());
        user.setPassword(passwordEncoder.encode(input.password()));

        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return userRepository.findByUsername(input.username())
                .orElseThrow();
    }
}
