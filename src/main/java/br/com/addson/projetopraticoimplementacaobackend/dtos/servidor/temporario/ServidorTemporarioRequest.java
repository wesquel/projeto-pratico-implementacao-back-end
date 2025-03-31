package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorTemporario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record ServidorTemporarioRequest(
        Integer id,
        @NotNull
        LocalDate dataAdmissao,
        LocalDate dataDemissao,
        @Valid @JsonProperty("pessoa")
        PessoaRequest pessoaRequest
) {
    public ServidorTemporario toEntity() {
        return new ServidorTemporario(
                this.dataAdmissao,
                this.dataDemissao,
                this.pessoaRequest.toEntity()
        );
    }
}
