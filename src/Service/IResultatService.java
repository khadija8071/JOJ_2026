package Service;

import Model.Resultat;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IResultatService {

    boolean enregistrer(Resultat r) throws SQLException;

    boolean modifier(Resultat r) throws SQLException;

    boolean supprimer(int idResultat) throws SQLException;

    Resultat rechercherParId(int idResultat) throws SQLException;

    List<Resultat> listerTous() throws SQLException;

    List<Resultat> classementParCompetition(int idCompetition) throws SQLException;

    /** Tableau des médailles par pays : {or, argent, bronze, total}. */
    Map<String, int[]> getTableauMedailles() throws SQLException;
}