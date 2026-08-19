package Model;

public class Utilisateur {
    private int IdUtilisateur;
    private String Nom ;
    private String Login;
    private String Mdp ;
    private String Role;

    public Utilisateur(String nomComplet, String login, String mdp, String role) {
    }

    public Utilisateur() {

    }

    public int getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        IdUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String mdp) {
        Mdp = mdp;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "IdUtilisateur=" + IdUtilisateur +
                ", Nom='" + Nom + '\'' +
                ", Login='" + Login + '\'' +
                ", Mdp='" + Mdp + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(Role);
    }
}
