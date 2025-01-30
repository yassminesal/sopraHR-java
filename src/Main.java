import service.impl.DocumentServiceImpl;
import model.Document;
import model.Magazine;
import model.Livre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Test de la connexion à la base de données
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_bibliotheque_v2?useUnicode=true&characterEncoding=UTF-8",
                "root",
                "azerty"
        )) {
            System.out.println("Connexion réussie : " + conn);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            return; // Arrête l'exécution si la connexion échoue
        }
        DocumentServiceImpl documentService = new DocumentServiceImpl();






    }
}
