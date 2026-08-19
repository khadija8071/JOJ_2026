package Service;

import Model.Discipline;

import java.sql.SQLException;
import java.util.List;

public interface IDisciplineService {

    boolean ajouter(Discipline d) throws SQLException;

    boolean modifier(Discipline d) throws SQLException;

    boolean supprimer(int idDiscipline) throws SQLException;

    Discipline rechercherParId(int idDiscipline) throws SQLException;

    List<Discipline> rechercherParNom(String nom) throws SQLException;

    List<Discipline> listerTous() throws SQLException;
}