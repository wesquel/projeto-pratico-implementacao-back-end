package br.com.addson.projetopraticoimplementacaobackend.dtos.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O nome de usuário é obrigatório.")
        String username,
        @NotBlank(message = "A senha é obrigatória.")
        String password) {
}
