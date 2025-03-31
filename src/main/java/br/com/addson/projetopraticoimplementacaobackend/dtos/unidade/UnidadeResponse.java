package br.com.addson.projetopraticoimplementacaobackend.dtos.unidade;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;

import java.util.Set;

public record UnidadeResponse(
        Integer id,
        String nome,
        String sigla,
        Set<EnderecoResponse> enderecos
) {
    public static UnidadeResponse fromEntity(Unidade unidade) {
        return new UnidadeResponse(
                unidade.getId(),
                unidade.getNome(),
                unidade.getSigla(),
                EnderecoResponse.fromSet(unidade.getEnderecos())
        );
    }
}
