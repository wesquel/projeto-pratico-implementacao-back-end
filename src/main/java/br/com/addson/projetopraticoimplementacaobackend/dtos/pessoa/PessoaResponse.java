package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;

import java.time.LocalDate;

public record PessoaResponse(
        Integer id,
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String nomeMae,
        String nomePai
) {
    public static PessoaResponse fromEntity(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getNomeMae(),
                pessoa.getNomePai()
        );
    }
}
