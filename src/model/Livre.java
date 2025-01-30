package model;

public class Livre extends Document {
    private String auteur;

    // Constructeur mis à jour pour inclure 'disponible'
    public Livre(int id, String titre, String auteur, boolean disponible) {
        super(id, titre, "LIVRE", disponible); // Appel au constructeur parent avec 'disponible'
        this.auteur = auteur;
    }

    // Getter pour auteur
    public String getAuteur() {
        return auteur;
    }

    // Setter pour auteur
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: LIVRE, Auteur: " + auteur;
    }
}
