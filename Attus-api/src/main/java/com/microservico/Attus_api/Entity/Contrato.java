package com.microservico.Attus_api.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroContrato;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Parte> partes = new ArrayList<>();

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Evento> eventos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusContratoEnum status;

    private LocalDate dataCriacao;
    private String descricao;

    public Long getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(Long numeroContrato) {

        this.numeroContrato = numeroContrato;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Parte> getPartes() {
        return partes;
    }

    public void setPartes(List<Parte> partes) {
        this.partes = partes;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public StatusContratoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusContratoEnum status) {
        this.status = status;
    }

//    public void addParte(Parte parte) {
//        partes.add(parte);
//        parte.setContrato(this);
//    }
//
//    public void removeParte(Parte parte) {
//        partes.remove(parte);
//        parte.setContrato(null);
//    }

    public Contrato() {
    }

    public Contrato(Long numeroContrato, List<Parte> partes, List<Evento> eventos,
                    StatusContratoEnum status, LocalDate dataCriacao, String descricao) {
        this.numeroContrato = numeroContrato;
        this.partes = partes != null ? partes : new ArrayList<>();
        this.eventos = eventos != null ? eventos : new ArrayList<>();
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.descricao = descricao;
    }

    public void setId(long l) {
    }
}
