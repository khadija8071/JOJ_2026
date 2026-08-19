package DAO;

import Model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {
        public boolean ajouter(Utilisateur u) throws SQLException {
            String sql = "INSERT INTO utilisateur (nom, login, mdp, role) VALUES (?, ?, ?, ?)";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getNom());
                ps.setString(2, u.getLogin());
                ps.setString(3, u.getMdp());
                ps.setString(4, u.getRole());
                int lignes = ps.executeUpdate();
                if (lignes > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            u.setIdUtilisateur(rs.getInt(1));
                        }
                    }
                    return true;
                }
                return false;
            }
        }

        public boolean modifier(Utilisateur u) throws SQLException {
            String sql = "UPDATE utilisateur SET nom = ?, login = ?, password = ?, role = ? WHERE id = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, u.getNom());
                ps.setString(2, u.getLogin());
                ps.setString(3, u.getMdp());
                ps.setString(4, u.getRole());
                ps.setInt(5, u.getIdUtilisateur());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean supprimer(int idUtilisateur) throws SQLException {
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUtilisateur);
                return ps.executeUpdate() > 0;
            }
        }

        public Utilisateur rechercherParId(int idUtilisateur) throws SQLException {
            String sql = "SELECT * FROM utilisateur WHERE id = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUtilisateur);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public Utilisateur rechercherParLogin(String login) throws SQLException {
            String sql = "SELECT * FROM utilisateur WHERE login = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        public List<Utilisateur> afficherTous() throws SQLException {
            List<Utilisateur> liste = new ArrayList<>();
            String sql = "SELECT * FROM utilisateur ORDER BY id_utilisateur";
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
         * Authentifie un utilisateur

         */
        public Utilisateur authentifier(String login, String motDePasse) throws SQLException {
            String sql = "SELECT * FROM utilisateur WHERE login = ? AND mdp = ?";
            Connection conn = Database.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, login);
                ps.setString(2, motDePasse);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapper(rs);
                    }
                }
            }
            return null;
        }

        private Utilisateur mapper(ResultSet rs) throws SQLException {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(rs.getInt("id_utilisateur"));
            u.setNom(rs.getString("nom_complet"));
            u.setLogin(rs.getString("login"));
            u.setMdp(rs.getString("mot_de_passe"));
            u.setRole(rs.getString("role"));
            return u;
        }
    }

