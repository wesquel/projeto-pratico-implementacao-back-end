package br.com.addson.projetopraticoimplementacaobackend.dtos.signup;

import br.com.addson.projetopraticoimplementacaobackend.models.User;

public record SignupResponse(Integer id, String username, String message) {
    public static SignupResponse fromEntity(User user) {
        return new SignupResponse(user.getId(), user.getUsername(), "Usuário criado com sucesso.");
    }
}
