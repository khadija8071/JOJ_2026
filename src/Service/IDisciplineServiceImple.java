package Service;

import DAO.DisciplineDao;
import Model.Discipline;

import java.sql.SQLException;
import java.util.List;

public class IDisciplineServiceImple implements IDisciplineService {

    private final DisciplineDao disciplineDAO = new DisciplineDao();

    @Override
    public boolean ajouter(Discipline d) throws SQLException {
        if (d.getNomDiscipline() == null || d.getNomDiscipline().isBlank()) {
            throw new IllegalArgumentException("Le nom de la discipline est obligatoire.");
        }
        return disciplineDAO.ajouter(d);
    }

    @Override
    public boolean modifier(Discipline d) throws SQLException {
        if (disciplineDAO.rechercherParId(d.getIdDiscipline()) == null) {
            throw new IllegalArgumentException("Discipline introuvable.");
        }
        return disciplineDAO.modifier(d);
    }

    @Override
    public boolean supprimer(int idDiscipline) throws SQLException {
        return disciplineDAO.supprimer(idDiscipline);
    }

    @Override
    public Discipline rechercherParId(int idDiscipline) throws SQLException {
        return disciplineDAO.rechercherParId(idDiscipline);
    }

    @Override
    public List<Discipline> rechercherParNom(String nom) throws SQLException {
        return disciplineDAO.rechercherParNom(nom);
    }

    @Override
    public List<Discipline> listerTous() throws SQLException {
        return disciplineDAO.afficherTous();
    }
}