package br.com.addson.projetopraticoimplementacaobackend.services;

import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.dtos.fotoPessoa.FotoPessoaResponse;
import br.com.addson.projetopraticoimplementacaobackend.dtos.pessoa.PessoaRequest;
import br.com.addson.projetopraticoimplementacaobackend.models.FotoPessoa;
import br.com.addson.projetopraticoimplementacaobackend.models.Pessoa;
import br.com.addson.projetopraticoimplementacaobackend.repositories.FotoPessoaRepository;
import br.com.addson.projetopraticoimplementacaobackend.repositories.PessoaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FotoPessoaService {

    private final String bucketName = "foto-pessoal";
    private final FotoPessoaRepository fotoPessoaRepository;
    private final MinioClient minioClient;
    private final PessoaRepository pessoaRepository;

    public FotoPessoaService(FotoPessoaRepository fotoPessoaRepository, MinioClient minioClient, PessoaRepository pessoaRepository) {
        this.fotoPessoaRepository = fotoPessoaRepository;
        this.minioClient = minioClient;
        this.pessoaRepository = pessoaRepository;
    }

    public List<FotoPessoaResponse> findAll(Pageable pageable) {
        Page<FotoPessoa> fotoPage = fotoPessoaRepository.findAll(pageable);
        return fotoPage.getContent().stream()
                .map(FotoPessoaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public FotoPessoaResponse getById(Integer id) {
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não existe!"));
        return FotoPessoaResponse.fromEntity(fotoPessoa);
    }

    @Transactional
    public FotoPessoaResponse update(FotoPessoaRequest fotoPessoaRequest) {
        if (fotoPessoaRequest.id() == null) {
            throw new IllegalArgumentException("O ID da foto da pessoa não pode ser nulo.");
        }

        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(fotoPessoaRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não encontrada para o ID: " + fotoPessoaRequest.id()));

        Pessoa pessoa = pessoaRepository.findById(fotoPessoaRequest.pessoaId()).orElseThrow(
                () -> new EntityNotFoundException("ID pessoa não encontrada.")
        );

        fotoPessoa.setBucket(fotoPessoaRequest.bucket());
        fotoPessoa.setPessoa(pessoa);
        FotoPessoa fotoPessoaSaved = fotoPessoaRepository.save(fotoPessoa);
        return FotoPessoaResponse.fromEntity(fotoPessoaSaved);
    }

    public void delete(Integer id) {
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto da pessoa não existe!"));
        fotoPessoaRepository.delete(fotoPessoa);
    }

    @Transactional
    public List<FotoPessoaResponse> uploadFotos(Integer pessoaId, List<MultipartFile> files)
            throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new EntityNotFoundException("ID pessoa não encontrada."));

        List<FotoPessoaResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString();
            if (fileName.length() > 50) {
                fileName = fileName.substring(0, 50);
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            FotoPessoa fotoPessoa = new FotoPessoa();
            fotoPessoa.setBucket(bucketName);
            fotoPessoa.setHash(fileName);
            fotoPessoa.setData(LocalDate.now());
            pessoa.getFotos().add(fotoPessoa);
            fotoPessoa.setPessoa(pessoa);
            FotoPessoa fotoPessoaSaved = fotoPessoaRepository.save(fotoPessoa);

            responses.add(FotoPessoaResponse.fromEntity(fotoPessoaSaved));
        }

        return responses;
    }

}
