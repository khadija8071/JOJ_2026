package DAO;
import Model.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDao {
        public boolean ajouter(Discipline d) throws SQLException {
            String sql = "INSERT INTO discipline (nom_discipline, description) VALUES (?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, d.getNomDiscipline());
                ps.setString(2, d.getDescription());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            d.setIdDiscipline(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Discipline d) throws SQLException {
            String sql = "UPDATE discipline SET nom_discipline = ?, description = ? WHERE id_discipline = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, d.getNomDiscipline());
                ps.setString(2, d.getDescription());
                ps.setInt(3, d.getIdDiscipline());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idDiscipline) throws SQLException {
            String sql = "DELETE FROM discipline WHERE id_discipline = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idDiscipline);
                return ps.executeUpdate() > 0;
            }
        }

        public Discipline rechercherParId(int idDiscipline) throws SQLException {
            String sql = "SELECT * FROM discipline WHERE id_discipline = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idDiscipline);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Discipline> rechercherParNom(String nom) throws SQLException {
            List<Discipline> liste = new ArrayList<>();
            String sql = "SELECT * FROM discipline WHERE nom_discipline LIKE ?";
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

        public List<Discipline> afficherTous() throws SQLException {
            List<Discipline> liste = new ArrayList<>();
            String sql = "SELECT * FROM discipline ORDER BY nom_discipline";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    liste.add(mapper(rs));
                }
            }
            return liste;
        }

        private Discipline mapper(ResultSet rs) throws SQLException {
            Discipline d = new Discipline();
            d.setIdDiscipline(rs.getInt("id_discipline"));
            d.setNomDiscipline(rs.getString("nom_discipline"));
            d.setDescription(rs.getString("description"));
            return d;
        }
    }

