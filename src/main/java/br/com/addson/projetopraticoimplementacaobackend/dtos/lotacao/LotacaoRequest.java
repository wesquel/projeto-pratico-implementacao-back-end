package br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao;

import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record LotacaoRequest(
        Integer id,
        @NotNull(message = "ID de pessoa não pode ser nulo")
        Integer pessoaId,
        @NotNull(message = "ID de unidade não pode ser nulo")
        Integer unidId,
        @NotNull(message = "A data de lotação não pode ser nula")
        LocalDate dataLotacao,
        LocalDate dataRemocao,
        @NotBlank(message = "O portaria é obrigatório.")
        @Size(min = 1, max = 100, message = "A portaria deve ter entre 1 e 100 caracteres.")
        String portaria
) {
    public Lotacao toEntity(){
        return new Lotacao(dataLotacao, dataRemocao, portaria);
    }
}
