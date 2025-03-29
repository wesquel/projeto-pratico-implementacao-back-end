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

}
