package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao.LotacaoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.lotacao.LotacaoResponse;
import br.com.addson.projetopraticoimplementacaobackend.services.LotacaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/lotacao")
public class LotacaoController {
    private final LotacaoService lotacaoService;

    public LotacaoController(LotacaoService lotacaoService) {
        this.lotacaoService = lotacaoService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<LotacaoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<LotacaoResponse> lotacoes = lotacaoService.findAll(pageable);
        return ResponseEntity.ok(lotacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            LotacaoResponse lotacaoResponse = lotacaoService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(lotacaoResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@Valid @RequestBody LotacaoRequest lotacaoRequest) {
        try {
            LotacaoResponse response = lotacaoService.register(lotacaoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@Valid @RequestBody LotacaoRequest lotacaoRequest) {
        try {
            LotacaoResponse response = lotacaoService.update(lotacaoRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            lotacaoService.delete(id);
        } catch (EntityNotFoundException ignored) {
        }
        return ResponseEntity.noContent().build();
    }
}
