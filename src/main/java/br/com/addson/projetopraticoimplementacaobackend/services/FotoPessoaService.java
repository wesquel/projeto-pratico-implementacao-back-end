package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.repositories.FotoPessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FotoPessoaService {

    private final FotoPessoaRepository fotoPessoaRepository;

    private final PessoaRepository pessoaRepository;

    public FotoPessoaService(FotoPessoaRepository fotoPessoaRepository, PessoaRepository pessoaRepository) {
        this.fotoPessoaRepository = fotoPessoaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<FotoPessoaResponse> findAll(Pageable pageable) {
        Page<FotoPessoa> fotoPage = fotoPessoaRepository.findAll(pageable);
        return fotoPage.getContent().stream()
                .map(FotoPessoaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public FotoPessoaResponse getById(Integer id) {
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não existe!"));
        return FotoPessoaResponse.fromEntity(fotoPessoa);
    }

    @Transactional
    public FotoPessoaResponse register(FotoPessoaRequest fotoPessoaRequest) {
        FotoPessoa fotoPessoa = fotoPessoaRequest.toEntity();
        Pessoa pessoa = pessoaRepository.findById(fotoPessoaRequest.pessoaId()).orElseThrow(
                () -> new EntityNotFoundException("ID pessoa não encontrada.")
        );

        fotoPessoa.setPessoa(pessoa);
        FotoPessoa fotoPessoaSaved = fotoPessoaRepository.save(fotoPessoa);
        return FotoPessoaResponse.fromEntity(fotoPessoaSaved);
    }

    @Transactional
    public FotoPessoaResponse update(FotoPessoaRequest fotoPessoaRequest) {
        if (fotoPessoaRequest.id() == null) {
            throw new IllegalArgumentException("O ID da foto da pessoa não pode ser nulo.");
        }

        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(fotoPessoaRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não encontrada para o ID: " + fotoPessoaRequest.id()));

        Pessoa pessoa = pessoaRepository.findById(fotoPessoaRequest.pessoaId()).orElseThrow(
                () -> new EntityNotFoundException("ID pessoa não encontrada.")
        );

        fotoPessoa.setData(fotoPessoaRequest.data());
        fotoPessoa.setHash(fotoPessoaRequest.hash());
        fotoPessoa.setBucket(fotoPessoaRequest.bucket());
        fotoPessoa.setPessoa(pessoa);
        FotoPessoa fotoPessoaSaved = fotoPessoaRepository.save(fotoPessoa);
        return FotoPessoaResponse.fromEntity(fotoPessoaSaved);
    }

    public void delete(Integer id) {
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não existe!"));
        fotoPessoaRepository.delete(fotoPessoa);
    }
}
