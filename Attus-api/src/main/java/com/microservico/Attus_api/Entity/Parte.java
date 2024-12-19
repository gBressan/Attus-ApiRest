package com.microservico.Attus_api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="Parte")
public class Parte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private ParteEnum tipo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "contrato_id", nullable = true)
    private Contrato contrato;

    private Long CpfOuCnpj;
    private String nome;
    private String email;
    private String telefone;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getCpfOuCnpj() {
        return CpfOuCnpj;
    }

    public void setCpfOuCnpj(Long cpfOuCnpj) {
        CpfOuCnpj = cpfOuCnpj;
    }

    public ParteEnum getTipo() {
        return tipo;
    }

    public void setTipo(ParteEnum tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public void setContratoId(Long contratoId) {
        this.contrato = new Contrato();
        this.contrato.setNumeroContrato(contratoId);
    }
}

