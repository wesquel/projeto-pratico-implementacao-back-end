package br.com.addson.projetopraticoimplementacaobackend.dtos.endereco;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record EnderecoResponse(
        Integer id,
        String tipoLogradouro,
        String logradouro,
        Integer numero,
        String bairro,
        @JsonProperty("cidade")
        CidadeResponse cidadeResponse)
{
    public static EnderecoResponse fromEntity(Endereco endereco){
        return new EnderecoResponse(
                endereco.getId(),
                endereco.getTipoLogradouro(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                CidadeResponse.fromEntity(endereco.getCidade())
        );
    }

    public static Set<EnderecoResponse> fromSet(Set<Endereco> enderecosSet){

        if (enderecosSet == null || enderecosSet.isEmpty()) {
            return Set.of();
        }

        return enderecosSet.stream().map(EnderecoResponse::fromEntity)
                .collect(Collectors.toSet());
    }
}
