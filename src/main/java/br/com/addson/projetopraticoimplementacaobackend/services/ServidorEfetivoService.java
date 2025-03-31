package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResumo;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoEnderecoFuncionalResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoUnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.models.*;
import br.com.addson.projetopraticoimplementacaobackend.repositories.LotacaoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.ServidorEfetivoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServidorEfetivoService{

    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;
    private final LotacaoRepository lotacaoRepository;
    private final UnidadeRepository unidadeRepository;

    public ServidorEfetivoService(ServidorEfetivoRepository servidorEfetivoRepository, PessoaService pessoaService, PessoaRepository pessoaRepository, LotacaoRepository lotacaoRepository, UnidadeRepository unidadeRepository) {
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.pessoaService = pessoaService;
        this.pessoaRepository = pessoaRepository;
        this.lotacaoRepository = lotacaoRepository;
        this.unidadeRepository = unidadeRepository;
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
        pessoa.getServidoresEfetivos().add(servidorEfetivo);
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

        if (!servidorEfetivoRequest.matricula().equals(servidorEfetivo.getMatricula())
                && servidorEfetivoRepository.existsByMatricula(servidorEfetivoRequest.matricula())) {
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

    public List<ServidorEfetivoUnidadeResponse> findServidoresByUnidade(Integer unid_id) {
        Unidade unidade = unidadeRepository.findById(unid_id).orElseThrow(
                () -> new EntityNotFoundException("ID de unidade não existe!")
        );
        List<Lotacao> lotacoes = lotacaoRepository.findByUnidade(unidade);

        return lotacoes.stream()
                .flatMap(lotacao -> {
                    Set<ServidorEfetivo> servidores = lotacao.getPessoa().getServidoresEfetivos();
                    return servidores.stream().map(servidor -> new ServidorEfetivoUnidadeResponse(
                            servidor.getId(),
                            servidor.getPessoa().getNome(),
                            servidor.getPessoa().getIdade(),
                            unidade.getNome(),
                            servidor.getPessoa().getFotos().stream()
                                    .map(FotoPessoaResumo::fromEntity)
                                    .collect(Collectors.toSet())
                    ));
                })
                .collect(Collectors.toList());

    }

    public List<ServidorEfetivoEnderecoFuncionalResponse> buscarEnderecoPorNome(String parteNome) {
        List<ServidorEfetivo> servidores = servidorEfetivoRepository.findByNomeContaining(parteNome);
        List<ServidorEfetivoEnderecoFuncionalResponse> enderecos = new ArrayList<>();
        for (ServidorEfetivo servidor : servidores) {
            Set<Lotacao> lotacaoSet = servidor.getPessoa().getLotacoes();
            for (Lotacao lotacao : lotacaoSet) {
                Unidade unidade = lotacao.getUnidade();
                Set<Endereco> enderecosUnidade = unidade.getEnderecos();
                for (Endereco endereco : enderecosUnidade) {
                    enderecos.add(new ServidorEfetivoEnderecoFuncionalResponse(
                            servidor.getPessoa().getId(),
                            servidor.getPessoa().getNome(),
                            EnderecoResponse.fromEntity(endereco)
                    ));
                }
            }
        }
        return enderecos;
    }
}
