package br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao;

import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record LotacaoResumo(
        Integer id,
        LocalDate dataLotacao,
        LocalDate dataRemocao,
        String portaria,
        @JsonProperty("unidade")
        UnidadeResponse unidadeResponse
) {
    public static LotacaoResumo fromEntity(Lotacao lotacao){
        return new LotacaoResumo(lotacao.getId(),
                lotacao.getDataLocacao(),
                lotacao.getDataRemocao(),
                lotacao.getPortaria(),
                UnidadeResponse.fromEntity(lotacao.getUnidade())
        );
    }
}
