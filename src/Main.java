import DAO.Database;

import java.sql.Connection;
import java.sql.SQLException;
import Service.IMenu;
import Service.IMenuImple;

public class Main {
    public static void main(String[] args) {
        IMenu menu = new IMenuImple();
        menu.demarrer();
    }
}
















/*void main() throws SQLException {
    /// verif de la connection !!!!
    Connection con = Database.getConnection();
    if (con != null){
        System.out.println("connection a la base reuissie");
    }else{
        System.out.println("echec avec la BD ");
    }
}*/
