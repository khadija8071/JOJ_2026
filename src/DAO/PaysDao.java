package DAO;

import Model.Pays;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaysDao {

        public boolean ajouter(Pays p) throws SQLException {
            String sql = "INSERT INTO pays (nom_pays, continent) VALUES (?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getNomPays());
                ps.setString(2, p.getContinent());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            p.setIdPays(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Pays p) throws SQLException {
            String sql = "UPDATE pays SET nom_pays = ?, continent = ? WHERE id_pays = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getNomPays());
                ps.setString(2, p.getContinent());
                ps.setInt(3, p.getIdPays());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idPays) throws SQLException {
            String sql = "DELETE FROM pays WHERE id_pays = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idPays);
                return ps.executeUpdate() > 0;
            }
        }

        public Pays rechercherParId(int idPays) throws SQLException {
            String sql = "SELECT * FROM pays WHERE id_pays = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idPays);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Pays> rechercherParNom(String nom) throws SQLException {
            List<Pays> liste = new ArrayList<>();
            String sql = "SELECT * FROM pays WHERE nom_pays LIKE ?";
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

        public List<Pays> afficherTous() throws SQLException {
            List<Pays> liste = new ArrayList<>();
            String sql = "SELECT * FROM pays ORDER BY nom_pays";
            Connection conn = Database.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    liste.add(mapper(rs));
                }
            }
            return liste;
        }

        private Pays mapper(ResultSet rs) throws SQLException {
            Pays p = new Pays();
            p.setIdPays(rs.getInt("id_pays"));
            p.setNomPays(rs.getString("nom_pays"));
            p.setContinent(rs.getString("continent"));
            return p;
        }
    }

