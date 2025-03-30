package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_seq")
    @SequenceGenerator(name = "cidade_seq", sequenceName = "cidade_seq", allocationSize = 1)
    @Column(name = "cid_id", nullable = false)
    private Integer id;

    @Column(name = "cid_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "cid_uf", length = 2, nullable = false)
    private String uf;

    @OneToMany(mappedBy = "cidade")
    private List<Endereco> enderecos;

    public Cidade() {
    }

    public Cidade(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
