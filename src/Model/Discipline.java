package Model;

public class Discipline {
    private int IdDiscipline ;
    private String NomDiscipline ;
    private String  Description ;

    public Discipline() {}

    public int getIdDiscipline() {
        return IdDiscipline;
    }

    public void setIdDiscipline(int idDiscipline) {
        IdDiscipline = idDiscipline;
    }

    public String getNomDiscipline() {
        return NomDiscipline;
    }

    public void setNomDiscipline(String nomDiscipline) {
        NomDiscipline = nomDiscipline;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "IdDiscipline=" + IdDiscipline +
                ", NomDiscipline='" + NomDiscipline + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
