package br.com.addson.projetopraticoimplementacaobackend.dtos.login;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "Refresh token não fornecido.")
        String refreshToken
) {}
