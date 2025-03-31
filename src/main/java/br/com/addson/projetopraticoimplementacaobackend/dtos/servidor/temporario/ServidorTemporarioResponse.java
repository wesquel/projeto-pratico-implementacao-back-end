package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorTemporario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ServidorTemporarioResponse(
        Integer id,
        LocalDate dataAdmissao,
        LocalDate dataDemissao,
        @JsonProperty("pessoa")
        PessoaResponse pessoaResponse
) {
    public static ServidorTemporarioResponse fromEntity(ServidorTemporario servidorTemporario) {
        return new ServidorTemporarioResponse(
                servidorTemporario.getId(),
                servidorTemporario.getDataAdmissao(),
                servidorTemporario.getDataDemissao(),
                PessoaResponse.fromEntity(servidorTemporario.getPessoa())
        );
    }
}
