package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record PessoaUpdateRequest(
        @NotNull(message = "O ID da pessoa é obrigatório.")
        Integer id,

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória.")
        LocalDate dataNascimento,

        @NotBlank(message = "O sexo é obrigatório.")
        String sexo,

        String nomeMae,
        String nomePai,
        @Valid
        Set<EnderecoUpdateRequest> enderecos
) {
}
