package br.com.addson.projetopraticoimplementacaobackend.dtos.unidade;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.stream.Collectors;

public record UnidadeRequest (
        Integer id,
        @NotNull
        @Size(min = 1, max = 200, message = "O nome deve ter entre 1 e 200 caracteres.")
        String nome,
        @NotNull
        @Size(min = 1, max = 20, message = "O sigla deve ter entre 1 e 20 caracteres.")
        String sigla,
        @Valid
        Set<EnderecoRequest> enderecos
) {
    public Unidade toEntity(){
        Set<Endereco> enderecoSet = this.enderecos.stream()
                .map(EnderecoRequest::toEntity).collect(Collectors.toSet());
        return new Unidade(
                this.nome,
                this.sigla,
                enderecoSet);
    }
}
