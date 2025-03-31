package br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record LotacaoResponse(
        Integer id,
        LocalDate dataLotacao,
        LocalDate dataRemocao,
        String portaria,
        @JsonProperty("pessoa")
        PessoaResponse pessoaResponse,
        @JsonProperty("unidade")
        UnidadeResponse unidadeResponse
) {
    public static LotacaoResponse fromEntity(Lotacao lotacao){
        return new LotacaoResponse(lotacao.getId(),
                lotacao.getDataLocacao(),
                lotacao.getDataRemocao(),
                lotacao.getPortaria(),
                PessoaResponse.fromEntity(lotacao.getPessoa()),
                UnidadeResponse.fromEntity(lotacao.getUnidade())
        );
    }
}
