package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
    @SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1)
    @Column(name = "pes_id", nullable = false)
    private Integer id;
    @Column(name = "pes_nome", length = 200, nullable = false)
    private String nome;
    @Column(name = "pes_data_nascimento", nullable = false)
    private LocalDate dataNascimento;
    @Column(name = "pes_sexo", length = 9, nullable = false)
    private String sexo;
    @Column(name = "pes_mae", length = 200, nullable = false)
    private String nomeMae;
    @Column(name = "pes_pai", length = 200)
    private String nomePai;

    @ManyToMany
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "pes_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "pessoa")
    private Set<FotoPessoa> fotos;

    @OneToMany(mappedBy = "pessoa")
    private Set<ServidorTemporario> servidoresTemporarios;

    @OneToMany(mappedBy = "pessoa")
    private Set<ServidorEfetivo> servidoresEfetivos;

    @OneToMany(mappedBy = "pessoa")
    private Set<Lotacao> lotacoes;

    public Pessoa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
