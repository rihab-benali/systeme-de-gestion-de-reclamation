package tn.itbs.systemeGestionReclamation.beans;

import jakarta.persistence.*;


@Entity
public class Client {
    @Id @GeneratedValue
    private Long id;
    private String nom;
    private String email;
    private String telephone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    // Getters & Setters


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client(Long id, String nom, String email, String telephone, User user) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.user = user;
    }

    public Client() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }
}
