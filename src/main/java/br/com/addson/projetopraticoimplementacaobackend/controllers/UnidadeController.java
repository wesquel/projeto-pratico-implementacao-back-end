package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.unidade.UnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.services.UnidadeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/unidade")
@RestController
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            UnidadeResponse unidadeResponse = unidadeService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(unidadeResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<UnidadeResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UnidadeResponse> unidades = unidadeService.findAll(pageable);
        return ResponseEntity.ok(unidades);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@Valid @RequestBody UnidadeRequest unidadeRequest) {
        try {
            UnidadeResponse response = unidadeService.register(unidadeRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@Valid @RequestBody UnidadeRequest unidadeRequest) {
        try {
            UnidadeResponse response = unidadeService.update(unidadeRequest);
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
            unidadeService.delete(id);
        } catch (EntityNotFoundException ignored) {
        }
        return ResponseEntity.noContent().build();
    }
}
