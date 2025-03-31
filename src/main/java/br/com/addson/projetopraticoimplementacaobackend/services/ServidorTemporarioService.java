package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorTemporario;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.ServidorTemporarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;

    public ServidorTemporarioService(PessoaService pessoaService, ServidorTemporarioRepository servidorTemporarioRepository, PessoaRepository pessoaRepository) {
        this.pessoaService = pessoaService;
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<ServidorTemporarioResponse> findAll(Pageable pageable) {
        Page<ServidorTemporario> servidorPage = servidorTemporarioRepository.findAll(pageable);
        return servidorPage.getContent().stream()
                .map(ServidorTemporarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ServidorTemporarioResponse getById(Integer id) {
        ServidorTemporario servidorTemporario = servidorTemporarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servidor Temporário não existe!"));
        return ServidorTemporarioResponse.fromEntity(servidorTemporario);
    }

    @Transactional
    public ServidorTemporarioResponse register(ServidorTemporarioRequest servidorTemporarioRequest) {
        ServidorTemporario servidorTemporario = servidorTemporarioRequest.toEntity();

        if (servidorTemporarioRequest.pessoaRequest() != null) {
            PessoaResponse pessoaResponse = pessoaService.register(servidorTemporarioRequest.pessoaRequest());
            Pessoa pessoa = pessoaRepository.findById(pessoaResponse.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tentativa de criar pessoa falhou."));
            servidorTemporario.setPessoa(pessoa);
        }

        ServidorTemporario servidorTemporarioSaved = servidorTemporarioRepository.save(servidorTemporario);
        return ServidorTemporarioResponse.fromEntity(servidorTemporarioSaved);
    }

    @Transactional
    public ServidorTemporarioResponse update(ServidorTemporarioRequest servidorTemporarioRequest) {
        if (servidorTemporarioRequest.id() == null) {
            throw new IllegalArgumentException("O ID do Servidor Temporário não pode ser nulo.");
        }

        PessoaResponse pessoaResponse = pessoaService.update(servidorTemporarioRequest.pessoaRequest());
        Pessoa pessoa = pessoaRepository.findById(pessoaResponse.id()).orElseThrow(() ->
                new EntityNotFoundException("Pessoa não encontrada!"));

        ServidorTemporario servidorTemporario = servidorTemporarioRepository.findById(servidorTemporarioRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Servidor Temporário não encontrado para o ID: " + servidorTemporarioRequest.id()));

        servidorTemporario.setDataAdmissao(servidorTemporarioRequest.dataAdmissao());
        servidorTemporario.setDataDemissao(servidorTemporarioRequest.dataDemissao());
        pessoa.getServidoresTemporarios().add(servidorTemporario);
        servidorTemporario.setPessoa(pessoa);

        ServidorTemporario servidorTemporarioSaved = servidorTemporarioRepository.save(servidorTemporario);
        return ServidorTemporarioResponse.fromEntity(servidorTemporarioSaved);
    }

    public void delete(Integer id) {
        ServidorTemporario servidorTemporario = servidorTemporarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servidor Temporário não existe!"));
        servidorTemporarioRepository.delete(servidorTemporario);
    }
}
