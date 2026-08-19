package Service;

import DAO.UtilisateurDao;
import Model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public class IUtilisateurServiceImple implements IUtilisateurService {

    private final UtilisateurDao utilisateurDAO = new UtilisateurDao();

    @Override
    public boolean ajouter(Utilisateur u) throws SQLException {
        if (u.getLogin() == null || u.getLogin().isBlank()) {
            throw new IllegalArgumentException("Le login est obligatoire.");
        }
        if (utilisateurDAO.rechercherParLogin(u.getLogin()) != null) {
            throw new IllegalArgumentException("Ce login existe déjà.");
        }
        return utilisateurDAO.ajouter(u);
    }

    @Override
    public boolean modifier(Utilisateur u) throws SQLException {
        if (utilisateurDAO.rechercherParId(u.getIdUtilisateur()) == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }
        return utilisateurDAO.modifier(u);
    }

    @Override
    public boolean supprimer(int idUtilisateur) throws SQLException {
        return utilisateurDAO.supprimer(idUtilisateur);
    }

    @Override
    public Utilisateur rechercherParId(int idUtilisateur) throws SQLException {
        return utilisateurDAO.rechercherParId(idUtilisateur);
    }

    @Override
    public Utilisateur rechercherParLogin(String login) throws SQLException {
        return utilisateurDAO.rechercherParLogin(login);
    }

    @Override
    public List<Utilisateur> listerTous() throws SQLException {
        return utilisateurDAO.afficherTous();
    }
}