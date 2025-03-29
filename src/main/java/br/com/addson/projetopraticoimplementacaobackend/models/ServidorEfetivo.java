package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

@Entity
public class ServidorEfetivo {
    @Id
    @ManyToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "se_matricula", nullable = false)
    private String matricula;
}
