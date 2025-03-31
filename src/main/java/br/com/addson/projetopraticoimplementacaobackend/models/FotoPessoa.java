package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
public class FotoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foto_pessoa_seq")
    @SequenceGenerator(name = "foto_pessoa_seq", sequenceName = "foto_pessoa_seq", allocationSize = 1)
    @Column(name = "fp_id", nullable = false)
    private Integer id;
    @Column(name = "fp_data", nullable = false)
    private LocalDate data;
    @Column(name = "fp_bucket", length = 50, nullable = false)
    private String bucket;
    @Column(name = "fp_hash", length = 50, nullable = false)
    private String hash;

    @ManyToOne
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    public FotoPessoa() {
    }

    public FotoPessoa(String bucket) {
        this.bucket = bucket;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
