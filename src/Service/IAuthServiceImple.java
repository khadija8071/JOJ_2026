package Service;

import DAO.UtilisateurDao;
import Model.Utilisateur;

import java.sql.SQLException;

public class IAuthServiceImple implements IAuthService {

    private final UtilisateurDao utilisateurDAO = new UtilisateurDao();
    private Utilisateur utilisateurConnecte;

    @Override
    public Utilisateur connecter(String login, String motDePasse) throws SQLException {
        Utilisateur u = utilisateurDAO.authentifier(login, motDePasse);
        if (u != null) {
            this.utilisateurConnecte = u;
        }
        return u;
    }

    @Override
    public void deconnecter() {
        this.utilisateurConnecte = null;
    }

    @Override
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    @Override
    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    @Override
    public boolean estAdmin() {
        return utilisateurConnecte != null && utilisateurConnecte.isAdmin();
    }
}