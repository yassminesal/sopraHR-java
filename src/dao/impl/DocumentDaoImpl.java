package dao.impl;

import dao.IDocumentDao;
import model.Document;
import model.Livre;
import model.Magazine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImpl implements IDocumentDao {

    private static final String url = "jdbc:mysql://localhost:3306/gestion_bibliotheque_v2?useUnicode=true&characterEncoding=UTF-8";
    private final String username = "root";
    private final String password = "azerty";

    @Override
    public void ajouterDocument(Document document) {
        String query;
        if (document instanceof Livre) {
            query = "INSERT INTO documents (titre, type_document, auteur, disponible) VALUES (?, 'LIVRE', ?, ?)";
        } else if (document instanceof Magazine) {
            query = "INSERT INTO documents (titre, type_document, periodicite, disponible) VALUES (?, 'MAGAZINE', ?, ?)";
        } else {
            throw new IllegalArgumentException("Type de document inconnu");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, document.getTitre());

            if (document instanceof Livre) {
                stmt.setString(2, ((Livre) document).getAuteur());
            } else if (document instanceof Magazine) {
                stmt.setString(2, ((Magazine) document).getPeriodicite().name());
            }

            stmt.setBoolean(3, document.isDisponible()); // Gestion de la disponibilité
            stmt.executeUpdate();
            System.out.println("Document ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerDocument(int documentId) {
        // Vérifier si le document existe avant de le supprimer
        if (documentExists(documentId)) {
            String query = "DELETE FROM documents WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, documentId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Document supprimé avec succès.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun document trouvé avec cet ID.");
        }
    }

    // Méthode pour vérifier si un document existe avant la suppression
    public boolean documentExists(int documentId) {
        String query = "SELECT COUNT(*) FROM documents WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, documentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Si le nombre de documents trouvés est supérieur à 0, alors le document existe.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Retourne false si une erreur se produit ou aucun document trouvé.
    }

    @Override
    public void mettreAJourDocument(Document document) {
        String query;
        if (document instanceof Livre) {
            query = "UPDATE documents SET titre = ?, auteur = ?, disponible = ? WHERE id = ?";
        } else if (document instanceof Magazine) {
            query = "UPDATE documents SET titre = ?, periodicite = ?, disponible = ? WHERE id = ?";
        } else {
            throw new IllegalArgumentException("Type de document inconnu");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, document.getTitre());

            if (document instanceof Livre) {
                stmt.setString(2, ((Livre) document).getAuteur());
            } else if (document instanceof Magazine) {
                stmt.setString(2, ((Magazine) document).getPeriodicite().name());
            }

            stmt.setBoolean(3, document.isDisponible());
            stmt.setInt(4, document.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Document mis à jour avec succès.");
            } else {
                System.out.println("Aucun document trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Document> recupererTousLesDocuments() {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM documents";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String typeDocument = rs.getString("type_document").toUpperCase();
                Document document;

                if ("LIVRE".equals(typeDocument)) {
                    document = new Livre(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getBoolean("disponible")
                    );
                } else if ("MAGAZINE".equals(typeDocument)) {
                    try {
                        Magazine.Periodicite periodicite = Magazine.Periodicite.valueOf(rs.getString("periodicite").toUpperCase());
                        document = new Magazine(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                periodicite,
                                rs.getBoolean("disponible")
                        );
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erreur : valeur de périodicité invalide dans la base de données - " + rs.getString("periodicite"));
                        continue;
                    }
                } else {
                    System.err.println("Type de document inconnu : " + typeDocument);
                    continue;
                }

                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public Document trouverDocumentParId(int documentId) {
        String query = "SELECT * FROM documents WHERE id = ?";
        Document document = null;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, documentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String typeDocument = rs.getString("type_document").toUpperCase();

                    if ("LIVRE".equals(typeDocument)) {
                        document = new Livre(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getBoolean("disponible")
                        );
                    } else if ("MAGAZINE".equals(typeDocument)) {
                        document = new Magazine(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                Magazine.Periodicite.valueOf(rs.getString("periodicite")),
                                rs.getBoolean("disponible")
                        );
                    }
                } else {
                    System.out.println("Aucun document trouvé avec cet ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return document;
    }
}
