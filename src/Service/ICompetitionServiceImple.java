package Service;

import DAO.CompetitionDao;
import DAO.DisciplineDao;
import Model.Competition;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ICompetitionServiceImple implements ICompetitionService {

    private static final Set<String> LIEUX_VALIDES = Set.of("Dakar", "Diamniadio", "Saly");

    private final CompetitionDao competitionDAO = new CompetitionDao();
    private final DisciplineDao disciplineDAO = new DisciplineDao();

    @Override
    public boolean ajouter(Competition c) throws SQLException {
        verifier(c);
        return competitionDAO.ajouter(c);
    }

    @Override
    public boolean modifier(Competition c) throws SQLException {
        if (competitionDAO.rechercherParId(c.getIdCompetition()) == null) {
            throw new IllegalArgumentException("Compétition introuvable.");
        }
        verifier(c);
        return competitionDAO.modifier(c);
    }

    @Override
    public boolean supprimer(int idCompetition) throws SQLException {
        return competitionDAO.supprimer(idCompetition);
    }

    @Override
    public Competition rechercherParId(int idCompetition) throws SQLException {
        return competitionDAO.rechercherParId(idCompetition);
    }

    @Override
    public List<Competition> rechercherParNom(String nom) throws SQLException {
        return competitionDAO.rechercherParNom(nom);
    }

    @Override
    public List<Competition> listerTous() throws SQLException {
        return competitionDAO.afficherTous();
    }

    private void verifier(Competition c) throws SQLException {
        if (c.getNomCompetition() == null || c.getNomCompetition().isBlank()) {
            throw new IllegalArgumentException("Le nom de la compétition est obligatoire.");
        }
        if (c.getLieu() == null || !LIEUX_VALIDES.contains(c.getLieu())) {
            throw new IllegalArgumentException("Le lieu doit être Dakar, Diamniadio ou Saly.");
        }
        if (disciplineDAO.rechercherParId(c.getIdCompetition()) == null) {
            throw new IllegalArgumentException("La discipline sélectionnée n'existe pas.");
        }
    }
}