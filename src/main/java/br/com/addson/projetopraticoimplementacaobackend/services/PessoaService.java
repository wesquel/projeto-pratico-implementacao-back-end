package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoRequest;
import br.com.addson.projetopraticoimplementacaobackend.enums.SexoEnum;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;
import br.com.addson.projetopraticoimplementacaobackend.repositories.EnderecoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.ServidorEfetivoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;

    private final ServidorEfetivoRepository servidorEfetivoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository, EnderecoService enderecoService, ServidorEfetivoRepository servidorEfetivoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoService = enderecoService;
        this.servidorEfetivoRepository = servidorEfetivoRepository;
    }

    public List<PessoaResponse> findAll(Pageable pageable) {
        Page<Pessoa> pessoasPage = pessoaRepository.findAll(pageable);
        return pessoasPage.getContent().stream()
                .map(PessoaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public PessoaResponse register(PessoaRequest pessoaRequest) {
        if ((pessoaRequest.nomeMae() == null || pessoaRequest.nomeMae().isBlank()) &&
                (pessoaRequest.nomePai() == null || pessoaRequest.nomePai().isBlank())) {
            throw new IllegalArgumentException(
                    "Pelo menos um dos campos 'nome do pai' ou 'nome da mãe' deve ser preenchido.");
        }
        SexoEnum sexoEnum = SexoEnum.fromInput(pessoaRequest.sexo());

        Pessoa pessoa = pessoaRequest.toEntity();
        pessoa.setSexo(sexoEnum.getValor());

        Set<Endereco> enderecosSalvos = new HashSet<>();
        for (EnderecoRequest enderecoRequest : pessoaRequest.enderecos()) {
            Endereco enderecoSalvo = enderecoService.register(enderecoRequest);
            enderecosSalvos.add(enderecoSalvo);
        }
        pessoa.setEnderecos(enderecosSalvos);

        Pessoa pessoaSaved = pessoaRepository.save(pessoa);

        Set<ServidorEfetivo> servidorEfetivoSet = new HashSet<>();
        for (String matricula : pessoaRequest.servidoresEfetivos()){
            if (servidorEfetivoRepository.existsByMatricula(matricula)) {
                throw new IllegalArgumentException("Matrícula já existente: " + matricula);
            }
            ServidorEfetivo servidorEfetivo = new ServidorEfetivo(matricula, pessoaSaved);
            servidorEfetivoSet.add(servidorEfetivoRepository.save(servidorEfetivo));
        }
        pessoaSaved.setServidoresEfetivos(servidorEfetivoSet);


        return PessoaResponse.fromEntity(pessoaSaved);
    }

    @Transactional
    public PessoaResponse update(PessoaRequest pessoaUpdateRequest){
        validateRequest(pessoaUpdateRequest);
        SexoEnum sexoEnum = SexoEnum.fromInput(pessoaUpdateRequest.sexo());
        Pessoa pessoa = pessoaRepository.findById(pessoaUpdateRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não existe!"));

        pessoa.setNome(pessoaUpdateRequest.nome());
        pessoa.setSexo(sexoEnum.getValor());
        pessoa.setDataNascimento(pessoaUpdateRequest.dataNascimento());
        pessoa.setNomeMae(pessoaUpdateRequest.nomeMae());
        pessoa.setNomePai(pessoaUpdateRequest.nomePai());

        Set<Endereco> enderecosAtualizados = updateEnderecos(pessoa, pessoaUpdateRequest.enderecos());
        pessoa.getEnderecos().removeIf(endereco -> !enderecosAtualizados.contains(endereco));
        pessoa.getEnderecos().addAll(enderecosAtualizados);

        Set<ServidorEfetivo> servidorEfetivoSet = updateServidoresEfetivos(pessoa,
                pessoaUpdateRequest.servidoresEfetivos());
        pessoa.setServidoresEfetivos(servidorEfetivoSet);

        Pessoa pessoaSaved = pessoaRepository.save(pessoa);
        return PessoaResponse.fromEntity(pessoaSaved);
    }

    public void delete(Integer id){
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Pessoa não existe!"));
        pessoaRepository.delete(pessoa);
    }

    public PessoaResponse getById(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Pessoa não existe!"));
        return PessoaResponse.fromEntity(pessoa);
    }

    private void validateRequest(PessoaRequest pessoaUpdateRequest) {
        if (pessoaUpdateRequest.id() == null) {
            throw new IllegalArgumentException("O ID de Pessoa não pode ser nulo.");
        }

        if ((pessoaUpdateRequest.nomeMae() == null || pessoaUpdateRequest.nomeMae().isBlank()) &&
                (pessoaUpdateRequest.nomePai() == null || pessoaUpdateRequest.nomePai().isBlank())) {
            throw new IllegalArgumentException("Pelo menos um dos campos 'nome do pai' ou 'nome da mãe' deve ser preenchido.");
        }
    }

    private Set<Endereco> updateEnderecos(Pessoa pessoa, Set<EnderecoRequest> enderecosRequests) {
        return enderecosRequests.stream()
                .map(enderecoUpdateRequest -> {
                    if (enderecoUpdateRequest.id() != null) {
                        return enderecoService.update(enderecoUpdateRequest);
                    } else {
                        Endereco endereco = enderecoService.register(enderecoUpdateRequest);
                        endereco.getPessoas().add(pessoa);
                        return enderecoRepository.save(endereco);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Set<ServidorEfetivo> updateServidoresEfetivos(Pessoa pessoa, Set<String> matriculas) {
        Set<ServidorEfetivo> servidorEfetivoSet = new HashSet<>();
        for (String matricula : matriculas) {
            if (servidorEfetivoRepository.existsByMatricula(matricula)) {
                throw new IllegalArgumentException("Matrícula já existente: " + matricula);
            }

            ServidorEfetivo servidorEfetivo = pessoa.getServidoresEfetivos().stream()
                    .filter(se -> se.getMatricula().equals(matricula))
                    .findFirst()
                    .orElse(new ServidorEfetivo(matricula, pessoa));

            servidorEfetivo.setMatricula(matricula);
            servidorEfetivoSet.add(servidorEfetivoRepository.save(servidorEfetivo));
        }
        return servidorEfetivoSet;
    }



}
