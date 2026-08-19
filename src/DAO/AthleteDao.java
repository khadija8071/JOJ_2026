package DAO;
import Model.Athlete;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AthleteDao {

        public boolean ajouter(Athlete a) throws SQLException {
            String sql = "INSERT INTO athlete (nom, prenom, sexe, date_naissance, id_pays, id_discipline) VALUES (?, ?, ?, ?, ?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, a.getNom());
                ps.setString(2, a.getPrenom());
                ps.setString(3, a.getSexe());
                ps.setInt(4, (a.getDateNaissance()));
                ps.setString(5, a.getPays());
                ps.setString(6, a.getDiscipline());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            a.setIdAthlete(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Athlete a) throws SQLException {
            String sql = "UPDATE athlete SET nom = ?, prenom = ?, sexe = ?, date_naissance = ?, id_pays = ?, id_discipline = ? WHERE id_athlete = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, a.getNom());
                ps.setString(2, a.getPrenom());
                ps.setString(3, a.getSexe());
                ps.setInt(4, (a.getDateNaissance()));
                ps.setString(5, a.getPays());
                ps.setString(6, a.getDiscipline());
                ps.setInt(7, a.getIdAthlete());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idAthlete) throws SQLException {
            String sql = "DELETE FROM athlete WHERE id_athlete = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idAthlete);
                return ps.executeUpdate() > 0;
            }
        }

        public Athlete rechercherParId(int idAthlete) throws SQLException {
            String sql = "SELECT * FROM athlete WHERE id_athlete = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idAthlete);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Athlete> rechercherParNom(String nom) throws SQLException {
            List<Athlete> liste = new ArrayList<>();
            String sql = "SELECT * FROM athlete WHERE nom LIKE ? OR prenom LIKE ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + nom + "%");
                ps.setString(2, "%" + nom + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        liste.add(mapper(rs));
                    }
                }
            }
            return liste;
        }

        public List<Athlete> afficherTous() throws SQLException {
            List<Athlete> liste = new ArrayList<>();
            String sql = "SELECT * FROM athlete ORDER BY nom, prenom";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    liste.add(mapper(rs));
                }
            }
            return liste;
        }

        /**
         * Affiche les athlètes avec le nom du pays et de la discipline
         * (jointure utile pour le menu "Afficher athlètes").
         */
        public List<String> afficherAvecDetails() throws SQLException {
            List<String> liste = new ArrayList<>();
            String sql = "SELECT a.id_athlete, a.nom, a.prenom, a.sexe, a.date_naissance, " +
                    "p.nom_pays, d.nom_discipline " +
                    "FROM athlete a " +
                    "JOIN pays p ON a.id_pays = p.id_pays " +
                    "JOIN discipline d ON a.id_discipline = d.id_discipline " +
                    "ORDER BY a.nom, a.prenom";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String ligne = String.format("[%d] %s %s (%s) - Né(e) le %s - %s - %s",
                            rs.getInt("id_athlete"),
                            rs.getString("prenom"),
                            rs.getString("nom"),
                            rs.getString("sexe"),
                            rs.getDate("date_naissance"),
                            rs.getString("nom_pays"),
                            rs.getString("nom_discipline"));
                    liste.add(ligne);
                }
            }
            return liste;
        }

        private Athlete mapper(ResultSet rs) throws SQLException {
            Athlete a = new Athlete();
            a.setIdAthlete(rs.getInt("id_athlete"));
            a.setNom(rs.getString("nom"));
            a.setPrenom(rs.getString("prenom"));
            a.setSexe(rs.getString("sexe"));
            a.setDateNaissance(rs.getInt("date_naissance"));
            a.setPays(rs.getString("id_pays"));
            a.setDiscipline(rs.getString("id_discipline"));
            return a;
        }
    }

