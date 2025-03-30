package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaUpdateRequest;
import br.com.addson.projetopraticoimplementacaobackend.exceptions.auth.UserAlreadyExistsException;
import br.com.addson.projetopraticoimplementacaobackend.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pessoa")
@RestController
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<PessoaResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PessoaResponse> pessoas = pessoaService.findAll(pageable);
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@Valid @RequestBody PessoaRequest pessoaRequest) {
        try {
            PessoaResponse response = pessoaService.register(pessoaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> register(@Valid @RequestBody PessoaUpdateRequest pessoaUpdateRequest) {
        try {
            PessoaResponse response = pessoaService.update(pessoaUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            pessoaService.delete(id);
        }catch (UsernameNotFoundException ignored){
        }
        return ResponseEntity.noContent().build();
    }

}
