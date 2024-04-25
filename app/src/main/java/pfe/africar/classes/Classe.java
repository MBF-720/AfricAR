package pfe.africar.classes;

import java.util.ArrayList;
import java.util.List;

public class Classe {



    private String classeID;
    private String ecoleID;
    private String niveau;
    private List<Eleve> eleves;
    private List<Professeur> professeurs;
    private List<String> matieres;
    private List<Quiz> quiz;

    public Classe(String niveau,String ecoleID) {
        this.niveau = niveau;
        this.eleves = new ArrayList<>();
        this.professeurs = new ArrayList<>();
        this.matieres = new ArrayList<>();
        this.quiz = new ArrayList<>();
    }

    public void addEleve(Eleve eleve) {
        this.eleves.add(eleve);
    }

    public void removeEleve(String eleve) {
        this.eleves.remove(eleve);
    }

    public void addProfesseur(Professeur professeur) {
        this.professeurs.add(professeur);
    }

    public void removeProfesseur(Professeur professeur) {
        this.professeurs.remove(professeur);
    }

    public void addMatiere(String matiere) {
        this.matieres.add(matiere);
    }

    public void removeMatiere(String matiere) {
        this.matieres.remove(matiere);
    }

    public void addQuiz(Quiz quiz) {
        this.quiz.add(quiz);
    }

    public void removeQuiz(Quiz quiz) {
        this.quiz.remove(quiz);
    }

    public String getNiveau() {
        return niveau;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public List<Professeur> getProfesseurs() {
        return professeurs;
    }

    public List<String> getMatieres() {
        return matieres;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }
    public String getClasseID() {
        return classeID;
    }

    public String getEcoleID() {
        return ecoleID;
    }

    public void setClasseID(String classeID) {
        this.classeID = classeID;
    }

    public void setEcoleID(String ecoleID) {
        this.ecoleID = ecoleID;
    }
}