package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.endereco.EnderecoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.services.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/endereco")
@RestController
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            EnderecoResponse enderecoResponse = enderecoService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(enderecoResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<EnderecoResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        List<EnderecoResponse> enderecos = enderecoService.findAll(pageable);
        return ResponseEntity.ok(enderecos);
    }
}
