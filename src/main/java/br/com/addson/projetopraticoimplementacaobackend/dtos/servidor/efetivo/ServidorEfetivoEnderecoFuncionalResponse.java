package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ServidorEfetivoEnderecoFuncionalResponse(
        Integer id,
        String nome,
        @JsonProperty("endereco")
        EnderecoResponse enderecoResponse
) {
}
