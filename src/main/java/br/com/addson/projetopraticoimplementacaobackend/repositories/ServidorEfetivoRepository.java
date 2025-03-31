package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Integer> {
    boolean existsByMatricula(String matricula);
    Optional<ServidorEfetivo> findByMatricula(String matricula);

    @Query("SELECT s FROM ServidorEfetivo s WHERE LOWER(s.pessoa.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<ServidorEfetivo> findByNomeContaining(@Param("nome") String nome);
}
