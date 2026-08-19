import DAO.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_Tables {
    public void main() {
        try (Connection conn = Database.getConnection()) {
            Statement stmt = conn.createStatement();

            // Création des tables
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS utilisateur (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomComplet VARCHAR(100) NOT NULL," +
                    "login VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "role ENUM('admin','user') NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pays (" +
                    "idPays INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomPays VARCHAR(100) NOT NULL," +
                    "continent VARCHAR(50) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS discipline (" +
                    "idDiscipline INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomDiscipline VARCHAR(100) NOT NULL," +
                    "Description VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS athletes (" +
                    "idathlete INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomAthlete VARCHAR(100) NOT NULL," +
                    "prenomAthlete VARCHAR(100) NOT NULL)"+
                    "sexe VARCHAR(50) NOT NULL)"+
                    "DateNaiss VARCHAR(50) NOT NULL)"+
                    "pays VARCHAR(100) NOT NULL)"+
                    "discipline VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS competition (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomCompetition VARCHAR(100) NOT NULL," +
                    "dateCompetition VARCHAR(100) UNIQUE NOT NULL,"+
                    "lieu ENUM('Dakar','Diamniadio','Saly') NOT NULL)+\n" +
                    "discipline VARCHAR(100) NOT NULL,");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS resultat (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "athlete VARCHAR(100) NOT NULL," +
                    "competition VARCHAR(50) UNIQUE NOT NULL," +
                    "score INT  NOT NULL," +
                    "rang INT NOT NULL)");



            // Insertion de données de test
            stmt.executeUpdate("INSERT INTO utilisateur (nomComplet, login, password, role) " +
                    "VALUES ('Admin principal', 'admin', 'admin123', 'admin')");

            stmt.executeUpdate("INSERT INTO pays (nomPays, continent) VALUES ('Sénégal','Afrique')");


            System.out.println("Base et données initialisées avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

