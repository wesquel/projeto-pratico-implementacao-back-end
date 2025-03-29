package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

@Entity
public class ServidorEfetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servidor_efetivo_seq")
    @SequenceGenerator(name = "servidor_efetivo_seq", sequenceName = "servidor_efetivo_seq", allocationSize = 1)
    @Column(name = "se_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "se_matricula", length = 20, unique = true, nullable = false)
    private String matricula;

    @PrePersist
    public void gerarMatricula() {
        if (this.matricula == null || this.matricula.isBlank()) {
            this.matricula = "SE" + String.format("%06d", id);
        }
    }

    public ServidorEfetivo() {
    }

    public Integer getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
