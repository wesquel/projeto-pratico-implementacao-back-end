package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ServidorTemporario {

    @Id
    @ManyToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "st_data_admissao", nullable = false)
    private Date dataAdmissao;

    @Column(name = "st_data_demissao")
    private Date dataDemissao;


}
