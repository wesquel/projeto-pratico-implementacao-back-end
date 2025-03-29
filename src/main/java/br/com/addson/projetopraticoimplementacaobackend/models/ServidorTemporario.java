package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

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
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;

    @Column(name = "st_data_demissao")
    @Temporal(TemporalType.DATE)
    private Date dataDemissao;

    public ServidorTemporario() {
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

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }
}
