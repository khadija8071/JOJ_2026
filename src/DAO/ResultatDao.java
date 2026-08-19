package DAO;
import Model.Resultat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultatDao {

    public class ResultatDAO {

        public boolean ajouter(Resultat r) throws SQLException {
            String sql = "INSERT INTO resultat (id_athlete, id_competition, score, rang) VALUES (?, ?, ?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, r.getId_Athlete());
                ps.setInt(2, r.getId_compet());
                ps.setDouble(3, r.getScore());
                ps.setInt(4, r.getRang());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            r.setIdResultat(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Resultat r) throws SQLException {
            String sql = "UPDATE resultat SET id_athlete = ?, id_competition = ?, score = ?, rang = ? WHERE id_resultat = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, r.getId_Athlete());
                ps.setInt(2, r.getId_compet());
                ps.setDouble(3, r.getScore());
                ps.setInt(4, r.getRang());
                ps.setInt(5, r.getIdResultat());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idResultat) throws SQLException {
            String sql = "DELETE FROM resultat WHERE id_resultat = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idResultat);
                return ps.executeUpdate() > 0;
            }
        }

        public Resultat rechercherParId(int idResultat) throws SQLException {
            String sql = "SELECT * FROM resultat WHERE id_resultat = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idResultat);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Resultat> afficherTous() throws SQLException {
            List<Resultat> liste = new ArrayList<>();
            String sql = "SELECT * FROM resultat ORDER BY id_competition, rang";
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
         * Classement d'une compétition donnée, trié par rang croissant (module 10.4).
         */
        public List<Resultat> classementParCompetition(int idCompetition) throws SQLException {
            List<Resultat> liste = new ArrayList<>();
            String sql = "SELECT * FROM resultat WHERE id_competition = ? ORDER BY rang";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idCompetition);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        liste.add(mapper(rs));
                    }
                }
            }
            return liste;
        }

        /**
         * Calcule le tableau des médailles par pays (module 11).
         * Clé de la map = nom du pays, valeur = tableau {or, argent, bronze, total}.
         */
        public Map<String, int[]> getTableauMedailles() throws SQLException {
            Map<String, int[]> tableau = new LinkedHashMap<>();
            String sql = "SELECT p.nom_pays AS pays, " +
                    "SUM(CASE WHEN r.rang = 1 THEN 1 ELSE 0 END) AS or_, " +
                    "SUM(CASE WHEN r.rang = 2 THEN 1 ELSE 0 END) AS argent, " +
                    "SUM(CASE WHEN r.rang = 3 THEN 1 ELSE 0 END) AS bronze " +
                    "FROM resultat r " +
                    "JOIN athlete a ON r.id_athlete = a.id_athlete " +
                    "JOIN pays p ON a.id_pays = p.id_pays " +
                    "WHERE r.rang IN (1, 2, 3) " +
                    "GROUP BY p.nom_pays " +
                    "ORDER BY or_ DESC, argent DESC, bronze DESC";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    int or = rs.getInt("or_");
                    int argent = rs.getInt("argent");
                    int bronze = rs.getInt("bronze");
                    tableau.put(rs.getString("pays"), new int[]{or, argent, bronze, or + argent + bronze});
                }
            }
            return tableau;
        }

        private Resultat mapper(ResultSet rs) throws SQLException {
            Resultat r = new Resultat();
            r.setIdResultat(rs.getInt("id_resultat"));
            r.setId_Athlete(rs.getInt("id_athlete"));
            r.setId_compet(rs.getInt("id_competition"));
            r.setScore(rs.getInt("score"));
            r.setRang(rs.getInt("rang"));
            return r;
        }
    }
}
