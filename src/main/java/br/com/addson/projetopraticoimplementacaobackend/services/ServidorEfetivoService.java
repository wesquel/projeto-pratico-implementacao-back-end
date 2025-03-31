package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.ServidorEfetivoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServidorEfetivoService{

    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;

    public ServidorEfetivoService(ServidorEfetivoRepository servidorEfetivoRepository, PessoaService pessoaService, PessoaRepository pessoaRepository) {
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.pessoaService = pessoaService;
        this.pessoaRepository = pessoaRepository;
    }

    public List<ServidorEfetivoResponse> findAll(Pageable pageable) {
        Page<ServidorEfetivo> servidorPage = servidorEfetivoRepository.findAll(pageable);
        return servidorPage.getContent().stream()
                .map(ServidorEfetivoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ServidorEfetivoResponse getByMatricula(String matricula) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Servidor Efetivo não existe!"));
        return ServidorEfetivoResponse.fromEntity(servidorEfetivo);
    }

    public ServidorEfetivoResponse register(ServidorEfetivoRequest servidorEfetivoRequest) {

        if (servidorEfetivoRepository.existsByMatricula(servidorEfetivoRequest.matricula())) {
            throw new UserAlreadyExistsException("Essa matricula já está cadastrada!");
        }
        ServidorEfetivo servidorEfetivo = servidorEfetivoRequest.toEntity();

        PessoaResponse pessoaResponse = pessoaService.register(servidorEfetivoRequest.pessoaRequest());
        Pessoa pessoa = pessoaRepository.findById(pessoaResponse.id())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não criada!"));
        servidorEfetivo.setPessoa(pessoa);

        ServidorEfetivo servidorEfetivoSaved = servidorEfetivoRepository.save(servidorEfetivo);
        return ServidorEfetivoResponse.fromEntity(servidorEfetivoSaved);
    }

    public ServidorEfetivoResponse update(ServidorEfetivoRequest servidorEfetivoRequest) {

        if (servidorEfetivoRequest.id() == null) {
            throw new IllegalArgumentException("O ID do Servidor Efetivo não pode ser nulo.");
        }

        pessoaService.update(servidorEfetivoRequest.pessoaRequest());

        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(servidorEfetivoRequest.id())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Servidor Efetivo não encontrado para o ID: " + servidorEfetivoRequest.id()));

        if (servidorEfetivoRepository.existsByMatricula(servidorEfetivoRequest.matricula())) {
            throw new IllegalArgumentException("Já existe um Servidor Efetivo com a matrícula: "
                    + servidorEfetivoRequest.matricula());
        }

        servidorEfetivo.setMatricula(servidorEfetivoRequest.matricula());
        ServidorEfetivo servidorEfetivoSaved = servidorEfetivoRepository.save(servidorEfetivo);
        return ServidorEfetivoResponse.fromEntity(servidorEfetivoSaved);
    }


    public void delete(Integer id) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servidor Efetivo não existe!"));
        servidorEfetivoRepository.delete(servidorEfetivo);
    }
}
