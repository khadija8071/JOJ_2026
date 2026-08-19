package Service;

import Model.Athlete;
import Model.Competition;
import Model.Discipline;
import Model.Pays;
import Model.Resultat;
import Model.Utilisateur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IMenuImple implements IMenu {

    private final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final IAuthService authService = new IAuthServiceImple();
    private final IUtilisateurService utilisateurService = new IUtilisateurServiceImple();
    private final IPaysService paysService = new IPaysServiceImple();
    private final IDisciplineService disciplineService = new IDisciplineServiceImple();
    private final IAthleteService athleteService = new IAthleteServiceImple();
    private final ICompetitionService competitionService = new ICompetitionserviceImple();
    private final IResultatService resultatService = new IResultatServiceImple();
    private final IStatistiqueService statistiqueService = new IStatistiqueServiceImple();

    @Override
    public void demarrer() {
        boolean quitter = false;
        while (!quitter) {
            if (!authService.estConnecte()) {
                seConnecter();
            }
            if (authService.estConnecte()) {
                quitter = afficherMenuPrincipal();
            }
        }
        System.out.println("Au revoir !");
        sc.close();
    }

    // ================= AUTHENTIFICATION =================

    private void seConnecter() {
        System.out.println("\n=== CONNEXION ===");
        System.out.print("Login : ");
        String login = sc.nextLine().trim();
        System.out.print("Mot de passe : ");
        String motDePasse = sc.nextLine().trim();
        try {
            Utilisateur u = authService.connecter(login, motDePasse);
            if (u == null) {
                System.out.println("Login ou mot de passe incorrect.");
            } else {
                System.out.println("Bienvenue " + u.getNomComplet() + " (" + u.getRole() + ") !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
    }

    // ================= MENU PRINCIPAL =================

    /** Renvoie true si l'utilisateur choisit de quitter l'application. */
    private boolean afficherMenuPrincipal() {
        System.out.println("\n===================================");
        System.out.println("JEUX OLYMPIQUES DE LA JEUNESSE 2026");
        System.out.println("===================================");
        System.out.println("1. Gestion des utilisateurs");
        System.out.println("2. Gestion des pays");
        System.out.println("3. Gestion des disciplines");
        System.out.println("4. Gestion des athlètes");
        System.out.println("5. Gestion des compétitions");
        System.out.println("6. Gestion des résultats");
        System.out.println("7. Statistiques");
        System.out.println("8. Déconnexion");
        System.out.println("9. Quitter");
        int choix = lireInt("Votre choix : ");

        switch (choix) {
            case 1:
                if (authService.estAdmin()) {
                    menuUtilisateurs();
                } else {
                    System.out.println("Accès réservé à l'administrateur.");
                }
                break;
            case 2: menuPays(); break;
            case 3: menuDisciplines(); break;
            case 4: menuAthletes(); break;
            case 5: menuCompetitions(); break;
            case 6: menuResultats(); break;
            case 7: afficherStatistiques(); break;
            case 8:
                authService.deconnecter();
                System.out.println("Vous êtes déconnecté.");
                break;
            case 9:
                return true;
            default:
                System.out.println("Choix invalide.");
        }
        return false;
    }

    // ================= MODULE UTILISATEURS =================

    private void menuUtilisateurs() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES UTILISATEURS ---");
            System.out.println("1. Ajouter utilisateur");
            System.out.println("2. Modifier utilisateur");
            System.out.println("3. Supprimer utilisateur");
            System.out.println("4. Rechercher utilisateur");
            System.out.println("5. Afficher utilisateurs");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        System.out.print("Nom complet : ");
                        String nomComplet = sc.nextLine().trim();
                        System.out.print("Login : ");
                        String login = sc.nextLine().trim();
                        System.out.print("Mot de passe : ");
                        String mdp = sc.nextLine().trim();
                        String role = lireRole();
                        boolean ok = utilisateurService.ajouter(new Utilisateur(nomComplet, login, mdp, role));
                        System.out.println(ok ? "Utilisateur ajouté." : "Échec de l'ajout.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID de l'utilisateur à modifier : ");
                        Utilisateur u = utilisateurService.rechercherParId(id);
                        if (u == null) {
                            System.out.println("Utilisateur introuvable.");
                            break;
                        }
                        System.out.print("Nouveau nom complet (" + u.getNomComplet() + ") : ");
                        u.setNomComplet(sc.nextLine().trim());
                        System.out.print("Nouveau login (" + u.getLogin() + ") : ");
                        u.setLogin(sc.nextLine().trim());
                        System.out.print("Nouveau mot de passe : ");
                        u.setMotDePasse(sc.nextLine().trim());
                        u.setRole(lireRole());
                        boolean ok = utilisateurService.modifier(u);
                        System.out.println(ok ? "Utilisateur modifié." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID de l'utilisateur à supprimer : ");
                        boolean ok = utilisateurService.supprimer(id);
                        System.out.println(ok ? "Utilisateur supprimé." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        int id = lireInt("ID de l'utilisateur à rechercher : ");
                        Utilisateur u = utilisateurService.rechercherParId(id);
                        System.out.println(u != null ? u : "Aucun utilisateur trouvé.");
                        break;
                    }
                    case 5: {
                        List<Utilisateur> liste = utilisateurService.listerTous();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    private String lireRole() {
        System.out.print("Rôle (ADMIN/AGENT) : ");
        String role = sc.nextLine().trim().toUpperCase();
        if (!role.equals("ADMIN") && !role.equals("AGENT")) {
            System.out.println("Rôle invalide, valeur par défaut AGENT appliquée.");
            return "AGENT";
        }
        return role;
    }

    // ================= MODULE PAYS =================

    private void menuPays() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES PAYS ---");
            System.out.println("1. Ajouter pays");
            System.out.println("2. Modifier pays");
            System.out.println("3. Supprimer pays");
            System.out.println("4. Rechercher pays");
            System.out.println("5. Liste des pays");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        System.out.print("Nom du pays : ");
                        String nom = sc.nextLine().trim();
                        System.out.print("Continent : ");
                        String continent = sc.nextLine().trim();
                        boolean ok = paysService.ajouter(new Pays(nom, continent));
                        System.out.println(ok ? "Pays ajouté." : "Échec de l'ajout.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID du pays à modifier : ");
                        Pays p = paysService.rechercherParId(id);
                        if (p == null) {
                            System.out.println("Pays introuvable.");
                            break;
                        }
                        System.out.print("Nouveau nom (" + p.getNomPays() + ") : ");
                        p.setNomPays(sc.nextLine().trim());
                        System.out.print("Nouveau continent (" + p.getContinent() + ") : ");
                        p.setContinent(sc.nextLine().trim());
                        boolean ok = paysService.modifier(p);
                        System.out.println(ok ? "Pays modifié." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID du pays à supprimer : ");
                        boolean ok = paysService.supprimer(id);
                        System.out.println(ok ? "Pays supprimé." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        System.out.print("Nom (ou partie du nom) à rechercher : ");
                        String nom = sc.nextLine().trim();
                        List<Pays> resultats = paysService.rechercherParNom(nom);
                        if (resultats.isEmpty()) {
                            System.out.println("Aucun pays trouvé.");
                        } else {
                            resultats.forEach(System.out::println);
                        }
                        break;
                    }
                    case 5: {
                        List<Pays> liste = paysService.listerTous();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    // ================= MODULE DISCIPLINES =================

    private void menuDisciplines() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES DISCIPLINES ---");
            System.out.println("1. Ajouter discipline");
            System.out.println("2. Modifier discipline");
            System.out.println("3. Supprimer discipline");
            System.out.println("4. Rechercher discipline");
            System.out.println("5. Afficher disciplines");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        System.out.print("Nom de la discipline : ");
                        String nom = sc.nextLine().trim();
                        System.out.print("Description : ");
                        String description = sc.nextLine().trim();
                        boolean ok = disciplineService.ajouter(new Discipline(nom, description));
                        System.out.println(ok ? "Discipline ajoutée." : "Échec de l'ajout.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID de la discipline à modifier : ");
                        Discipline d = disciplineService.rechercherParId(id);
                        if (d == null) {
                            System.out.println("Discipline introuvable.");
                            break;
                        }
                        System.out.print("Nouveau nom (" + d.getNomDiscipline() + ") : ");
                        d.setNomDiscipline(sc.nextLine().trim());
                        System.out.print("Nouvelle description (" + d.getDescription() + ") : ");
                        d.setDescription(sc.nextLine().trim());
                        boolean ok = disciplineService.modifier(d);
                        System.out.println(ok ? "Discipline modifiée." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID de la discipline à supprimer : ");
                        boolean ok = disciplineService.supprimer(id);
                        System.out.println(ok ? "Discipline supprimée." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        System.out.print("Nom (ou partie du nom) à rechercher : ");
                        String nom = sc.nextLine().trim();
                        List<Discipline> resultats = disciplineService.rechercherParNom(nom);
                        if (resultats.isEmpty()) {
                            System.out.println("Aucune discipline trouvée.");
                        } else {
                            resultats.forEach(System.out::println);
                        }
                        break;
                    }
                    case 5: {
                        List<Discipline> liste = disciplineService.listerTous();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    // ================= MODULE ATHLETES =================

    private void menuAthletes() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES ATHLÈTES ---");
            System.out.println("1. Ajouter athlète");
            System.out.println("2. Modifier athlète");
            System.out.println("3. Supprimer athlète");
            System.out.println("4. Rechercher athlète");
            System.out.println("5. Afficher athlètes");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        System.out.print("Nom : ");
                        String nom = sc.nextLine().trim();
                        System.out.print("Prénom : ");
                        String prenom = sc.nextLine().trim();
                        String sexe = lireSexe();
                        LocalDate dateNaissance = lireDate("Date de naissance (jj/mm/aaaa) : ");
                        int idPays = lireInt("ID du pays : ");
                        int idDiscipline = lireInt("ID de la discipline : ");
                        boolean ok = athleteService.ajouter(
                                new Athlete(nom, prenom, sexe, dateNaissance, idPays, idDiscipline));
                        System.out.println(ok ? "Athlète ajouté." : "Échec de l'ajout.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID de l'athlète à modifier : ");
                        Athlete a = athleteService.rechercherParId(id);
                        if (a == null) {
                            System.out.println("Athlète introuvable.");
                            break;
                        }
                        System.out.print("Nouveau nom (" + a.getNom() + ") : ");
                        a.setNom(sc.nextLine().trim());
                        System.out.print("Nouveau prénom (" + a.getPrenom() + ") : ");
                        a.setPrenom(sc.nextLine().trim());
                        a.setSexe(lireSexe());
                        a.setDateNaissance(lireDate("Nouvelle date de naissance (jj/mm/aaaa) : "));
                        a.setIdPays(lireInt("Nouvel ID pays : "));
                        a.setIdDiscipline(lireInt("Nouvel ID discipline : "));
                        boolean ok = athleteService.modifier(a);
                        System.out.println(ok ? "Athlète modifié." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID de l'athlète à supprimer : ");
                        boolean ok = athleteService.supprimer(id);
                        System.out.println(ok ? "Athlète supprimé." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        System.out.print("Nom ou prénom à rechercher : ");
                        String nom = sc.nextLine().trim();
                        List<Athlete> resultats = athleteService.rechercherParNom(nom);
                        if (resultats.isEmpty()) {
                            System.out.println("Aucun athlète trouvé.");
                        } else {
                            resultats.forEach(System.out::println);
                        }
                        break;
                    }
                    case 5: {
                        List<String> liste = athleteService.listerAvecDetails();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    private String lireSexe() {
        System.out.print("Sexe (M/F) : ");
        String sexe = sc.nextLine().trim().toUpperCase();
        while (!sexe.equals("M") && !sexe.equals("F")) {
            System.out.print("Valeur invalide. Sexe (M/F) : ");
            sexe = sc.nextLine().trim().toUpperCase();
        }
        return sexe;
    }

    // ================= MODULE COMPETITIONS =================

    private void menuCompetitions() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES COMPÉTITIONS ---");
            System.out.println("1. Ajouter compétition");
            System.out.println("2. Modifier compétition");
            System.out.println("3. Supprimer compétition");
            System.out.println("4. Rechercher compétition");
            System.out.println("5. Afficher compétitions");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        System.out.print("Nom de la compétition : ");
                        String nom = sc.nextLine().trim();
                        LocalDate date = lireDate("Date (jj/mm/aaaa) : ");
                        String lieu = lireLieu();
                        int idDiscipline = lireInt("ID de la discipline : ");
                        boolean ok = competitionService.ajouter(
                                new Competition(nom, date, lieu, idDiscipline));
                        System.out.println(ok ? "Compétition ajoutée." : "Échec de l'ajout.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID de la compétition à modifier : ");
                        Competition c = competitionService.rechercherParId(id);
                        if (c == null) {
                            System.out.println("Compétition introuvable.");
                            break;
                        }
                        System.out.print("Nouveau nom (" + c.getNomCompetition() + ") : ");
                        c.setNomCompetition(sc.nextLine().trim());
                        c.setDateCompetition(lireDate("Nouvelle date (jj/mm/aaaa) : "));
                        c.setLieu(lireLieu());
                        c.setIdDiscipline(lireInt("Nouvel ID discipline : "));
                        boolean ok = competitionService.modifier(c);
                        System.out.println(ok ? "Compétition modifiée." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID de la compétition à supprimer : ");
                        boolean ok = competitionService.supprimer(id);
                        System.out.println(ok ? "Compétition supprimée." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        System.out.print("Nom (ou partie du nom) à rechercher : ");
                        String nom = sc.nextLine().trim();
                        List<Competition> resultats = competitionService.rechercherParNom(nom);
                        if (resultats.isEmpty()) {
                            System.out.println("Aucune compétition trouvée.");
                        } else {
                            resultats.forEach(System.out::println);
                        }
                        break;
                    }
                    case 5: {
                        List<Competition> liste = competitionService.listerTous();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    private String lireLieu() {
        System.out.print("Lieu (Dakar/Diamniadio/Saly) : ");
        String lieu = sc.nextLine().trim();
        while (!lieu.equalsIgnoreCase("Dakar") && !lieu.equalsIgnoreCase("Diamniadio") && !lieu.equalsIgnoreCase("Saly")) {
            System.out.print("Lieu invalide. Choisissez Dakar, Diamniadio ou Saly : ");
            lieu = sc.nextLine().trim();
        }
        // Normalise la casse (première lettre en majuscule) pour respecter l'ENUM SQL
        return lieu.substring(0, 1).toUpperCase() + lieu.substring(1).toLowerCase();
    }

    // ================= MODULE RESULTATS =================

    private void menuResultats() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- GESTION DES RÉSULTATS ---");
            System.out.println("1. Enregistrer résultat");
            System.out.println("2. Modifier résultat");
            System.out.println("3. Supprimer résultat");
            System.out.println("4. Classement compétition");
            System.out.println("5. Afficher résultats");
            System.out.println("6. Retour");
            int choix = lireInt("Votre choix : ");
            try {
                switch (choix) {
                    case 1: {
                        int idAthlete = lireInt("ID de l'athlète : ");
                        int idCompetition = lireInt("ID de la compétition : ");
                        double score = lireDouble("Score : ");
                        int rang = lireInt("Rang : ");
                        boolean ok = resultatService.enregistrer(
                                new Resultat(idAthlete, idCompetition, score, rang));
                        System.out.println(ok ? "Résultat enregistré." : "Échec de l'enregistrement.");
                        break;
                    }
                    case 2: {
                        int id = lireInt("ID du résultat à modifier : ");
                        Resultat r = resultatService.rechercherParId(id);
                        if (r == null) {
                            System.out.println("Résultat introuvable.");
                            break;
                        }
                        r.setIdAthlete(lireInt("Nouvel ID athlète (" + r.getIdAthlete() + ") : "));
                        r.setIdCompetition(lireInt("Nouvel ID compétition (" + r.getIdCompetition() + ") : "));
                        r.setScore(lireDouble("Nouveau score (" + r.getScore() + ") : "));
                        r.setRang(lireInt("Nouveau rang (" + r.getRang() + ") : "));
                        boolean ok = resultatService.modifier(r);
                        System.out.println(ok ? "Résultat modifié." : "Échec de la modification.");
                        break;
                    }
                    case 3: {
                        int id = lireInt("ID du résultat à supprimer : ");
                        boolean ok = resultatService.supprimer(id);
                        System.out.println(ok ? "Résultat supprimé." : "Échec de la suppression.");
                        break;
                    }
                    case 4: {
                        int idCompetition = lireInt("ID de la compétition : ");
                        List<Resultat> classement = resultatService.classementParCompetition(idCompetition);
                        if (classement.isEmpty()) {
                            System.out.println("Aucun résultat pour cette compétition.");
                        } else {
                            for (Resultat r : classement) {
                                System.out.printf("Rang %d - Athlète #%d - Score %.2f - Médaille : %s%n",
                                        r.getRang(), r.getIdAthlete(), r.getScore(), r.getMedaille());
                            }
                        }
                        break;
                    }
                    case 5: {
                        List<Resultat> liste = resultatService.listerTous();
                        liste.forEach(System.out::println);
                        break;
                    }
                    case 6: retour = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur base de données : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    // ================= STATISTIQUES + TABLEAU DES MÉDAILLES =================

    private void afficherStatistiques() {
        try {
            System.out.println("\n--- STATISTIQUES ---");
            Map<String, Integer> stats = statistiqueService.getStatistiquesGlobales();
            System.out.println("Nombre de pays         : " + stats.get("pays"));
            System.out.println("Nombre d'athlètes       : " + stats.get("athletes"));
            System.out.println("Nombre de disciplines   : " + stats.get("disciplines"));
            System.out.println("Nombre de compétitions  : " + stats.get("competitions"));
            System.out.println("Nombre de résultats     : " + stats.get("resultats"));

            System.out.println("\n--- TABLEAU DES MÉDAILLES ---");
            Map<String, int[]> tableau = resultatService.getTableauMedailles();
            System.out.printf("%-20s %-5s %-8s %-8s %-8s%n", "Pays", "Or", "Argent", "Bronze", "Total");
            for (Map.Entry<String, int[]> entree : tableau.entrySet()) {
                int[] m = entree.getValue();
                System.out.printf("%-20s %-5d %-8d %-8d %-8d%n", entree.getKey(), m[0], m[1], m[2], m[3]);
            }
        } catch (SQLException e) {
            System.out.println("Erreur base de données : " + e.getMessage());
        }
    }

    // ================= UTILITAIRES DE SAISIE =================

    private int lireInt(String message) {
        while (true) {
            System.out.print(message);
            String saisie = sc.nextLine().trim();
            try {
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre entier valide.");
            }
        }
    }

    private double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            String saisie = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(saisie);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre valide.");
            }
        }
    }

    private LocalDate lireDate(String message) {
        while (true) {
            System.out.print(message);
            String saisie = sc.nextLine().trim();
            try {
                return LocalDate.parse(saisie, FORMAT_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide, utilisez jj/mm/aaaa (ex: 15/03/2008).");
            }
        }
    }
}