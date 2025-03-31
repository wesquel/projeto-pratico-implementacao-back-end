package br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa;

import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;

import java.time.LocalDate;

public record FotoPessoaRequest(
        Integer id,
        LocalDate data,
        String bucket,
        String hash,
        Integer pessoaId
) {
    public FotoPessoa toEntity(){
        return new FotoPessoa(data, bucket, hash);
    }
}
