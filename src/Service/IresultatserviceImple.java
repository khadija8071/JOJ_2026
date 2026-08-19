package Service;

import DAO.AthleteDao;
import DAO.CompetitionDao;
import DAO.ResultatDao;
import Model.Resultat;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IResultatServiceImple implements IResultatService {

    private final ResultatDao resultatDAO = new ResultatDao();
    private final AthleteDao athleteDAO = new AthleteDao();
    private final CompetitionDao competitionDAO = new CompetitionDao();

    @Override
    public boolean enregistrer(Resultat r) throws SQLException {
        verifier(r);
        return resultatDAO.ajouter(r);
    }

    @Override
    public boolean modifier(Resultat r) throws SQLException {
        if (resultatDAO.rechercherParId(r.getIdResultat()) == null) {
            throw new IllegalArgumentException("Résultat introuvable.");
        }
        verifier(r);
        return resultatDAO.modifier(r);
    }

    @Override
    public boolean supprimer(int idResultat) throws SQLException {
        return resultatDAO.supprimer(idResultat);
    }

    @Override
    public Resultat rechercherParId(int idResultat) throws SQLException {
        return resultatDAO.rechercherParId(idResultat);
    }

    @Override
    public List<Resultat> listerTous() throws SQLException {
        return resultatDAO.afficherTous();
    }

    @Override
    public List<Resultat> classementParCompetition(int idCompetition) throws SQLException {
        return resultatDAO.classementParCompetition(idCompetition);
    }

    @Override
    public Map<String, int[]> getTableauMedailles() throws SQLException {
        return resultatDAO.getTableauMedailles();
    }

    private void verifier(Resultat r) throws SQLException {
        if (athleteDAO.rechercherParId(r.getIdAthlete()) == null) {
            throw new IllegalArgumentException("L'athlète sélectionné n'existe pas.");
        }
        if (competitionDAO.rechercherParId(r.getIdCompetition()) == null) {
            throw new IllegalArgumentException("La compétition sélectionnée n'existe pas.");
        }
        if (r.getRang() <= 0) {
            throw new IllegalArgumentException("Le rang doit être un entier positif.");
        }
    }
}