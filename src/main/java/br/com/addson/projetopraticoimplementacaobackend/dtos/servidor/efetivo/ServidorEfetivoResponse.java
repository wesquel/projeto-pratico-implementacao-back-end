package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import com.fasterxml.jackson.annotation.JsonProperty;


public record ServidorEfetivoResponse(
        Integer id,
        String matricula,
        @JsonProperty("pessoa")
        PessoaResponse pessoaResponse
) {
    public static ServidorEfetivoResponse fromEntity(ServidorEfetivo servidorEfetivo) {
        return new ServidorEfetivoResponse(
                servidorEfetivo.getId(),
                servidorEfetivo.getMatricula(),
                PessoaResponse.fromEntity(servidorEfetivo.getPessoa())
        );
    }
}
