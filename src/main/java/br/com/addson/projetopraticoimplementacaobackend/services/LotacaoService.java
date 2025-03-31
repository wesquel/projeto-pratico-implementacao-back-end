package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao.LotacaoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao.LotacaoResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Lotacao;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import br.com.addson.projetopraticoimplementacaobackend.repositories.LotacaoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotacaoService {

    private final UnidadeRepository unidadeRepository;
    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;

    @Autowired
    public LotacaoService(LotacaoRepository lotacaoRepository, UnidadeRepository unidadeRepository, PessoaRepository pessoaRepository) {
        this.lotacaoRepository = lotacaoRepository;
        this.unidadeRepository = unidadeRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<LotacaoResponse> findAll(Pageable pageable) {
        Page<Lotacao> lotacaoPage = lotacaoRepository.findAll(pageable);
        return lotacaoPage.getContent().stream()
                .map(LotacaoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public LotacaoResponse getById(Integer id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lotação não existe!"));
        return LotacaoResponse.fromEntity(lotacao);
    }

    @Transactional
    public LotacaoResponse register(LotacaoRequest lotacaoRequest) {
        Lotacao lotacao = lotacaoRequest.toEntity();

        Unidade unidade = unidadeRepository.findById(lotacaoRequest.unidId()).orElseThrow(
                () -> new EntityNotFoundException("Unidade ID não encontrado: " + lotacaoRequest.unidId()));
        Pessoa pessoa = pessoaRepository.findById(lotacaoRequest.pessoaId()).orElseThrow(
                () -> new EntityNotFoundException("Pessoa ID não encontrado:" + lotacaoRequest.pessoaId()));

        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        Lotacao lotacaoSaved = lotacaoRepository.save(lotacao);

        return LotacaoResponse.fromEntity(lotacaoSaved);
    }

    @Transactional
    public LotacaoResponse update(LotacaoRequest lotacaoRequest) {
        if (lotacaoRequest.id() == null) {
            throw new IllegalArgumentException("O ID da Lotação não pode ser nulo.");
        }

        Lotacao lotacao = lotacaoRepository.findById(lotacaoRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Lotação não encontrada para o ID: " + lotacaoRequest.id()));

        Unidade unidade = unidadeRepository.findById(lotacaoRequest.unidId()).orElseThrow(
                () -> new EntityNotFoundException("Unidade ID não encontrado: " + lotacaoRequest.unidId()));
        Pessoa pessoa = pessoaRepository.findById(lotacaoRequest.pessoaId()).orElseThrow(
                () -> new EntityNotFoundException("Pessoa ID não encontrado:" + lotacaoRequest.pessoaId()));

        lotacao.setDataLocacao(lotacaoRequest.dataLotacao());
        lotacao.setDataRemocao(lotacaoRequest.dataRemocao());
        lotacao.setPortaria(lotacaoRequest.portaria());
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);


        Lotacao lotacaoSaved = lotacaoRepository.save(lotacao);
        return LotacaoResponse.fromEntity(lotacaoSaved);
    }

    public void delete(Integer id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lotação não existe!"));
        lotacaoRepository.delete(lotacao);
    }
}
