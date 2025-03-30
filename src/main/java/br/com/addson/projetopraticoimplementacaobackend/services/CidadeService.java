package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;
import br.com.addson.projetopraticoimplementacaobackend.repositories.CidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<CidadeResponse> findAll(Pageable pageable) {
        Page<Cidade> cidadesPage = cidadeRepository.findAll(pageable);
        return cidadesPage.getContent().stream()
                .map(CidadeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public CidadeResponse getById(Integer id) {
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cidade não existe!"));
        return CidadeResponse.fromEntity(cidade);
    }

    public Cidade register(CidadeRequest cidadeRequest) {
        return cidadeRepository.findByNomeAndUf(
                cidadeRequest.nome().toUpperCase(), cidadeRequest.uf().toUpperCase()
        ).orElseGet(() -> {
            Cidade novaCidade = cidadeRequest.toEntity();
            novaCidade.setEnderecos(new ArrayList<>());
            return cidadeRepository.save(novaCidade);
        });
    }

    public Cidade update(CidadeRequest cidadeRequest) {
        Cidade cidade = cidadeRepository.findById(cidadeRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("ID de cidade inválido!"));
        cidade.setNome(cidadeRequest.nome());
        cidade.setUf(cidadeRequest.uf());
        return cidadeRepository.save(cidade);
    }

    public void delete(Integer id) {
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Cidade não existe!"));
        cidadeRepository.delete(cidade);
    }
}
