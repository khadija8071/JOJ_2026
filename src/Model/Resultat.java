package Model;

public class Resultat {
    private int IdResultat ;
    private int id_Athlete ;
    private int id_compet ;
    private int Score;
    private int Rang ;

    public Resultat() {
    }

    public int getIdResultat() {
        return IdResultat;
    }

    public void setIdResultat(int idResultat) {
        IdResultat = idResultat;
    }


    public int getId_Athlete() {
        return id_Athlete;
    }

    public void setId_Athlete(int id_Athlete) {
        this.id_Athlete = id_Athlete;
    }

    public int getId_compet() {
        return id_compet;
    }

    public void setId_compet(int id_compet) {
        this.id_compet = id_compet;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getRang() {
        return Rang;
    }

    public void setRang(int rang) {
        Rang = rang;
    }

    public String getMedaille() {
        switch (Rang) {
            case 1:
                return "Or";
            case 2:
                return "Argent";
            case 3:
                return "Bronze";
            default:
                return "-";
        }
    }

    @Override
    public String toString() {
        return "Resultat{" +
                "IdResultat=" + IdResultat +
                ", lAthlete=" + id_Athlete +
                ", compet=" + id_compet +
                ", Score=" + Score +
                ", Rang=" + Rang +
                '}';
    }
}
