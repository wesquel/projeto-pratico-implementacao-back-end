package br.com.addson.projetopraticoimplementacaobackend.dtos.cidade;

import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;

public record CidadeResponse(String nome, String cidade) {
    public static CidadeResponse fromEntity(Cidade cidade){
        return new CidadeResponse(cidade.getNome(), cidade.getUf());
    }
}
