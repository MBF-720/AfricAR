package pfe.africar.classes;


import java.util.ArrayList;

// Classe représentant une école
public class Ecole {
    private int id;
    private String nom;
    private String directeur;
    private String contactTel;
    private String contactEmail;
    private String adresse;
    private ArrayList<Classe> classes;
    private ArrayList<Salle> salles;
    private ArrayList<Eleve> eleves;
    private ArrayList<Professeur> professeurs;
    private ArrayList<Agent> agents;

    // Constructeur
    public Ecole(int id, String nom, String directeur, String contactTel, String contactEmail, String adresse) {
        this.id = id;
        this.nom = nom;
        this.directeur = directeur;
        this.contactTel = contactTel;
        this.contactEmail = contactEmail;
        this.adresse = adresse;
        this.classes = new ArrayList<>();
        this.salles = new ArrayList<>();
        this.eleves = new ArrayList<>();
        this.professeurs = new ArrayList<>();
        this.agents = new ArrayList<>();
    }

    // Méthodes pour ajouter des classes, des salles, des élèves, des professeurs et des agents
    public void ajouterClasse(Classe classe) {
        classes.add(classe);
    }

    public void ajouterSalle(Salle salle) {
        salles.add(salle);
    }

    public void ajouterEleve(Eleve eleve) {
        eleves.add(eleve);
    }

    public void ajouterProfesseur(Professeur professeur) {
        professeurs.add(professeur);
    }

    public void ajouterAgent(Agent agent) {
        agents.add(agent);
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDirecteur() {
        return directeur;
    }

    public void setDirecteur(String directeur) {
        this.directeur = directeur;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ArrayList<Classe> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Classe> classes) {
        this.classes = classes;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }

    public void setSalles(ArrayList<Salle> salles) {
        this.salles = salles;
    }

    public ArrayList<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
    }

    public ArrayList<Professeur> getProfesseurs() {
        return professeurs;
    }

    public void setProfesseurs(ArrayList<Professeur> professeurs) {
        this.professeurs = professeurs;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }
}





