package model;

public class ClientInterne extends Client {
    public enum Departement {
        A,
        B,
        C;
    }

    private Departement departement;


    public ClientInterne(int id, String nom, String email, String telephone, Departement departement) {
        super(id, nom, email, telephone,"INTERNE");
        this.departement = departement;
    }


    public Departement getDepartement() {
        return departement;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: INTERNE "+", Département: " + departement;
    }
}
