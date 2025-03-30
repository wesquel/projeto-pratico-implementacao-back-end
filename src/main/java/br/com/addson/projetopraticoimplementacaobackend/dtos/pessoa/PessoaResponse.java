package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;

public record PessoaResponse(
        Integer id,
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String nomeMae,
        String nomePai,
        @JsonProperty("enderecos")
        Set<EnderecoResponse> enderecoResponseSet
) {
    public static PessoaResponse fromEntity(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getNomeMae(),
                pessoa.getNomePai(),
                EnderecoResponse.fromSet(pessoa.getEnderecos())
        );
    }
}
