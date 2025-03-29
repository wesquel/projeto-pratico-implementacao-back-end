package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
