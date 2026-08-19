package Service;

import java.sql.SQLException;
import java.util.Map;

public interface IStatistiqueService {

    Map<String, Integer> getStatistiquesGlobales() throws SQLException;
}