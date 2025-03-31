package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
}
