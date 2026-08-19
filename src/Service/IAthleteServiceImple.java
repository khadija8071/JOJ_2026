package Service;

import DAO.AthleteDao;
import DAO.DisciplineDao;
import DAO.PaysDao;
import Model.Athlete;

import java.sql.SQLException;
import java.util.List;

public class IAthleteServiceImple implements IAthleteService {

    private final AthleteDao athleteDAO = new AthleteDao();
    private final PaysDao paysDAO = new PaysDao();
    private final DisciplineDao disciplineDAO = new DisciplineDao();

    @Override
    public boolean ajouter(Athlete a) throws SQLException {
        verifierReferences(a);
        return athleteDAO.ajouter(a);
    }

    @Override
    public boolean modifier(Athlete a) throws SQLException {
        if (athleteDAO.rechercherParId(a.getIdAthlete()) == null) {
            throw new IllegalArgumentException("Athlète introuvable.");
        }
        verifierReferences(a);
        return athleteDAO.modifier(a);
    }

    @Override
    public boolean supprimer(int idAthlete) throws SQLException {
        return athleteDAO.supprimer(idAthlete);
    }

    @Override
    public Athlete rechercherParId(int idAthlete) throws SQLException {
        return athleteDAO.rechercherParId(idAthlete);
    }

    @Override
    public List<Athlete> rechercherParNom(String nom) throws SQLException {
        return athleteDAO.rechercherParNom(nom);
    }

    @Override
    public List<Athlete> listerTous() throws SQLException {
        return athleteDAO.afficherTous();
    }

    @Override
    public List<String> listerAvecDetails() throws SQLException {
        return athleteDAO.afficherAvecDetails();
    }

    /** Vérifie que le pays et la discipline référencés existent bien avant insertion/mise à jour. */
    private void verifierReferences(Athlete a) throws SQLException {
        if (a.getNom() == null || a.getNom().isBlank() || a.getPrenom() == null || a.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le nom et le prénom sont obligatoires.");
        }
        if (paysDAO.rechercherParId(a.getPays()) == null) {
            throw new IllegalArgumentException("Le pays sélectionné n'existe pas.");
        }
        if (disciplineDAO.rechercherParId(a.getDiscipline()) == null) {
            throw new IllegalArgumentException("La discipline sélectionnée n'existe pas.");
        }
    }
}