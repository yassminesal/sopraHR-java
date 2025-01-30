package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/gestion_bibliotheque_v2?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "azerty";



    private static Connection connection = null;

    private DatabaseConnection() {

    }
    public static Connection getConnection() {

        if(connection == null) {

            try {
                connection = DriverManager.getConnection(url, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Erreur : "+ e.getMessage());
                System.out.println("echec");
            }
        }
        System.out.println("Ok");
        return connection;
    }
}