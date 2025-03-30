package br.com.addson.projetopraticoimplementacaobackend.dtos.cidade;

import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CidadeUpdateRequest(
        Integer id,
        @NotNull(message = "O nome da cidade é obrigatório.")
        @Size(min = 3, max = 200, message = "O tipo de nome deve ter entre 3 e 200 caracteres.")
        String nome,
        @NotNull(message = "A uf é obrigatória.")
        @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres.")
        String uf
) {
}