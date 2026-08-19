package Service;

import DAO.PaysDao;
import Model.Pays;

import java.sql.SQLException;
import java.util.List;

public class IPaysServiceImple implements IPaysService {

    private final PaysDao paysDAO = new PaysDao();

    @Override
    public boolean ajouter(Pays p) throws SQLException {
        if (p.getNomPays() == null || p.getNomPays().isBlank()) {
            throw new IllegalArgumentException("Le nom du pays est obligatoire.");
        }
        return paysDAO.ajouter(p);
    }

    @Override
    public boolean modifier(Pays p) throws SQLException {
        if (paysDAO.rechercherParId(p.getIdPays()) == null) {
            throw new IllegalArgumentException("Pays introuvable.");
        }
        return paysDAO.modifier(p);
    }

    @Override
    public boolean supprimer(int idPays) throws SQLException {
        return paysDAO.supprimer(idPays);
    }

    @Override
    public Pays rechercherParId(int idPays) throws SQLException {
        return paysDAO.rechercherParId(idPays);
    }

    @Override
    public List<Pays> rechercherParNom(String nom) throws SQLException {
        return paysDAO.rechercherParNom(nom);
    }

    @Override
    public List<Pays> listerTous() throws SQLException {
        return paysDAO.afficherTous();
    }
}