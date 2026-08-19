package Service;

import Model.Athlete;

import java.sql.SQLException;
import java.util.List;

public interface IAthleteService {

    boolean ajouter(Athlete a) throws SQLException;

    boolean modifier(Athlete a) throws SQLException;

    boolean supprimer(int idAthlete) throws SQLException;

    Athlete rechercherParId(int idAthlete) throws SQLException;

    List<Athlete> rechercherParNom(String nom) throws SQLException;

    List<Athlete> listerTous() throws SQLException;

    List<String> listerAvecDetails() throws SQLException;
}