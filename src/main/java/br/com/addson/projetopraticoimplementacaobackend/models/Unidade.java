package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_seq")
    @SequenceGenerator(name = "unidade_seq", sequenceName = "unidade_seq", allocationSize = 1)
    @Column(name = "unid_id", nullable = false)
    private Integer id;

    @Column(name = "unid_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "unid_sigla", length = 20, nullable = false)
    private String sigla;

    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "unid_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "unidade")
    private Set<Lotacao> lotacoes;

    public Unidade() {
    }

    public Unidade(String nome, String sigla, Set<Endereco> enderecos) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.enderecos = enderecos;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<Lotacao> getLotacoes() {
        return lotacoes;
    }

    public void setLotacoes(Set<Lotacao> lotacoes) {
        this.lotacoes = lotacoes;
    }
}
