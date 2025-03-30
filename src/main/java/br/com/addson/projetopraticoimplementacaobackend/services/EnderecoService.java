package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.cidade.CidadeRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.models.Cidade;
import br.com.addson.projetopraticoimplementacaobackend.models.Endereco;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.repositories.CidadeRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final CidadeService cidadeService;
    private final CidadeRepository cidadeRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, CidadeService cidadeService, CidadeRepository cidadeRepository1) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeService = cidadeService;
        this.cidadeRepository = cidadeRepository1;
    }

    public List<EnderecoResponse> findAll(Pageable pageable) {
        Page<Endereco> enderecosPage = enderecoRepository.findAll(pageable);
        return enderecosPage.getContent().stream()
                .map(EnderecoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public EnderecoResponse getById(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Endereço não existe!"));
        return EnderecoResponse.fromEntity(endereco);
    }

    public Endereco register(EnderecoRequest enderecoRequest) {
        Endereco endereco = enderecoRequest.toEntity();
        endereco.setPessoas(new HashSet<>());
        return enderecoRepository.save(endereco);
    }

    public Endereco update(EnderecoRequest enderecoRequest) {
        Endereco endereco = enderecoRepository.findById(enderecoRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("ID de endereço inválido!" + enderecoRequest.id()));

        endereco.setTipoLogradouro(enderecoRequest.tipoLogradouro());
        endereco.setLogradouro(enderecoRequest.logradouro());
        endereco.setNumero(enderecoRequest.numero());
        endereco.setBairro(enderecoRequest.bairro());

        Cidade cidade = (enderecoRequest.cidade().id() == null)
                ? cidadeService.register(enderecoRequest.cidade())
                : cidadeService.update(enderecoRequest.cidade());

        if (enderecoRequest.cidade().id() == null) {
            cidade.getEnderecos().add(endereco);
        }

        endereco.setCidade(cidade);
        return enderecoRepository.save(endereco);
    }

    public void delete(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Endereço não existe!"));
        enderecoRepository.delete(endereco);
    }

}
