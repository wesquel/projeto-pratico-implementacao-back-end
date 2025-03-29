package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PessoaRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória.")
        LocalDate dataNascimento,

        @NotBlank(message = "O sexo é obrigatório.")
        String sexo,

        String nomeMae,
        String nomePai
) {
}
