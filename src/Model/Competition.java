package Model;

public class Competition {
    private int IdCompetition ;
    private String NomCompetition ;
    private int DateCompetition ;
    private String Lieu ; // dk,saly,diamniadio
    private String Discipline ;
    public Competition() {}

    public int getIdCompetition() {
        return IdCompetition;
    }

    public void setIdCompetition(int idCompetition) {
        IdCompetition = idCompetition;
    }

    public String getNomCompetition() {
        return NomCompetition;
    }

    public void setNomCompetition(String nomCompetition) {
        NomCompetition = nomCompetition;
    }

    public int getDateCompetition() {
        return DateCompetition;
    }

    public void setDateCompetition(int dateCompetition) {
        DateCompetition = dateCompetition;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public String getDiscipline() {
        return Discipline;
    }

    public void setDiscipline(String discipline) {
        Discipline = discipline;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "IdCompetition=" + IdCompetition +
                ", NomCompetition='" + NomCompetition + '\'' +
                ", DateCompetition=" + DateCompetition +
                ", Lieu='" + Lieu + '\'' +
                ", Discipline='" + Discipline + '\'' +
                '}';
    }
}
