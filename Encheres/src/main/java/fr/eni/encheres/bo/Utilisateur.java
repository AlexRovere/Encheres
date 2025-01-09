package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {
    private int noUtilisateur;
    @NotEmpty(message = "Le pseudo est obligatoire.")
    private String pseudo;
    @Size(max = 15, message = "Le mot de passe doit contenir au maximum 15 caractères.")
    @NotEmpty(message = "Le nom est obligatoire.")
    private String nom;
    @Size(max = 15, message = "Le mot de passe doit contenir au maximum 15 caractères.")
    @NotEmpty(message = "Le prénom est obligatoire.")
    private String prenom;
    @NotEmpty(message = "L'email' est obligatoire.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "L'adresse email n'est pas valide."
    )
    private String email;
    @Pattern(
            regexp = "^$|^[0-9]{10}$",
            message = "Le numéro de téléphone doit contenir exactement 10 chiffres."
    )
    private String telephone;
    @Size(max = 30, message = "La rue doit contenir au maximum 30 caractères.")
    @NotEmpty(message = "La rue est obligatoire.")
    private String rue;
    @NotEmpty(message = "Le code postal est obligatoire.")
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "Le code postal doit contenir exactement 5 chiffres."
    )
    private String codePostal;
    @Size(max = 20, message = "La ville doit contenir au maximum 20 caractères.")
    @NotEmpty(message = "La ville est obligatoire.")
    private String ville;
    @NotEmpty(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial."
    )
    private String motDePasse;
    private int credit;
    private boolean administrateur;

    public Utilisateur() {
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "noUtilisateur=" + noUtilisateur +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", administrateur=" + administrateur +
                '}';
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }
}
