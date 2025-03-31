package br.com.addson.projetopraticoimplementacaobackend.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // URL do seu servidor MinIO
                .credentials("myaccesskey123", "My$ecureP@ssw0rd") // Suas credenciais
                .build();
    }
}
