package br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoResumo;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioResumo;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorTemporario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record PessoaResponse(
        Integer id,
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String nomeMae,
        String nomePai,
        @JsonProperty("enderecos")
        Set<EnderecoResponse> enderecoResponseSet,
        @JsonProperty("servidoresEfetivos")
        Set<ServidorEfetivoResumo> servidoresEfetivos,
        @JsonProperty("servidoresTemporarios")
        Set<ServidorTemporarioResumo> servidorTemporarios

) {
    public static PessoaResponse fromEntity(Pessoa pessoa) {
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getNomeMae(),
                pessoa.getNomePai(),
                EnderecoResponse.fromSet(pessoa.getEnderecos()),
                pessoa.getServidoresEfetivos() == null ? Set.of() :
                        pessoa.getServidoresEfetivos().stream()
                                .map(ServidorEfetivoResumo::fromEntity)
                                .collect(Collectors.toSet()),
                pessoa.getServidoresTemporarios() == null ? Set.of() : // Adicionando a conversão para ServidorTemporarioResponse
                        pessoa.getServidoresTemporarios().stream()
                                .map(ServidorTemporarioResumo::fromEntity)
                                .collect(Collectors.toSet())
        );
    }
}
