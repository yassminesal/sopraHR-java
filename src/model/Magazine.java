package model;

public class Magazine extends Document {

    public enum Periodicite {
        ANNUEL, MENSUEL, TRIMESTRIEL
    }

    private Periodicite periodicite;

    // Constructeur mis à jour
    public Magazine(int id, String titre, Periodicite periodicite, boolean disponible) {
        super(id, titre, "MAGAZINE", disponible); // Appel au constructeur parent avec 'disponible'
        this.periodicite = periodicite;
    }

    // Getter pour periodicite
    public Periodicite getPeriodicite() {
        return periodicite;
    }

    // Setter pour periodicite
    public void setPeriodicite(Periodicite periodicite) {
        this.periodicite = periodicite;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: MAGAZINE, Périodicité: " + periodicite;
    }
}
