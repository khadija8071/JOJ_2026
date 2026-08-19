package Service;

import Model.Utilisateur;

import java.sql.SQLException;

public interface IAuthService {

    /** Tente de connecter un utilisateur. Renvoie null si login/mot de passe invalides. */
    Utilisateur connecter(String login, String motDePasse) throws SQLException;

    /** Déconnecte l'utilisateur courant. */
    void deconnecter();

    /** Renvoie l'utilisateur actuellement connecté (ou null). */
    Utilisateur getUtilisateurConnecte();

    /** Vrai si un utilisateur est connecté. */
    boolean estConnecte();

    /** Vrai si l'utilisateur connecté a le rôle ADMIN. */
    boolean estAdmin();
}