package br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa;

import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;

import java.time.LocalDate;

public record FotoPessoaResumo(
        Integer id,
        LocalDate data,
        String bucket,
        String hash
) {
    public static FotoPessoaResumo fromEntity(FotoPessoa fotoPessoa) {
        return new FotoPessoaResumo(
                fotoPessoa.getId(),
                fotoPessoa.getData(),
                fotoPessoa.getBucket(),
                fotoPessoa.getHash()
        );
    }
}
