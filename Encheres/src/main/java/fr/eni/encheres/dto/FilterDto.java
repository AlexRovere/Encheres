package fr.eni.encheres.dto;

public class FilterDto {
    private String nomArticle;
    private Integer noCategorie;
    private Boolean achat = true;
    private Boolean enchereOuverte;
    private Boolean enchereEnCours;
    private Boolean enchereRemporte;
    private Boolean venteEnCours;
    private Boolean venteNonDebute;
    private Boolean venteTermine;

    public FilterDto(String nomArticle, Integer noCategorie, boolean achat, Boolean enchereOuverte, Boolean enchereEnCours, Boolean enchereRemporte, Boolean venteEnCours, Boolean venteNonDebute, Boolean venteTermine) {
        this.nomArticle = nomArticle;
        this.noCategorie = noCategorie;
        this.achat = achat;
        this.enchereOuverte = enchereOuverte;
        this.enchereEnCours = enchereEnCours;
        this.enchereRemporte = enchereRemporte;
        this.venteEnCours = venteEnCours;
        this.venteNonDebute = venteNonDebute;
        this.venteTermine = venteTermine;
    }

    public FilterDto() {

    }

    @Override
    public String toString() {
        return "FilterDto{" +
                "nomArticle='" + nomArticle + '\'' +
                ", noCategorie=" + noCategorie +
                ", achat=" + achat +
                ", enchereOuverte=" + enchereOuverte +
                ", enchereEnCours=" + enchereEnCours +
                ", enchereRemporte=" + enchereRemporte +
                ", venteEnCours=" + venteEnCours +
                ", venteNonDebute=" + venteNonDebute +
                ", venteTermine=" + venteTermine +
                '}';
    }

    public void setAchat(Boolean achat) {
        this.achat = achat;
    }

    public boolean getAchat() {
        return achat;
    }

    public Boolean getEnchereOuverte() {
        return enchereOuverte;
    }

    public void setEnchereOuverte(Boolean enchereOuverte) {
        this.enchereOuverte = enchereOuverte;
    }

    public Boolean getEnchereEnCours() {
        return enchereEnCours;
    }

    public void setEnchereEnCours(Boolean enchereEnCours) {
        this.enchereEnCours = enchereEnCours;
    }

    public Boolean getEnchereRemporte() {
        return enchereRemporte;
    }

    public void setEnchereRemporte(Boolean enchereRemporte) {
        this.enchereRemporte = enchereRemporte;
    }

    public Boolean getVenteEnCours() {
        return venteEnCours;
    }

    public void setVenteEnCours(Boolean venteEnCours) {
        this.venteEnCours = venteEnCours;
    }

    public Boolean getVenteNonDebute() {
        return venteNonDebute;
    }

    public void setVenteNonDebute(Boolean venteNonDebute) {
        this.venteNonDebute = venteNonDebute;
    }

    public Boolean getVenteTermine() {
        return venteTermine;
    }

    public void setVenteTermine(Boolean venteTermine) {
        this.venteTermine = venteTermine;
    }

    public FilterDto(String nomArticle, Integer noCategorie) {
        this.nomArticle = nomArticle;
        this.noCategorie = noCategorie;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public Integer getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(Integer noCategorie) {
        this.noCategorie = noCategorie;
    }
}
