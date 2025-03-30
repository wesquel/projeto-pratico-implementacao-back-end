package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaUpdateRequest;
import br.com.addson.projetopraticoimplementacaobackend.enums.SexoEnum;
import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.repositories.EnderecoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository, EnderecoService enderecoService) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoService = enderecoService;
    }

    public List<PessoaResponse> findAll(Pageable pageable) {
        Page<Pessoa> pessoasPage = pessoaRepository.findAll(pageable);
        return pessoasPage.getContent().stream()
                .map(PessoaResponse::fromEntity)
                .collect(Collectors.toList());
    }

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
        return PessoaResponse.fromEntity(pessoaSaved);
    }

    public PessoaResponse update(PessoaUpdateRequest pessoaUpdateRequest){

        if ((pessoaUpdateRequest.nomeMae() == null || pessoaUpdateRequest.nomeMae().isBlank()) &&
                (pessoaUpdateRequest.nomePai() == null || pessoaUpdateRequest.nomePai().isBlank())) {
            throw new IllegalArgumentException(
                    "Pelo menos um dos campos 'nome do pai' ou 'nome da mãe' deve ser preenchido.");
        }

        SexoEnum sexoEnum = SexoEnum.fromInput(pessoaUpdateRequest.sexo());

        Pessoa pessoa = pessoaRepository.findById(pessoaUpdateRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não existe!"));

        pessoa.setNome(pessoaUpdateRequest.nome());
        pessoa.setSexo(sexoEnum.getValor());
        pessoa.setDataNascimento(pessoaUpdateRequest.dataNascimento());
        pessoa.setNomeMae(pessoaUpdateRequest.nomeMae());
        pessoa.setNomePai(pessoaUpdateRequest.nomePai());

        Set<Endereco> enderecosAtualizados = pessoaUpdateRequest.enderecos().stream()
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

        pessoa.getEnderecos().removeIf(endereco -> !enderecosAtualizados.contains(endereco));
        pessoa.getEnderecos().addAll(enderecosAtualizados);

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



}
