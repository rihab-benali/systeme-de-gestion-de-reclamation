package tn.itbs.systemeGestionReclamation.beans;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reclamation {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Client client;

    private String produit;
    private String statut;
    private String description;
    private LocalDate date;
    private Integer note;

    public Reclamation() {
    }

    public Reclamation(Long id, Client client, String produit, String statut, String description, LocalDate date, Integer note, AgentSAV agent) {
        this.id = id;
        this.client = client;
        this.produit = produit;
        this.statut = statut;
        this.description = description;
        this.date = date;
        this.note = note;
        this.agent = agent;
    }

    public void setAgent(AgentSAV agent) {
        this.agent = agent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    @ManyToOne
    private AgentSAV agent; // Optional: link reclamation to agent

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getProduit() {
        return produit;
    }

    public String getStatut() {
        return statut;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getNote() {
        return note;
    }

    public AgentSAV getAgent() {
        return agent;
    }

// Getters & Setters
}

