package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario.ServidorTemporarioResponse;
import br.com.addson.projetopraticoimplementacaobackend.services.ServidorTemporarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servidor/temporario")
public class ServidorTemporarioController {
    private final ServidorTemporarioService servidorTemporarioService;

    public ServidorTemporarioController(ServidorTemporarioService servidorTemporarioService) {
        this.servidorTemporarioService = servidorTemporarioService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ServidorTemporarioResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ServidorTemporarioResponse> servidores = servidorTemporarioService.findAll(pageable);
        return ResponseEntity.ok(servidores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            ServidorTemporarioResponse servidorResponse = servidorTemporarioService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(servidorResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@Valid @RequestBody ServidorTemporarioRequest servidorTemporarioRequest) {
        try {
            ServidorTemporarioResponse response = servidorTemporarioService.register(servidorTemporarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@Valid @RequestBody ServidorTemporarioRequest servidorTemporarioRequest) {
        try {
            ServidorTemporarioResponse response = servidorTemporarioService.update(servidorTemporarioRequest);
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
            servidorTemporarioService.delete(id);
        } catch (EntityNotFoundException ignored) {
        }
        return ResponseEntity.noContent().build();
    }
}