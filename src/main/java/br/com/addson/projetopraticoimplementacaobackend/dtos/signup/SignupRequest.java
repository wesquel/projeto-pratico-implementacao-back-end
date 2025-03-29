package br.com.addson.projetopraticoimplementacaobackend.dtos.signup;


import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

public record SignupRequest(
        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres.")
        String username,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password,

        @JsonProperty("pessoa")
        PessoaRequest pessoaRequest
) {}
