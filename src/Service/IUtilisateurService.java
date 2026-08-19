package Service;

import Model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface IUtilisateurService {

    boolean ajouter(Utilisateur u) throws SQLException;

    boolean modifier(Utilisateur u) throws SQLException;

    boolean supprimer(int idUtilisateur) throws SQLException;

    Utilisateur rechercherParId(int idUtilisateur) throws SQLException;

    Utilisateur rechercherParLogin(String login) throws SQLException;

    List<Utilisateur> listerTous() throws SQLException;
}