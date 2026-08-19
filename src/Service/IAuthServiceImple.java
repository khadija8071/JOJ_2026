package Service;

import DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IAuthServiceImple implements IAuthService{
    @Override
    public boolean trouverAdmin(String login, String password) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE login=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // true si utilisateur trouvé
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
