package dao.impl;

import dao.IEmpruntDao;
import model.Emprunt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDaoImpl implements IEmpruntDao {

    private static final String url = "jdbc:mysql://localhost:3306/gestion_bibliotheque_v2?useUnicode=true&characterEncoding=UTF-8";
    private final String username = "root";
    private final String password = "azerty";

    @Override
    public void ajouterEmprunt(Emprunt emprunt) {
        String query = "INSERT INTO emprunts (client_id, document_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, emprunt.getClientId());
            stmt.setInt(2, emprunt.getDocumentId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));

            stmt.executeUpdate();
            System.out.println("Emprunt ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerEmprunt(int empruntId) {
        String query = "DELETE FROM emprunts WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empruntId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Emprunt supprimé avec succès.");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Emprunt> recupererTousLesEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String query = "SELECT * FROM emprunts";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Emprunt emprunt = new Emprunt(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("document_id"),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour").toLocalDate()
                );
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public boolean empruntExiste(int empruntId) {
        String query = "SELECT COUNT(*) FROM emprunts WHERE id = ?";  // La requête pour vérifier l'existence
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empruntId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Si le compte est supérieur à 0, l'emprunt existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Si aucune ligne n'est trouvée, l'emprunt n'existe pas
    }
}
