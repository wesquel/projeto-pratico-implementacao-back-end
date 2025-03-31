package br.com.addson.projetopraticoimplementacaobackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servidor_temporario_seq")
    @SequenceGenerator(name = "servidor_temporario_seq", sequenceName = "servidor_temporario_seq", allocationSize = 1)
    @Column(name = "st_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "st_data_admissao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate dataDemissao;

    public ServidorTemporario() {
    }

    public ServidorTemporario(LocalDate dataAdmissao, LocalDate dataDemissao, Pessoa pessoa) {
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
        this.pessoa = pessoa;
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

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }
}
