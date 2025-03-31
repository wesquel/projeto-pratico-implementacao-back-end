package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Integer> {
    boolean existsByMatricula(String matricula);
    Optional<ServidorEfetivo> findByMatricula(String matricula);
}
