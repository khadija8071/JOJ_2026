import DAO.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_Tables {

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            // ==================== CREATION DES TABLES ====================

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
                    "prenomAthlete VARCHAR(100) NOT NULL," +
                    "sexe VARCHAR(50) NOT NULL," +
                    "DateNaiss VARCHAR(50) NOT NULL," +
                    "pays VARCHAR(100) NOT NULL," +
                    "discipline VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS competition (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nomCompetition VARCHAR(100) NOT NULL," +
                    "dateCompetition VARCHAR(100) NOT NULL," +
                    "lieu ENUM('Dakar','Diamniadio','Saly') NOT NULL," +
                    "discipline VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS resultat (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "athlete VARCHAR(100) NOT NULL," +
                    "competition VARCHAR(100) NOT NULL," +
                    "score INT NOT NULL," +
                    "rang INT NOT NULL)");

            System.out.println("Tables créées avec succès !");

            // ==================== INSERTION DE DONNEES DE TEST ====================

            stmt.executeUpdate("INSERT INTO utilisateur (nomComplet, login, password, role) VALUES " +
                    "('Admin principal', 'admin', 'admin123', 'admin')," +
                    "('Fatou Ndiaye', 'fndiaye', 'agent123', 'user')");

            stmt.executeUpdate("INSERT INTO pays (nomPays, continent) VALUES " +
                    "('Sénégal', 'Afrique')," +
                    "('France', 'Europe')," +
                    "('Maroc', 'Afrique')");

            stmt.executeUpdate("INSERT INTO discipline (nomDiscipline, Description) VALUES " +
                    "('Athlétisme', 'Course, saut et lancer')," +
                    "('Natation', 'Épreuves de nage en bassin')," +
                    "('Judo', 'Sport de combat japonais')");

            stmt.executeUpdate("INSERT INTO athletes (nomAthlete, prenomAthlete, sexe, DateNaiss, pays, discipline) VALUES " +
                    "('Diop', 'Ibrahima', 'M', '2008-03-12', 'Sénégal', 'Athlétisme')," +
                    "('Fall', 'Aïssatou', 'F', '2007-11-05', 'Sénégal', 'Natation')," +
                    "('Dubois', 'Louis', 'M', '2008-02-14', 'France', 'Athlétisme')");

            stmt.executeUpdate("INSERT INTO competition (nomCompetition, dateCompetition, lieu, discipline) VALUES " +
                    "('100m Sprint Garçons', '2026-05-10', 'Dakar', 'Athlétisme')," +
                    "('200m Nage Libre Filles', '2026-05-11', 'Diamniadio', 'Natation')," +
                    "('Judo -60kg Garçons', '2026-05-12', 'Saly', 'Judo')");

            stmt.executeUpdate("INSERT INTO resultat (athlete, competition, score, rang) VALUES " +
                    "('Diop', '100m Sprint Garçons', 1045, 1)," +
                    "('Dubois', '100m Sprint Garçons', 1052, 2)," +
                    "('Fall', '200m Nage Libre Filles', 11832, 1)");

            System.out.println("Données de test insérées avec succès !");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}