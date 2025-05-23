package tn.itbs.systemeGestionReclamation.beans;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;

@Entity
public class AgentSAV {
    @Id @GeneratedValue
    private Long id;
    private String nom;
    private String competence;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // Getters & Setters


    public AgentSAV() {
    }

    public AgentSAV(Long id, String nom, String competence, User user) {
        this.id = id;
        this.nom = nom;
        this.competence = competence;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCompetence() {
        return competence;
    }
}
