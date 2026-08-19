package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/joj_dakar2026";
        String user = "root";
        String password = "";
        Connection conn = null;
        try{

            conn = DriverManager.getConnection(url,user,password);
            System.out.println("connection avec joj_dk reuissie !!!");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return conn;
    }


}