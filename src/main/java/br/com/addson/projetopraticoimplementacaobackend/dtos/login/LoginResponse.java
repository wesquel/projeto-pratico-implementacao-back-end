package br.com.addson.projetopraticoimplementacaobackend.dtos.login;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.User;
import org.springframework.security.core.userdetails.UserDetails;


public record LoginResponse(String username, String token, String refreshToken,
                            long expiresIn, PessoaResponse pessoa, String message) {

    public static LoginResponse fromUserAndTokens(User user, String jwtToken,
                                                  String refreshToken, long expirationTime) {
        PessoaResponse pessoaResponse = (user.getPessoa() != null)
                ? PessoaResponse.fromEntity(user.getPessoa()) : null;
        return new LoginResponse(user.getUsername(), jwtToken, refreshToken, expirationTime,
                pessoaResponse, "Login realizado com sucesso.");
    }

    public static LoginResponse fromUserAndTokens(UserDetails userDetails, String jwtToken,
                                                  String refreshToken, long expirationTime) {
        PessoaResponse pessoaResponse = null;
        if (userDetails instanceof User user) {
            pessoaResponse = (user.getPessoa() != null) ? PessoaResponse.fromEntity(user.getPessoa()) : null;
        }
        return new LoginResponse(userDetails.getUsername(), jwtToken, refreshToken, expirationTime,
                pessoaResponse, "Token atualizado com sucesso.");
    }
}
