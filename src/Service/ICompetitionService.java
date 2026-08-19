package Service;

import Model.Competition;

import java.sql.SQLException;
import java.util.List;

public interface ICompetitionService {

    boolean ajouter(Competition c) throws SQLException;

    boolean modifier(Competition c) throws SQLException;

    boolean supprimer(int idCompetition) throws SQLException;

    Competition rechercherParId(int idCompetition) throws SQLException;

    List<Competition> rechercherParNom(String nom) throws SQLException;

    List<Competition> listerTous() throws SQLException;
}