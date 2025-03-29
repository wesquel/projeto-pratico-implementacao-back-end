package br.com.addson.projetopraticoimplementacaobackend.dtos.login;

public record LoginResponse(String token, String refreshToken, Long expiresIn) {
}
