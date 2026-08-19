package Service;

import DAO.AthleteDao;
import DAO.CompetitionDao;
import DAO.DisciplineDao;
import DAO.PaysDao;
import DAO.ResultatDao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class IStatistiqueServiceImple implements IStatistiqueService {

    private final PaysDao paysDAO = new PaysDao();
    private final AthleteDao athleteDAO = new AthleteDao();
    private final DisciplineDao disciplineDAO = new DisciplineDao();
    private final CompetitionDao competitionDAO = new CompetitionDao();
    private final ResultatDao resultatDAO = new ResultatDao();

    @Override
    public Map<String, Integer> getStatistiquesGlobales() throws SQLException {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("pays", paysDAO.afficherTous().size());
        stats.put("athletes", athleteDAO.afficherTous().size());
        stats.put("disciplines", disciplineDAO.afficherTous().size());
        return stats;
    }
}