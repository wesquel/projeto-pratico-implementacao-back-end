package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaUpdateRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.Unidade;
import br.com.addson.projetopraticoimplementacaobackend.repositories.EnderecoRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.UnidadeRepository;
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
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final EnderecoService enderecoService;
    private final EnderecoRepository enderecoRepository;

    public UnidadeService(UnidadeRepository unidadeRepository, EnderecoService enderecoService, EnderecoRepository enderecoRepository) {
        this.unidadeRepository = unidadeRepository;
        this.enderecoService = enderecoService;
        this.enderecoRepository = enderecoRepository;
    }

    public List<UnidadeResponse> findAll(Pageable pageable) {
        Page<Unidade> unidadesPage = unidadeRepository.findAll(pageable);
        return unidadesPage.getContent().stream()
                .map(UnidadeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UnidadeResponse getById(Integer id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não existe!"));
        return UnidadeResponse.fromEntity(unidade);
    }

    public UnidadeResponse update(UnidadeRequest unidadeRequest){
        Unidade unidade = unidadeRepository.findById(unidadeRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não existe!"));

        unidade.setNome(unidadeRequest.nome());
        unidade.setSigla(unidadeRequest.sigla());

        Set<Endereco> enderecosAtualizados = unidadeRequest.enderecos().stream()
                .map(enderecoUpdateRequest -> {
                    if (enderecoUpdateRequest.id() != null) {
                        return enderecoService.update(enderecoUpdateRequest);
                    } else {
                        Endereco endereco = enderecoService.register(enderecoUpdateRequest);
                        endereco.getUnidades().add(unidade);
                        return enderecoRepository.save(endereco);
                    }
                })
                .collect(Collectors.toSet());

        unidade.getEnderecos().removeIf(endereco -> !enderecosAtualizados.contains(endereco));
        unidade.getEnderecos().addAll(enderecosAtualizados);

        Unidade unidadeSaved = unidadeRepository.save(unidade);
        return UnidadeResponse.fromEntity(unidadeSaved);
    }

    public UnidadeResponse register(UnidadeRequest unidadeRequest) {
        Unidade unidade = unidadeRequest.toEntity();

        Set<Endereco> enderecosSalvos = new HashSet<>();
        for (EnderecoRequest enderecoRequest : unidadeRequest.enderecos()) {
            Endereco enderecoSalvo = enderecoService.register(enderecoRequest);
            enderecosSalvos.add(enderecoSalvo);
        }
        unidade.setEnderecos(enderecosSalvos);

        Unidade unidadeSaved = unidadeRepository.save(unidade);
        return UnidadeResponse.fromEntity(unidadeSaved);
    }

    public void delete(Integer id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não existe!"));
        unidadeRepository.delete(unidade);
    }


}
