package br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa;

import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;

import java.time.LocalDate;

public record FotoPessoaRequest(
        Integer id,
        String bucket,
        Integer pessoaId
) {
    public FotoPessoa toEntity(){
        return new FotoPessoa(bucket);
    }
}
