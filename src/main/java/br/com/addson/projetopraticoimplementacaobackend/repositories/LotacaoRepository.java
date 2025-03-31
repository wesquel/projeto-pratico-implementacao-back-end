package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
    List<Lotacao> findByUnidade(Unidade unidade);
}
