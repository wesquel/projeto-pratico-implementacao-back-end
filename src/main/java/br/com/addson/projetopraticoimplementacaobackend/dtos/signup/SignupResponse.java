package br.com.addson.projetopraticoimplementacaobackend.dtos.signup;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.User;

public record SignupResponse(Integer id, String username, PessoaResponse pessoa, String message) {
    public static SignupResponse fromEntity(User user) {
        return new SignupResponse(user.getId(), user.getUsername(),
                PessoaResponse.fromEntity(user.getPessoa()),"Usuário criado com sucesso.");
    }
}
