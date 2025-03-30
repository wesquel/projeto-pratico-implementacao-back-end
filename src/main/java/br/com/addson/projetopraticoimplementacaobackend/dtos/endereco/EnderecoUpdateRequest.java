package br.com.addson.projetopraticoimplementacaobackend.dtos.endereco;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoUpdateRequest(
        Integer id,
        @NotNull(message = "O tipo de logradouro é obrigatório.")
        @Size(min = 3, max = 50, message = "O tipo de logradouro deve ter entre 3 e 50 caracteres.")
        String tipoLogradouro,

        @NotNull
        @Size(min = 3, max = 200, message = "O logradouro deve ter entre 3 e 200 caracteres.")
        String logradouro,

        @NotNull
        Integer numero,
        @Size(min = 3, max = 100, message = "O bairro deve ter entre 3 e 100 caracteres.")
        String bairro,

        @NotNull(message = "A cidade é obrigatória.")
        CidadeUpdateRequest cidade
) {
}

