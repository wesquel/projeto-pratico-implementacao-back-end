package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record PessoaRequest(
        Integer id,
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória.")
        LocalDate dataNascimento,

        @NotBlank(message = "O sexo é obrigatório.")
        String sexo,

        String nomeMae,
        String nomePai,
        Set<EnderecoRequest> enderecos
) {
        public Pessoa toEntity() {
                Set<Endereco> enderecoEntities = enderecos.stream()
                        .map(EnderecoRequest::toEntity)
                        .collect(Collectors.toSet());

                return new Pessoa(
                        this.nome,
                        this.dataNascimento,
                        this.sexo,
                        this.nomeMae,
                        this.nomePai,
                        enderecoEntities
                );
        }
}
