package br.com.addson.projetopraticoimplementacaobackend.dtos.endereco;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;

public record EnderecoRequest(
        String tipoLogradouro,
        String logradouro,
        Integer numero,
        String bairro,
        CidadeRequest cidade
) {
    public Endereco toEntity() {
        return new Endereco(
                this.tipoLogradouro,
                this.logradouro,
                this.numero,
                this.bairro,
                this.cidade.toEntity()
        );
    }
}

