package dao.impl;

import dao.IClientDao;
import model.Client;
import model.ClientExterne;
import model.ClientInterne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements IClientDao {

    private static final String url = "jdbc:mysql://localhost:3306/gestion_bibliotheque_v2?useUnicode=true&characterEncoding=UTF-8";
    private final String username = "root";
    private final String password = "azerty";

    @Override
    public void ajouterClient(Client client) {
        String query;

        if (client instanceof ClientInterne) {
            query = "INSERT INTO clients (nom, email, telephone, type_client, departement) VALUES (?, ?, ?, 'INTERNE', ?)";
        } else if (client instanceof ClientExterne) {
            query = "INSERT INTO clients (nom, email, telephone, type_client, abonnement) VALUES (?, ?, ?, 'EXTERNE', ?)";
        } else {
            throw new IllegalArgumentException("Type de client inconnu");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getTelephone());

            if (client instanceof ClientInterne) {
                stmt.setString(4, ((ClientInterne) client).getDepartement().name());
            } else if (client instanceof ClientExterne) {
                stmt.setString(4, ((ClientExterne) client).getAbonnement().name());
            }

            stmt.executeUpdate();
            System.out.println("Client ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerClient(int clientId) {
        String query = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, clientId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client supprimé avec succès.");
            } else {
                System.out.println("Aucun client trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean clientExists(int clientId) {
        String query = "SELECT COUNT(*) FROM clients WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Si le nombre de clients trouvés est supérieur à 0, alors le client existe.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Retourne false si une erreur se produit ou aucun client trouvé.
    }

    @Override
    public void mettreAJourClient(Client client) {
        String query;

        if (client instanceof ClientInterne) {
            query = "UPDATE clients SET nom = ?, email = ?, telephone = ?, departement = ? WHERE id = ?";
        } else if (client instanceof ClientExterne) {
            query = "UPDATE clients SET nom = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?";
        } else {
            throw new IllegalArgumentException("Type de client inconnu");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getTelephone());

            // Gestion du type de client (Interne ou Externe)
            if (client instanceof ClientInterne) {
                stmt.setString(4, ((ClientInterne) client).getDepartement().name());
            } else if (client instanceof ClientExterne) {
                stmt.setString(4, ((ClientExterne) client).getAbonnement().name());
            }

            stmt.setInt(5, client.getId()); // Mettre à jour le client avec le bon ID

            int rowsAffected = stmt.executeUpdate(); // Exécuter la mise à jour
            if (rowsAffected > 0) {
                System.out.println("Client mis à jour avec succès.");
            } else {
                System.out.println("Aucun client trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du client : " + e.getMessage());
        }
    }


    @Override
    public List<Client> recupererTousLesClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients"; // Exemple de requête pour récupérer tous les clients

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String type = rs.getString("type_client");

                // Créer un client en fonction du type
                Client client;
                if ("INTERNE".equals(type)) {
                    // Si le client est interne, crée un ClientInterne (ajuster en fonction des données)
                    client = new ClientInterne(id, nom, email, telephone, ClientInterne.Departement.valueOf(rs.getString("departement")));
                } else if ("EXTERNE".equals(type)) {
                    // Si le client est externe, on vérifie si "abonnement" est null
                    String abonnementString = rs.getString("abonnement");
                    ClientExterne.Abonnement abonnement = null;
                    if (abonnementString != null) {
                        try {
                            abonnement = ClientExterne.Abonnement.valueOf(abonnementString.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Valeur d'abonnement invalide pour le client ID: " + id);
                        }
                    }

                    // Créer le client externe avec l'abonnement ou null si non valide
                    client = new ClientExterne(id, nom, email, telephone, abonnement != null ? abonnement : ClientExterne.Abonnement.MENSUEL);  // Par défaut, assigner un abonnement "MENSUEL" si l'abonnement est nul ou invalide
                } else {
                    // Cas par défaut si le type client n'est pas défini correctement
                    continue;
                }
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


    @Override
    public Client trouverClientParId(int clientId) {
        String query = "SELECT * FROM clients WHERE id = ?";
        Client client = null;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String typeClient = rs.getString("type_client");

                    if ("INTERNE".equals(typeClient)) {
                        client = new ClientInterne(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getString("email"),
                                rs.getString("telephone"),
                                ClientInterne.Departement.valueOf(rs.getString("departement").toUpperCase())
                        );
                    } else if ("EXTERNE".equals(typeClient)) {
                        client = new ClientExterne(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getString("email"),
                                rs.getString("telephone"),
                                ClientExterne.Abonnement.valueOf(rs.getString("abonnement").toUpperCase())
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
