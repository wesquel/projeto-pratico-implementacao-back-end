package br.com.addson.projetopraticoimplementacaobackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lotacao_seq")
    @SequenceGenerator(name = "lotacao_seq", sequenceName = "lotacao_seq", allocationSize = 1)
    @Column(name = "lot_id", nullable = false)
    private Integer id;

    @Column(name = "lot_data_locacao", nullable = false)
    private LocalDate dataLocacao;

    @Column(name = "lot_data_remocao", length = 100)
    private LocalDate dataRemocao;

    @Column(name = "lot_portaria", length = 100)
    private String portaria;

    @ManyToOne
    @JoinColumn(name = "unid_id", referencedColumnName = "unid_id", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Pessoa pessoa;

    public Lotacao() {
    }


    public Lotacao(LocalDate dataLotacao, LocalDate dataRemocao, String portaria) {
        this.dataLocacao = dataLotacao;
        this.dataRemocao = dataRemocao;
        this.portaria = portaria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
