package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupResponse;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.models.User;
import br.com.addson.projetopraticoimplementacaobackend.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public SignupResponse signup(SignupRequest input) {

        if (userRepository.existsByUsername(input.username())) {
            throw new UserAlreadyExistsException("O nome de usuário já registrado.");
        }

        User user = new User();
        user.setUsername(input.username());
        user.setPassword(passwordEncoder.encode(input.password()));
        User savedUser  = userRepository.save(user);
        return SignupResponse.fromEntity(savedUser);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return userRepository.findByUsername(input.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }
}
