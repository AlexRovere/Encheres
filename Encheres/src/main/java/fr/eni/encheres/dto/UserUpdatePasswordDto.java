package fr.eni.encheres.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdatePasswordDto {

    @NotEmpty(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial."
    )
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;

    public UserUpdatePasswordDto(String confirmationMotDePasse, String nouveauMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public UserUpdatePasswordDto() {
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public String getConfirmationMotDePasse() {
        return confirmationMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }

    @Override
    public String toString() {
        return "UserUpdatePasswordDto{" +
                "nouveauMotDePasse='" + nouveauMotDePasse + '\'' +
                ", confirmationMotDePasse='" + confirmationMotDePasse + '\'' +
                '}';
    }
}
