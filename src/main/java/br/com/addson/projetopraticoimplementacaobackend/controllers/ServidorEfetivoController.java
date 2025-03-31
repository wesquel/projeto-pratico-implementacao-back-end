package br.com.addson.projetopraticoimplementacaobackend.controllers;

import br.com.addson.projetopraticoimplementacaobackend.dtos.exception.ResponseException;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoEnderecoFuncionalResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo.ServidorEfetivoUnidadeResponse;
import br.com.addson.projetopraticoimplementacaobackend.services.ServidorEfetivoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servidor/efetivo")
public class ServidorEfetivoController {
    private final ServidorEfetivoService servidorEfetivoService;

    public ServidorEfetivoController(ServidorEfetivoService servidorEfetivoService) {
        this.servidorEfetivoService = servidorEfetivoService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<ServidorEfetivoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ServidorEfetivoResponse> servidores = servidorEfetivoService.findAll(pageable);
        return ResponseEntity.ok(servidores);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<?> getByMatricula(@PathVariable String matricula) {
        try {
            ServidorEfetivoResponse servidorResponse = servidorEfetivoService.getByMatricula(matricula);
            return ResponseEntity.status(HttpStatus.OK).body(servidorResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@Valid @RequestBody ServidorEfetivoRequest servidorEfetivoRequest) {
        try {
            ServidorEfetivoResponse response = servidorEfetivoService.register(servidorEfetivoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@Valid @RequestBody ServidorEfetivoRequest servidorEfetivoRequest) {
        try {
            ServidorEfetivoResponse response = servidorEfetivoService.update(servidorEfetivoRequest);
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
            servidorEfetivoService.delete(id);
        } catch (EntityNotFoundException ignored) {
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unidade/{unid_id}")
    public ResponseEntity<?> findUnidadeById(@PathVariable Integer unid_id){
        try {
            List<ServidorEfetivoUnidadeResponse> servidorEfetivoUnidadeResponses =
                    servidorEfetivoService.findServidoresByUnidade(unid_id);
            return ResponseEntity.status(HttpStatus.FOUND).body(servidorEfetivoUnidadeResponses);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
        }
    }

    @GetMapping("/unidade/endereco")
    public ResponseEntity<?> buscarEnderecoPorNome(
            @RequestParam("nome") String parteNome) {
        try {
            List<ServidorEfetivoEnderecoFuncionalResponse> enderecos = servidorEfetivoService.buscarEnderecoPorNome(parteNome);
            return ResponseEntity.status(HttpStatus.OK).body(enderecos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseException(e.getMessage()));
        }
    }
}
