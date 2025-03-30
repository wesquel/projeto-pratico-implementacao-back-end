package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Optional<Cidade> findByNomeAndUf(String nome, String uf);
}
