package br.com.addson.projetopraticoimplementacaobackend.dtos.cidade;

import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;

public record CidadeResponse(Integer id, String nome, String uf) {
    public static CidadeResponse fromEntity(Cidade cidade){
        return new CidadeResponse(
                cidade.getId(),
                cidade.getNome(),
                cidade.getUf()
        );
    }
}
