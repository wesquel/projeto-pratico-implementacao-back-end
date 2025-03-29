package br.com.addson.projetopraticoimplementacaobackend.exceptions.auth;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
