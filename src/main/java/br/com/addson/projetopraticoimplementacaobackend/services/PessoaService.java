package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaUpdateRequest;
import br.com.addson.projetopraticoimplementacaobackend.enums.SexoEnum;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
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

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequest.nome());
        pessoa.setDataNascimento(pessoaRequest.dataNascimento());
        pessoa.setSexo(sexoEnum.getValor());
        pessoa.setNomeMae(pessoaRequest.nomeMae());
        pessoa.setNomePai(pessoaRequest.nomePai());
        Pessoa pessoaSaved = pessoaRepository.save(pessoa);
        return PessoaResponse.fromEntity(pessoaSaved);
    }

    public  PessoaResponse update(PessoaUpdateRequest pessoaUpdateRequest){

        if ((pessoaUpdateRequest.nomeMae() == null || pessoaUpdateRequest.nomeMae().isBlank()) &&
                (pessoaUpdateRequest.nomePai() == null || pessoaUpdateRequest.nomePai().isBlank())) {
            throw new IllegalArgumentException(
                    "Pelo menos um dos campos 'nome do pai' ou 'nome da mãe' deve ser preenchido.");
        }

        SexoEnum sexoEnum = SexoEnum.fromInput(pessoaUpdateRequest.sexo());

        Pessoa pessoa = pessoaRepository.findById(pessoaUpdateRequest.id())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe!"));

        pessoa.setNome(pessoaUpdateRequest.nome());
        pessoa.setSexo(sexoEnum.getValor());
        pessoa.setDataNascimento(pessoaUpdateRequest.dataNascimento());
        pessoa.setNomeMae(pessoaUpdateRequest.nomeMae());
        pessoa.setNomePai(pessoaUpdateRequest.nomePai());
        Pessoa pessoaSaved = pessoaRepository.save(pessoa);
        return PessoaResponse.fromEntity(pessoaSaved);
    }

    public void delete(Integer id){
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe!"));
        pessoaRepository.delete(pessoa);
    }

}
