package DAO;
import Model.Competition;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDao {

    public class CompetitionDAO {

        public boolean ajouter(Competition c) throws SQLException {
            String sql = "INSERT INTO competition (nom_competition, date_competition, lieu, id_discipline) VALUES (?, ?, ?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, c.getNomCompetition());
                ps.setInt(2, (c.getDateCompetition()));
                ps.setString(3, c.getLieu());
                ps.setString(4, c.getDiscipline());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            c.setIdCompetition(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Competition c) throws SQLException {
            String sql = "UPDATE competition SET nom_competition = ?, date_competition = ?, lieu = ?, id_discipline = ? WHERE id_competition = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, c.getNomCompetition());
                ps.setInt(2, (c.getDateCompetition()));
                ps.setString(3, c.getLieu());
                ps.setString(4, c.getDiscipline());
                ps.setInt(5, c.getIdCompetition());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idCompetition) throws SQLException {
            String sql = "DELETE FROM competition WHERE id_competition = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idCompetition);
                return ps.executeUpdate() > 0;
            }
        }

        public Competition rechercherParId(int idCompetition) throws SQLException {
            String sql = "SELECT * FROM competition WHERE id_competition = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idCompetition);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Competition> rechercherParNom(String nom) throws SQLException {
            List<Competition> liste = new ArrayList<>();
            String sql = "SELECT * FROM competition WHERE nom_competition LIKE ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + nom + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        liste.add(mapper(rs));
                    }
                }
            }
            return liste;
        }

        public List<Competition> afficherTous() throws SQLException {
            List<Competition> liste = new ArrayList<>();
            String sql = "SELECT * FROM competition ORDER BY date_competition";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    liste.add(mapper(rs));
                }
            }
            return liste;
        }

        private Competition mapper(ResultSet rs) throws SQLException {
            Competition c = new Competition();
            c.setIdCompetition(rs.getInt("id_competition"));
            c.setNomCompetition(rs.getString("nom_competition"));
            c.setDateCompetition(rs.getInt("date_competition"));
            c.setLieu(rs.getString("lieu"));
            c.setDiscipline(rs.getString("id_discipline"));
            return c;
        }
    }
}
