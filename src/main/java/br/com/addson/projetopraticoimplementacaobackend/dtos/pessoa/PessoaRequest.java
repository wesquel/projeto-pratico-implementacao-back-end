package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
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
        @NotNull(message = "A lista de endereços não pode ser nula.")
        Set<EnderecoRequest> enderecos,

        @NotNull(message = "A lista de servidores Efetivos não pode ser nula.")
        Set<String> servidoresEfetivos
) {
        public Pessoa toEntity() {
                Set<Endereco> enderecoEntities = enderecos.stream()
                        .map(EnderecoRequest::toEntity)
                        .collect(Collectors.toSet());

                Set<ServidorEfetivo> servidorEfetivosEntities = new HashSet<>();;

                return new Pessoa(
                        this.nome,
                        this.dataNascimento,
                        this.sexo,
                        this.nomeMae,
                        this.nomePai,
                        enderecoEntities,
                        servidorEfetivosEntities
                );
        }
}
