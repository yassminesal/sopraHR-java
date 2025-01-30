package model;

public class Client {
    private int id;
    private String nom;
    private String email;
    private String telephone;
    private String typeClient;

    public Client(int id, String nom, String email, String telephone,String typeClient) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.typeClient = typeClient;

    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "ID: " + id + ", Nom: " + nom + ", Email: " + email + ", Téléphone: " + telephone;
    }
}
