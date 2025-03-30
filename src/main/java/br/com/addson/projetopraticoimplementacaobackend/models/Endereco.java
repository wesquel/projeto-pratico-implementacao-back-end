package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    @SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
    @Column(name = "end_id", nullable = false)
    private Integer id;

    @Column(name = "end_tipo_logradouro", length = 50, nullable = false)
    private String tipoLogradouro;

    @Column(name = "end_logradouro", length = 200, nullable = false)
    private String logradouro;

    @Column(name = "end_numero", nullable = false)
    private Integer numero;

    @Column(name = "end_bairro", length = 100, nullable = false)
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;

    @ManyToMany(mappedBy = "enderecos")
    private Set<Pessoa> pessoas;

    @ManyToMany(mappedBy = "enderecos")
    private Set<Unidade> unidades;

    public Endereco() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Set<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(Set<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public Set<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(Set<Unidade> unidades) {
        this.unidades = unidades;
    }
}
