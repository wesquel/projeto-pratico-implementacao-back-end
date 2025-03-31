package br.com.addson.projetopraticoimplementacaobackend.repositories;

import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
    List<Lotacao> findByUnidade(Unidade unidade);
}
