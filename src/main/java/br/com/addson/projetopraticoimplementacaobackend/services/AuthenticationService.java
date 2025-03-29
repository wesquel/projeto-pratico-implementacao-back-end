package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.login.LoginRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.signup.SignupResponse;
import br.com.addson.projetopraticoimplementacaobackend.enums.SexoEnum;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
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

        if ((input.pessoaRequest().nomeMae() == null || input.pessoaRequest().nomeMae().isBlank()) &&
                (input.pessoaRequest().nomePai() == null || input.pessoaRequest().nomePai().isBlank())) {
            throw new IllegalArgumentException(
                    "Pelo menos um dos campos 'nome do pai' ou 'nome da mãe' deve ser preenchido.");
        }

        if (userRepository.existsByUsername(input.username())) {
            throw new UserAlreadyExistsException("O nome de usuário já registrado.");
        }

        SexoEnum sexoEnum = SexoEnum.fromInput(input.pessoaRequest().sexo());

        User user = new User();
        user.setUsername(input.username());
        user.setPassword(passwordEncoder.encode(input.password()));

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(input.pessoaRequest().nome());
        pessoa.setDataNascimento(input.pessoaRequest().dataNascimento());
        pessoa.setSexo(sexoEnum.getValor());
        pessoa.setNomeMae(input.pessoaRequest().nomeMae());
        pessoa.setNomePai(input.pessoaRequest().nomePai());

        user.setPessoa(pessoa);
        pessoa.setUsuario(user);

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
