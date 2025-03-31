package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.services.FotoPessoaService;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/pessoa/foto")
public class FotoPessoaController {
    private final FotoPessoaService fotoPessoaService;

    public FotoPessoaController(FotoPessoaService fotoPessoaService) {
        this.fotoPessoaService = fotoPessoaService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<FotoPessoaResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<FotoPessoaResponse> fotos = fotoPessoaService.findAll(pageable);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            FotoPessoaResponse fotoResponse = fotoPessoaService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(fotoResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFotos(@RequestParam("files") List<MultipartFile> files,
                                         @RequestParam("pessoaId") Integer pessoaId) {
        try {
            List<FotoPessoaResponse> responses = fotoPessoaService.uploadFotos(pessoaId, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(responses);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseException("Erro ao fazer upload das fotos: " + e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@Valid @RequestBody FotoPessoaRequest fotoPessoaRequest) {
        try {
            FotoPessoaResponse response = fotoPessoaService.update(fotoPessoaRequest);
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
            fotoPessoaService.delete(id);
        } catch (EntityNotFoundException ignored) {
        }
        return ResponseEntity.noContent().build();
    }
}
