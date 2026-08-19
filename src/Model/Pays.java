package Model;

public class Pays {
    private int IdPays ;
    private String nomPays ;
private String  Continent ;
    public Pays(String nom, String continent) {
    }

    public Pays() {

    }

    public int getIdPays() {
        return IdPays;
    }

    public void setIdPays(int idPays) {
        IdPays = idPays;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "IdPays=" + IdPays +
                ", nomPays='" + nomPays + '\'' +
                ", Continent='" + Continent + '\'' +
                '}';
    }
}
