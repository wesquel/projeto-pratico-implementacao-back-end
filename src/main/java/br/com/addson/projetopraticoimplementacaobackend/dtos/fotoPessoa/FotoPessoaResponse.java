package br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;

import java.time.LocalDate;

public record FotoPessoaResponse(
        Integer id,
        LocalDate data,
        String bucket,
        String hash,
        PessoaResponse pessoaResponse
) {
    public static FotoPessoaResponse fromEntity(FotoPessoa fotoPessoa) {
        return new FotoPessoaResponse(
                fotoPessoa.getId(),
                fotoPessoa.getData(),
                fotoPessoa.getBucket(),
                fotoPessoa.getHash(),
                PessoaResponse.fromEntity(fotoPessoa.getPessoa())
        );
    }
}
