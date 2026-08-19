package Service;

import Model.Pays;

import java.sql.SQLException;
import java.util.List;

public interface IPaysService {

    boolean ajouter(Pays p) throws SQLException;

    boolean modifier(Pays p) throws SQLException;

    boolean supprimer(int idPays) throws SQLException;

    Pays rechercherParId(int idPays) throws SQLException;

    List<Pays> rechercherParNom(String nom) throws SQLException;

    List<Pays> listerTous() throws SQLException;
}