package br.com.addson.projetopraticoimplementacaobackend.dtos.login;

import br.com.addson.projetopraticoimplementacaobackend.models.User;
import org.springframework.security.core.userdetails.UserDetails;


public record LoginResponse(String username, String token, String refreshToken,
                            long expiresIn, String message) {

    public static LoginResponse fromUserAndTokens(User user, String jwtToken,
                                                  String refreshToken, long expirationTime) {

        return new LoginResponse(user.getUsername(), jwtToken, refreshToken, expirationTime,
                "Login realizado com sucesso.");
    }

    public static LoginResponse fromUserAndTokens(UserDetails userDetails, String jwtToken,
                                                  String refreshToken, long expirationTime) {

        return new LoginResponse(userDetails.getUsername(), jwtToken, refreshToken, expirationTime
                ,"Token atualizado com sucesso.");
    }
}
