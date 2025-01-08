package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Enchere {
    private int noEnchere;
    private Utilisateur utilisateur;
    private LocalDate dateEnchere;
    private int montantEnchere;

    public Enchere() {
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "noEnchere=" + noEnchere +
                ", utilisateur=" + utilisateur +
                ", dateEnchere=" + dateEnchere +
                ", montantEnchere=" + montantEnchere +
                '}';
    }

    public int getNoEnchere() {
        return noEnchere;
    }

    public void setNoEnchere(int noEnchere) {
        this.noEnchere = noEnchere;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }
}
