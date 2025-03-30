package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record ServidorEfetivoRequest(
        Integer id,
        @Size(min = 1, max = 20, message = "A matricula deve ter entre 1 e 20 caracteres.")
        String matricula,
        @JsonProperty("pessoa")
        @Valid
        PessoaRequest pessoaRequest
) {
    public ServidorEfetivo toEntity() {
        return new ServidorEfetivo(
                this.matricula,
                this.pessoaRequest.toEntity()
        );
    }
}
