package Model;

import java.time.LocalDate;

public class Athlete {
    private int  IdAthlete ;
    private String Nom ;
    private String Prenom ;
    private String Sexe;
    private int DateNaissance ;
    private String Pays ;
    private String Discipline ;


    public Athlete(String nom, String prenom, String sexe, LocalDate dateNaissance, int idPays, int idDiscipline) {
    }

    public Athlete() {

    }

    public int getIdAthlete() {
        return IdAthlete;
    }

    public void setIdAthlete(int idAthlete) {
        IdAthlete = idAthlete;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public int getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(int dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public String getPays() {
        return Pays;
    }

    public void setPays(String pays) {
        Pays = pays;
    }

    public String getDiscipline() {
        return Discipline;
    }

    public void setDiscipline(String discipline) {
        Discipline = discipline;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "IdAthlete=" + IdAthlete +
                ", Nom='" + Nom + '\'' +
                ", Prénom='" + Prenom + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", DateNaissance=" + DateNaissance +
                ", Pays='" + Pays + '\'' +
                ", Discipline='" + Discipline + '\'' +
                '}';
    }
}
