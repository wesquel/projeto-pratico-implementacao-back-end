package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo;

import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResumo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record ServidorEfetivoUnidadeResponse(
        Integer id,
        String nome,
        Integer idade,
        String unidadeLotacao,
        @JsonProperty("fotos")
        Set<FotoPessoaResumo> fotoPessoaResponse
) {
}
