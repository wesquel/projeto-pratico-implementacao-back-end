package br.com.addson.projetopraticoimplementacaobackend.dtos.endereco;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeRequest;

public record EnderecoRequest(
        String tipoLogradouro,
        String logradouro,
        Integer numero,
        Integer bairro,
        CidadeRequest cidade
) {
}
