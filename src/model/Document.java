package model;

public abstract class Document {
    private int id;
    private String titre;
    private String typeDocument;
    private boolean disponible; // Nouvel attribut ajouté

    // Constructeur mis à jour
    public Document(int id, String titre, String typeDocument, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.typeDocument = typeDocument;
        this.disponible = disponible;
    }

    // Getter et setter pour typeDocument
    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    // Getter et setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et setter pour titre
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Titre: " + titre + ", Type: " + typeDocument + ", Disponible: " + disponible;
    }
}
