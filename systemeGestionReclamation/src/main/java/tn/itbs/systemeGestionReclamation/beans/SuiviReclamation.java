package tn.itbs.systemeGestionReclamation.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;  // <-- import this
import java.time.LocalDate;

@Entity
public class SuiviReclamation {
    @Id @GeneratedValue
    private Long id;

    private String message;
    private String action;
    private LocalDate date;

    @ManyToOne
    private Reclamation reclamation;

    @ManyToOne
    private AgentSAV employe;

    // Getters & Setters

    public SuiviReclamation() {
    }

    public SuiviReclamation(Long id, String message, String action, LocalDate date, Reclamation reclamation, AgentSAV employe) {
        this.id = id;
        this.message = message;
        this.action = action;
        this.date = date;
        this.reclamation = reclamation;
        this.employe = employe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public AgentSAV getEmploye() {
        return employe;
    }

    public void setEmploye(AgentSAV employe) {
        this.employe = employe;
    }
}
