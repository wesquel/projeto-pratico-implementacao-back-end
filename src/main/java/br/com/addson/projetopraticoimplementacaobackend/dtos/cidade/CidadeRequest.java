package br.com.addson.projetopraticoimplementacaobackend.dtos.cidade;

import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;

public record CidadeRequest(String nome, String uf) {
    public Cidade toEntity() {
        return new Cidade(this.nome, this.uf);
    }
}
