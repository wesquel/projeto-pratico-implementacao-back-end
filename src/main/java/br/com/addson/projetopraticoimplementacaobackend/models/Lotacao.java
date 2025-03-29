package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lotacao_seq")
    @SequenceGenerator(name = "lotacao_seq", sequenceName = "lotacao_seq", allocationSize = 1)
    @Column(name = "lot_id", nullable = false)
    private Integer id;

    @Column(name = "lot_data_locacao", nullable = false)
    private Date dataLocacao;

    @Column(name = "lot_data_remocao", length = 100)
    private Date dataRemocao;

    @Column(name = "lot_portaria", length = 100)
    private String portaria;

    @ManyToOne
    @JoinColumn(name = "unid_id", referencedColumnName = "unid_id", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Pessoa pessoa;
}
