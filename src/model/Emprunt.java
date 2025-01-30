package model;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int clientId;
    private int documentId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;


    public Emprunt() {
    }

    // Constructeur avec 5 paramètres (pour récupération d'un emprunt existant dans la base de données)
    public Emprunt(int id, int clientId, int documentId, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.clientId = clientId;
        this.documentId = documentId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    // Constructeur avec 4 paramètres (sans l'id, utile pour l'ajout dans la base de données)
    public Emprunt(int clientId, int documentId, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.clientId = clientId;
        this.documentId = documentId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    // Getters et setters avec validation de la date
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {

        this.documentId = documentId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {

        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        if (dateRetour.isBefore(dateEmprunt)) {
            throw new IllegalArgumentException("La date de retour ne peut pas être antérieure à la date d'emprunt.");
        }
        this.dateRetour = dateRetour;
    }

    // Méthode pour vérifier si l'emprunt a été retourné
    public boolean isReturned() {
        return LocalDate.now().isAfter(dateRetour);
    }

    @Override
    public String toString() {
        return "ID Emprunt: " + id + ", Client ID: " + clientId + ", Document ID: " + documentId +
                ", Date Emprunt: " + dateEmprunt + ", Date Retour: " + dateRetour;
    }
}
